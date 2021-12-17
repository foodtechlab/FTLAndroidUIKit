package com.foodtechlab.ftlandroiduikit.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.flowWithLifecycle
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 16.07.2020
 */
class FTLProgressDialog private constructor() : DialogFragment(), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLProgressDialogTheme> =
        FTLProgressDialogThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var message: String? = null

    private var tvMessage: TextView? = null
    private var llContainer: LinearLayout? = null
    private var pbProgress: ProgressBar? = null

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.layout_ftl_progress_dialog, null)
        llContainer = view?.findViewById(R.id.ll_ftl_progress_dialog_container)
        tvMessage = view?.findViewById(R.id.tv_ftl_progress_dialog_message)
        pbProgress = view?.findViewById(R.id.pb_ftl_progress_dialog_progress)

        tvMessage?.text = message

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .create().apply { setCanceledOnTouchOutside(false) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onThemeChanged()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
    }

    fun onThemeChanged() {
        launch {
            viewThemeManager.mapToViewData().flowWithLifecycle(lifecycle).collect { theme ->
                llContainer?.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        theme.bgColor
                    )
                )
                tvMessage?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        theme.textColor
                    )
                )
                pbProgress?.progressTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        theme.accentColor
                    )
                )
            }
        }
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

data class FTLProgressDialogTheme(
    @ColorRes val textColor: Int,
    @ColorRes val bgColor: Int,
    @ColorRes val accentColor: Int
) : ViewTheme()
