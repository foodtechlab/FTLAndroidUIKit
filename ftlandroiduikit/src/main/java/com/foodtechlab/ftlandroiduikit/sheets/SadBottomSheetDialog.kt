package com.foodtechlab.ftlandroiduikit.sheets

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.foodtechlab.ftlandroiduikit.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Umalt on 20.05.2020
 */
class SadBottomSheetDialog : BottomSheetDialogFragment(), View.OnClickListener {

    private val title by lazy {
        val strRes = arguments?.getInt(KEY_TITLE, -1) ?: -1
        if (strRes != -1) getString(strRes) else null
    }

    private val message by lazy {
        val strRes = arguments?.getInt(KEY_MESSAGE, -1) ?: -1
        if (strRes != -1) getString(strRes) else null
    }

    private val neutralBtnVisibility by lazy {
        arguments?.getInt(KEY_NEUTRAL_BTN_VISIBILITY, View.VISIBLE) ?: View.VISIBLE
    }

    private val positiveBtnVisibility by lazy {
        arguments?.getInt(KEY_POSITIVE_BTN_VISIBILITY, View.GONE) ?: View.GONE
    }

    private val negativeBtnVisibility by lazy {
        arguments?.getInt(KEY_NEGATIVE_BTN_VISIBILITY, View.GONE) ?: View.GONE
    }

    private var onClickListener: View.OnClickListener? = null

    private lateinit var tvTitle: TextView
    private lateinit var tvMessage: TextView

    private lateinit var btnNeutral: Button
    private lateinit var btnPositive: Button
    private lateinit var btnNegative: Button

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
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_sad, container, false)

        tvTitle = view.findViewById(R.id.tv_dialog_sad_title)
        tvMessage = view.findViewById(R.id.tv_dialog_sad_message)

        btnNeutral = view.findViewById(R.id.btn_dialog_sad_neutral)
        btnPositive = view.findViewById(R.id.btn_dialog_sad_positive)
        btnNegative = view.findViewById(R.id.btn_dialog_sad_negative)

        setupUI()

        return view
    }

    override fun onClick(v: View) {
        onClickListener?.onClick(v)
    }

    private fun setupUI() {
        tvTitle.text = title
        tvMessage.text = message

        btnNeutral.visibility = neutralBtnVisibility
        btnPositive.visibility = positiveBtnVisibility
        btnNegative.visibility = negativeBtnVisibility

        context?.let {
            var backgroundStateList = R.drawable.selector_dialog_sad_btn_neutral_first
            var colorStateList = R.color.selector_dialog_sad_btn_neutral_first
            when {
                positiveBtnVisibility == View.VISIBLE && negativeBtnVisibility == View.GONE ||
                        positiveBtnVisibility == View.GONE && negativeBtnVisibility == View.VISIBLE -> {
                    backgroundStateList = R.drawable.selector_dialog_sad_btn_neutral_second
                    colorStateList = R.color.selector_dialog_sad_btn_neutral_second
                }
                positiveBtnVisibility == View.VISIBLE && negativeBtnVisibility == View.VISIBLE -> {
                    backgroundStateList = R.drawable.selector_dialog_sad_btn_neutral_third
                    colorStateList = R.color.selector_dialog_sad_btn_neutral_third
                }
            }

            btnNeutral.background = ContextCompat.getDrawable(it, backgroundStateList)

            btnNeutral.setTextColor(ContextCompat.getColorStateList(it, colorStateList))
        }

        btnNeutral.invalidate()
        btnNeutral.requestLayout()

        btnNeutral.setOnClickListener(this)
        btnPositive.setOnClickListener(this)
        btnNegative.setOnClickListener(this)
    }

    companion object {
        const val TAG = "SadBottomSheetDialog"

        private const val KEY_TITLE = "key_title"
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_NEUTRAL_BTN_VISIBILITY = "key_neutral_btn_visibility"
        private const val KEY_POSITIVE_BTN_VISIBILITY = "key_positive_btn_visibility"
        private const val KEY_NEGATIVE_BTN_VISIBILITY = "key_negative_btn_visibility"

        fun newInstance(
            @StringRes titleResId: Int,
            @StringRes messageResId: Int,
            neutralBtnVisibility: Int = View.VISIBLE,
            positiveBtnVisibility: Int = View.GONE,
            negativeBtnVisibility: Int = View.GONE
        ) = SadBottomSheetDialog().apply {
            arguments = Bundle().apply {
                putInt(KEY_TITLE, titleResId)
                putInt(KEY_MESSAGE, messageResId)
                putInt(KEY_NEUTRAL_BTN_VISIBILITY, neutralBtnVisibility)
                putInt(KEY_POSITIVE_BTN_VISIBILITY, positiveBtnVisibility)
                putInt(KEY_NEGATIVE_BTN_VISIBILITY, negativeBtnVisibility)
            }
        }
    }
}