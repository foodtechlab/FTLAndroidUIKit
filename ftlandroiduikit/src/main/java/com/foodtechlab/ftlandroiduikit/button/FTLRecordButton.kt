package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.record.RecordState
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

class FTLRecordButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
    var isEnabledState: Boolean = true
        set(value) {
            field = value
            isActivated = field
        }

    var recordState: RecordState = RecordState.STOP
        set(value) {
            field = value
            background = ContextCompat.getDrawable(
                context,
                when (field) {
                    RecordState.RECORDING -> R.drawable.img_ftl_record_button_recording
                    else -> R.drawable.img_ftl_record_button_stop
                }
            )
        }

    private var clickListener: OnClickListener? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLRecordButton) {
            isEnabledState = getBoolean(R.styleable.FTLRecordButton_isEnabledState, true)
            recordState = RecordState.values()[getInt(
                R.styleable.FTLRecordButton_recordState,
                recordState.ordinal
            )]
        }

        super.setOnClickListener {
            if (isEnabledState) {
                recordState = when (recordState) {
                    RecordState.STOP -> RecordState.RECORDING
                    else -> RecordState.STOP
                }
            }

            clickListener?.onClick(this)
        }

        onThemeChanged(ThemeManager.theme)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()

        val bundle = Bundle()
        bundle.putSerializable(KEY_BUNDLE_RECORD_STATE, recordState)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        state?.let {
            if (it is Bundle) {
                recordState = it.getSerializable(KEY_BUNDLE_RECORD_STATE) as RecordState
            }
        }
        super.onRestoreInstanceState(state)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        backgroundTintList = ContextCompat.getColorStateList(
            context,
            theme.ftlRecordButtonTheme.foregroundColor
        )
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    companion object {
        const val KEY_BUNDLE_RECORD_STATE = "KEY_BUNDLE_RECORD_STATE"
    }
}