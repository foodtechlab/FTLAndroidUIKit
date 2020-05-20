package com.foodtechlab.ftlandroiduikit.sheets

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
import androidx.core.view.*
import androidx.fragment.app.DialogFragment
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.FTLButtonCancel
import com.foodtechlab.ftlandroiduikit.button.FTLButtonSecondary
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Umalt on 20.05.2020
 */
class FTLBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    private val dialogState by lazy {
        arguments?.getParcelable(KEY_DIALOG_STATE) ?: DialogState(
            title = "undefined",
            message = "undefined",
            type = Type.SAD,
            buttons = emptyList()
        )
    }

    private var onClickListener: View.OnClickListener? = null

    private var behavior: BottomSheetBehavior<*>? = null

    private lateinit var ivImage: ImageView

    private lateinit var llContent: LinearLayout

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
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SadBottomSheetDialogStyle)
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

        setupUI()

        return view
    }

    override fun onClick(v: View) {
        onClickListener?.onClick(v)
    }

    private fun setupUI() {
        ivImage.setImageResource(dialogState.type.imgRes)

        tvTitle.text = dialogState.title
        tvMessage.text = dialogState.message

        context?.let { ctx ->
            dialogState.buttons.forEach {
                if (it.isCancelAction) {
                    val view = FTLButtonCancel(ctx).apply {
                        id = it.id
                        text = it.title
                        setOnClickListener(this@FTLBottomSheet)
                    }
                    llContent.addView(view)
                    view.setMarginTop()
                } else {
                    val view = FTLButtonSecondary(ctx).apply {
                        id = it.id
                        text = it.title
                        setOnClickListener(this@FTLBottomSheet)
                    }
                    llContent.addView(view)
                    view.setMarginTop()
                }
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