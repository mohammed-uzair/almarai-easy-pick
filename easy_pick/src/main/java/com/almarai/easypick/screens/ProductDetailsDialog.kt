package com.almarai.easypick.screens

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.almarai.easypick.R
import com.almarai.easypick.extensions.positionDialogAtBottom


class ProductDetailsDialog : DialogFragment() {
    private var fragment: Fragment? = null
    private var recyclerView: RecyclerView? = null
    private var indexPos = 0
    private lateinit var viewq: View
//    private var customerInvoiceAdapter_asrm: SalesAdapterDefault? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewq = inflater.inflate(R.layout.dialog_product_detail, container)
        return viewq
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

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        // Set a theme on the dialog builder constructor!
//        val builder: AlertDialog.Builder =
//            AlertDialog.Builder(requireContext(), R.style.DialogStyle)
//        return builder.create()
//    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    private fun init() {
        //Init
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
    }

    private fun setProductValues() {
//        if (indexPos > RecyclerView.NO_POSITION && indexPos < salesProductDetailModelList!!.size) {
//            val salesProductDetailModel: SalesProductDetailModel =
//                salesProductDetailModelList!![indexPos]
//
//            //Get the next item from the list
//            salesProductDetailModelList!!.size
//            //Check if this product is a batch product, show the batch details popup
//            if (salesProductDetailModel.BatchEntryEnabled === ENABLED) {
//                //We don't want to show the batch product
//                ++indexPos
//
//                //Get the next item from the list
//                setProductValues()
//
//                /*//This is a batch product
//                customerInvoiceAdapter_asrm.openBatchDialog(fragment,
//                        customerInvoiceAdapter_asrm,
//                        indexPos,
//                        salesProductDetailModel.ItemNumber,
//                        salesProductDetailModel.UnitsPerCase,
//                        salesProductDetailModel.crates,
//                        salesProductDetailModel.pieces,
//                        salesProductDetailModel.Description,
//                        salesProductDetailModel.batchDetails);
//
//                //Dismiss the current dialog, to avoid over drawn of dialogs.
//                this.dismiss();*/
//            } else {
//                itemDescription!!.text = String.format(
//                    "%s - %s", String.valueOf(salesProductDetailModel.ItemNumber),
//                    salesProductDetailModel.Description
//                )
//                wd.setText(String.valueOf(salesProductDetailModel.WtdSalesQty))
//                upcText.setText(String.valueOf(salesProductDetailModel.UnitsPerCase))
//                cratesText.setText(String.valueOf(salesProductDetailModel.crates))
//                piecesText.setText(String.valueOf(salesProductDetailModel.pieces))
//                piecesText!!.requestFocus()
//                piecesText!!.setSelection(0, piecesText!!.text.length)
//            }
//        } else {
//            //The user might have reached the end of the list, dismiss the dialog
//            this.dismiss()
//        }
    }
}