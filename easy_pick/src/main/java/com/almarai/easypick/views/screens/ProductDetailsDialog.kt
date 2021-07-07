package com.almarai.easypick.views.screens

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.almarai.business.CratesAndPieces
import com.almarai.business.CratesPieces
import com.almarai.common.logging.FIREBASE_ANALYTICS
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.product.ProductStatus
import com.almarai.easypick.R
import com.almarai.easypick.databinding.DialogProductDetailBinding
import com.almarai.easypick.extensions.hideKeyboard
import com.almarai.easypick.extensions.positionDialogAtBottom
import com.almarai.easypick.utils.AlertTones.playTone
import com.almarai.easypick.view_models.HighlightItem
import com.almarai.easypick.view_models.ProductListViewModel
import com.almarai.easypick.view_models.UpdateProduct
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsDialog : DialogFragment(),
    View.OnKeyListener {
    private val productListViewModel: ProductListViewModel by activityViewModels()
    private var indexPos = 0
    private val args: ProductDetailsDialogArgs by navArgs()
    private lateinit var products: List<Product>
    private lateinit var dialogProductDetailsBinding: DialogProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogProductDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_product_detail, container, false)
        dialogProductDetailsBinding.apply {
            lifecycleOwner = this@ProductDetailsDialog
            dialogProductDetailsBinding.product =
                Product()
            dialogProductDetailsBinding.productDialog = this@ProductDetailsDialog
        }

        return dialogProductDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hide the navigation bar from bottom
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        //Avoid default color, use our custom color what we define
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        init()
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    private fun init() {
        indexPos = args.SelectedProductPosition

        productListViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                if (result is Result.Success) products = result.data
            }
        })
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        process()
    }

    private fun process() {
        //Set the dialogue to specified position
        dialog?.positionDialogAtBottom()

        //Place the first view from the list with index position
        setProductValues()

        //Set the key listener
        dialogProductDetailsBinding.dialogProductDetailCratesEditText.setOnKeyListener(this@ProductDetailsDialog)
        dialogProductDetailsBinding.dialogProductDetailPiecesEditText.setOnKeyListener(this@ProductDetailsDialog)
    }

    private fun setProductValues() {
        if (indexPos > RecyclerView.NO_POSITION && indexPos < products.size) {
            dialogProductDetailsBinding.product = products[indexPos]

            lifecycleScope.launch {
                // Hack
                delay(300)
                dialogProductDetailsBinding.dialogProductDetailPiecesEditText.selectAll()
            }

            FIREBASE_ANALYTICS?.logEvent("product_detail_dialog") {
                param(FirebaseAnalytics.Param.ITEM_ID, products[indexPos].number.toString())
                param(FirebaseAnalytics.Param.ITEM_NAME, products[indexPos].description)
            }
        } else {
            //The user might have reached the end of the list, dismiss the dialog
            this.dismiss()

            selectAllOnSearchView()
        }
    }

    private fun selectAllOnSearchView() {
        val currentFocus = requireActivity().currentFocus
        if (currentFocus is SearchView.SearchAutoComplete) {
            //User is in gotham mode
            currentFocus.selectAll()

            hideKeyboard()
        }
    }

    override fun onKey(view: View, keyCode: Int, event: KeyEvent): Boolean {
        return if (event.action == KeyEvent.ACTION_UP) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    return when (view.id) {
                        R.id.dialog_product_detail_crates_edit_text -> {
                            focusOnPieces(view)
                            true
                        }
                        R.id.dialog_product_detail_pieces_edit_text -> {
                            updateProduct()
                            false
                        }
                        else -> false
                    }
                }

                KeyEvent.KEYCODE_DPAD_UP -> {
                    onKeyBoardUpArrowPressed()
                    true
                }

                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    onKeyBoardDownArrowPressed()
                    true
                }

                else -> false
            }
        } else false
    }

    private fun getDialogHeight() = dialog?.window?.decorView?.height ?: 286//Custom height, if null

    private fun onKeyBoardDownArrowPressed() {
        productListViewModel.highlightRecentItemInList(HighlightItem(indexPos, getDialogHeight()))
        indexPos++
        setProductValues()
    }

    private fun onKeyBoardUpArrowPressed() {
        --indexPos
        productListViewModel.highlightRecentItemInList(HighlightItem(indexPos - 1, getDialogHeight()))
        setProductValues()
    }

    private fun focusOnPieces(v: View) {
        if (dialogProductDetailsBinding.dialogProductDetailCratesEditText.text.toString()
                .isEmpty()
        ) {
            dialogProductDetailsBinding.dialogProductDetailCratesEditText.setText(R.string.default_number)
        }
        v.focusSearch(View.FOCUS_RIGHT).requestFocus()
    }

    private fun updateProduct() {
        if (dialogProductDetailsBinding.dialogProductDetailPiecesEditText.text.toString()
                .isEmpty()
        ) {
            dialogProductDetailsBinding.dialogProductDetailPiecesEditText.setText(R.string.default_number)
        }
        updateProductPickedStatus()

        //Make the alert sound
        playTone(true)

        indexPos++
        setProductValues()
    }

    private fun updateProductPickedStatus() {
        val product = products[indexPos]
        val cratesPieces = CratesPieces.calculateTotalCratesAndPieces(
            dialogProductDetailsBinding.dialogProductDetailCratesEditText.text.toString().toInt(),
            dialogProductDetailsBinding.dialogProductDetailPiecesEditText.text.toString().toInt(),
            product.upc
        )

        //Update the data in the list
        product.productStatus = ProductStatus.Picked
        product.totalStock = "${cratesPieces.crates}/${cratesPieces.pieces}"

        val dialogHeight = dialog?.window?.decorView?.height ?: 286//Custom height
        productListViewModel.updateProduct(UpdateProduct(indexPos, product, cratesPieces, dialogHeight))
    }

    interface ProductItemChangeListener {
        fun onItemChanged(
            position: Int,
            product: Product,
            cratesAndPieces: CratesAndPieces,
            dialogHeight: Int
        )

        fun highlightRecentItemInList(position: Int, dialogHeight: Int)
    }
}