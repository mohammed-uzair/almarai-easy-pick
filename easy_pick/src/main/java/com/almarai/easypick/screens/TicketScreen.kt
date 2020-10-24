package com.almarai.easypick.screens

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.almarai.data.easy_pick_models.Result
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenSelfTicketGeneratorBinding
import com.almarai.easypick.utils.alert_dialog.showAlertDialog
import com.almarai.easypick.utils.progress.hideProgress
import com.almarai.easypick.utils.progress.showProgress
import com.almarai.easypick.utils.setTitle
import com.almarai.easypick.view_models.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint

const val FILES_INTENT_RESULT_CODE = 1901

@AndroidEntryPoint
class TicketScreen : Fragment() {
    private val viewModel: TicketViewModel by viewModels()
    private lateinit var screenBinding: ScreenSelfTicketGeneratorBinding
    private lateinit var files: List<Uri>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.screen_self_ticket_generator,
                container,
                false
            )
        screenBinding.apply {
            lifecycleOwner = this@TicketScreen
            viewModel = this@TicketScreen.viewModel
        }

        return screenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        //Set screen title
        setTitle(R.string.title_ticket_generation)

        screenBinding.screenTicketAttachProofButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }

            startActivityForResult(intent, FILES_INTENT_RESULT_CODE)
        }

        screenBinding.screenTicketSendButton.setOnClickListener {
            viewModel.uploadFiles(files)

            observeUploadResult()
        }
    }

    private fun observeUploadResult() {
        viewModel.uploadResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Fetching -> {
                    showProgress(R.string.loading_upload_files)
                }
                is Result.Success -> {
                    hideProgress()

                    showAlertDialog(
                        R.string.alert_upload_ticket_title,
                        getString(R.string.alert_upload_ticket_message) + it.data
                    )
                }
                is Result.Error -> {
                    hideProgress()

                    showAlertDialog(
                        R.string.alert_ticket_upload_failed_title,
                        R.string.alert_ticket_upload_failed_ticket_message
                    )
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILES_INTENT_RESULT_CODE && resultCode == RESULT_OK && data != null) {
            val _files = mutableListOf<Uri>()

            if (data.clipData == null && data.data != null) {
                _files.add(data.data!!)
            } else {
                val count = data.clipData?.itemCount ?: 0

                for (i in 0 until count) {
                    val path = data.clipData?.getItemAt(i)?.uri

                    if (path != null)
                        _files.add(path)
                }
            }

            files = _files
        } else if (data?.clipData == null) {
            showAlertDialog(alertMessage = R.string.alert_selected_data_null_message)
        }
    }
}