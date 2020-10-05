package com.foodtechlab.ftlandroiduikit.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 16.07.2020
 */
class FTLProgressDialog private constructor() : DialogFragment(),
    ThemeManager.ThemeChangedListener {

    private var message: String? = null

    private var tvMessage: TextView? = null
    private var llContainer: LinearLayout? = null

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.layout_ftl_progress_dialog, null)
        llContainer = view?.findViewById(R.id.ll_ftl_progress_dialog_container)
        tvMessage = view?.findViewById(R.id.tv_ftl_progress_dialog_message)
        onThemeChanged(ThemeManager.theme)

        tvMessage?.text = message

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .create().apply { setCanceledOnTouchOutside(false) }
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        llContainer?.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                theme.ftlProgressDialogTheme.bgColor
            )
        )
        tvMessage?.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                theme.ftlProgressDialogTheme.textColor
            )
        )
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