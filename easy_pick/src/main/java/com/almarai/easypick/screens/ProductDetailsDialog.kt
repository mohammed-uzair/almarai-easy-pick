package com.almarai.easypick.screens

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.business.CratesPieces
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.product.ProductStatus
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ProductsAdapter
import com.almarai.easypick.databinding.DialogProductDetailBinding
import com.almarai.easypick.extensions.hideKeyboard
import com.almarai.easypick.extensions.positionDialogAtBottom
import com.almarai.easypick.extensions.showFocus
import com.almarai.easypick.utils.AlertTones.playTone
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProductDetailsDialog(private val productsAdapter: ProductsAdapter) : DialogFragment(),
    View.OnKeyListener, TextView.OnEditorActionListener {
    private var indexPos = 0
    private val args: ProductDetailsDialogArgs by navArgs()
    private lateinit var products: List<Product>
    private lateinit var dialogProductDetailsBinding: DialogProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        products = productsAdapter.products
        indexPos = args.SelectedProductPosition
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
        dialogProductDetailsBinding.dialogProductDetailPiecesEditText.setOnEditorActionListener(this@ProductDetailsDialog)
    }

    private fun setProductValues() {
        if (indexPos > RecyclerView.NO_POSITION && indexPos < products.size) {
            dialogProductDetailsBinding.product = products[indexPos]

            lifecycleScope.launch {
                // Hack
                delay(300)
                dialogProductDetailsBinding.dialogProductDetailPiecesEditText.selectAll()
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

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        return if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    return when (v.id) {
                        R.id.dialog_product_detail_crates_edit_text -> {
                            onCratesEnterPressed(v)
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
                    onKeyboardUpArrowPressed()
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

    private fun onKeyBoardDownArrowPressed() {
        showItemInList(indexPos)
        indexPos++
        setProductValues()
    }

    private fun onKeyboardUpArrowPressed() {
        --indexPos
        showItemInList(indexPos - 1)
        setProductValues()
    }

    private fun onCratesEnterPressed(v: View) {
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

        if (product.productStatus == ProductStatus.NotPicked) {
            //Update the items picked count in the products main list screen
            val itemsPicked: Int = productsAdapter.productViewModel.itemsPicked.value ?: 0
            productsAdapter.productViewModel.itemsPicked.value = itemsPicked + 1
        }

        //Update the array list and the recycler view
        products[indexPos].apply {
            editedCrates = cratesPieces.crates
            editedPieces = cratesPieces.pieces
            totalStock = "${cratesPieces.crates}/${cratesPieces.pieces}"
            productStatus = ProductStatus.Picked
        }

        //Notify the adapter
        productsAdapter.notifyItemChanged(indexPos)

        showItemInList(indexPos)
    }

    private fun showItemInList(index: Int) {
        //Init
        val dialogOffset = calculateDialogOffset()

        //Scroll to specified item from the list, and display above this dialog
        val layoutManager = productsAdapter.recyclerView.layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(index, dialogOffset)
        productsAdapter.recyclerView.showFocus(index, lifecycleScope)
    }

    private fun calculateDialogOffset(): Int {
        val dialogHeight = dialog?.window?.decorView?.height ?: 286//Custom height
        val recyclerViewHeight = productsAdapter.recyclerView.height
        val itemHeight =
            productsAdapter.recyclerView.findViewHolderForAdapterPosition(indexPos - 1)?.itemView?.height
                ?: 195//Custom aprox item card height

        return recyclerViewHeight - (dialogHeight + itemHeight + 20)//~extra 20 pixels for adding all the views margin spaces
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            updateProduct()
        }

        return false
    }
}