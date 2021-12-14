package com.foodtechlab.ftlandroiduikit.sheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.ButtonType
import com.foodtechlab.ftlandroiduikit.button.FTLButton
import com.foodtechlab.ftlandroiduikit.common.*
import com.foodtechlab.ftlandroiduikit.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Umalt on 20.05.2020
 */
class FTLBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    var behavior: BottomSheetBehavior<*>? = null

    private val viewThemeManager: ViewThemeManager<FTLBottomSheetTheme> =
        FTLBottomSheetThemeManager()
    private val dialogState by lazy {
        arguments?.getParcelable(KEY_DIALOG_STATE) ?: DialogState(
            title = "undefined",
            message = "undefined",
            type = Type.SAD,
            buttons = emptyList()
        )
    }
    private var onClickListener: View.OnClickListener? = null
    private lateinit var ivImage: ImageView
    private lateinit var llContent: LinearLayout
    private lateinit var llContainer: LinearLayout
    private lateinit var vTop: View
    private lateinit var tvTitle: TextView
    private lateinit var tvMessage: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize listeners here

        onClickListener =
            context as? View.OnClickListener ?: parentFragment as? View.OnClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Work with styles here
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FTLBottomSheetDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Setup the dialog here

        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            behavior = BottomSheetBehavior.from(bottomSheet)
            behavior?.skipCollapsed = true
            behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_ftl_bottom_sheet, container, false)

        tvTitle = view.findViewById(R.id.tv_ftl_bottom_sheet_title)
        tvMessage = view.findViewById(R.id.tv_ftl_bottom_sheet_message)
        ivImage = view.findViewById(R.id.iv_ftl_bottom_sheet_image)
        llContent = view.findViewById(R.id.ll_ftl_bottom_sheet)
        llContainer = view.findViewById(R.id.ll_container)
        vTop = view.findViewById(R.id.v_top)

        onThemeChanged()

        return view
    }

    override fun onDestroyView() {
        removeListenersAndButtonsIfNeed()
        onClickListener = null
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        onClickListener?.onClick(v)
    }

    fun onThemeChanged() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewThemeManager.mapToViewData().flowWithLifecycle(lifecycle).collect { theme ->
                    context?.let { ctx ->
                        llContainer.background.changeColor(
                            ContextCompat.getColor(
                                ctx,
                                theme.bgColor
                            )
                        )
                        vTop.background.changeColor(
                            ContextCompat.getColor(
                                ctx,
                                theme.bgColor
                            )
                        )
                        tvMessage.setTextColor(
                            ContextCompat.getColor(
                                ctx,
                                theme.messageColor
                            )
                        )
                    }
                }
        }
        removeListenersAndButtonsIfNeed()
        setupUI()
    }

    private fun setupUI() {
        ivImage.setImageResource(dialogState.type.imgRes)
        tvTitle.text = dialogState.title
        tvMessage.text = dialogState.message
        context?.let { ctx ->
            dialogState.buttons.forEach {
                val v = FTLButton(ctx).apply {
                    id = it.id
                    text = it.title
                    val type = when (it.buttonType) {
                        PRIMARY_BUTTON -> ButtonType.PRIMARY
                        SECONDARY_BUTTON -> ButtonType.SECONDARY
                        CANCEL_BUTTON -> ButtonType.CANCEL
                        else -> ButtonType.ADDITIONAL
                    }
                    setButtonType(type)
                    setOnClickListener(this@FTLBottomSheet)
                }
                llContent.addView(v)
                v.setMarginTop()
            }
        }
    }

    private fun View.setMarginTop() {
        val lParams = layoutParams as LinearLayout.LayoutParams
        lParams.updateMargins(
            marginLeft,
            context?.dpToPx(BUTTON_MARGIN_TOP)?.toInt() ?: marginTop,
            marginEnd,
            marginBottom
        )
    }

    private fun removeListenersAndButtonsIfNeed() {
        dialogState.buttons.forEach {
            val v = llContainer.findViewById<FTLButton?>(it.id)
            v?.let { button ->
                button.setOnClickListener(null)
                llContent.removeView(button)
            }
        }
    }

    companion object {
        const val TAG = "FTLBottomSheet"

        private const val KEY_DIALOG_STATE = "key_dialog_state"
        private const val BUTTON_MARGIN_TOP = 16f

        fun newInstance(dialogState: DialogState) = FTLBottomSheet().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_DIALOG_STATE, dialogState)
            }
        }
    }
}

data class FTLBottomSheetTheme(
    @ColorRes val messageColor: Int,
    @ColorRes val bgColor: Int
) : ViewTheme()
