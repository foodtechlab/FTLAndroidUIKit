package com.foodtechlab.ftlandroiduikit.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 16.07.2020
 */
class FTLProgressDialog private constructor() : DialogFragment() {

    private var message: String? = null

    private var tvMessage: TextView? = null

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.layout_ftl_progress_dialog, null)

        tvMessage = view?.findViewById(R.id.tv_ftl_progress_dialog_message)

        tvMessage?.text = message

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .create().apply { setCanceledOnTouchOutside(false) }
    }

    companion object {
        const val TAG = "FTLProgressDialog"

        fun newInstance(msg: String? = null): FTLProgressDialog {
            return FTLProgressDialog().apply {
                message = msg
            }
        }
    }
}