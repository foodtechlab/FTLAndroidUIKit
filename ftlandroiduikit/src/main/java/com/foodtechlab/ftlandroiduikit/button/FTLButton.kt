package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.DotsProgress

/**
 * Created by Umalt on 23.06.2020
 */
class FTLButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val displayDensity = resources.displayMetrics.density

    var inProgress = false

    var text: CharSequence?
        get() = tvText.text
        set(value) {
            tvText.text = value?.toString()
        }

    private var buttonType = ButtonType.PRIMARY

    private val tvText: TextView
    private val dotProgress: DotsProgress

    init {
        inflate(context, R.layout.layout_ftl_button, this)

        minimumHeight = (MIN_HEIGHT * displayDensity).toInt()

        tvText = findViewById(R.id.tv_text)
        dotProgress = findViewById(R.id.dot_progress)

        context.withStyledAttributes(attrs, R.styleable.FTLButton) {
            text = getString(R.styleable.FTLButton_ftlButton_text)

            val ordinal = getInt(R.styleable.FTLButton_ftlButton_type, buttonType.ordinal)
            buttonType = ButtonType.values()[ordinal]

            val textColor = getColor(R.styleable.FTLButton_ftlButton_textColor, -1)
            val dotColor = getColor(R.styleable.FTLButton_ftlButton_dotColor, -1)
            val bounceDotColor = getColor(R.styleable.FTLButton_ftlButton_bounceDotColor, -1)

            updateViewState(textColor, dotColor, bounceDotColor)
        }
    }

    override fun onSaveInstanceState(): Parcelable? =
        SavedState(super.onSaveInstanceState()).apply {
            inProgress = this@FTLButton.inProgress
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            setProgressVisibility(state.inProgress)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun updateViewState(
        @ColorInt textColor: Int = -1,
        @ColorInt dotColor: Int = -1,
        @ColorInt bounceDotColor: Int = -1
    ) {
        background = buttonType.background?.let { ContextCompat.getDrawable(context, it) }

        with(dotProgress) {
            this.dotColor = if (dotColor != -1) dotColor else ContextCompat.getColor(
                context,
                buttonType.dotColor
            )

            this.bounceDotColor =
                if (bounceDotColor != -1) bounceDotColor else ContextCompat.getColor(
                    context,
                    buttonType.bounceDotColor
                )
        }

        with(tvText) {
            isAllCaps = buttonType.isAllCaps

            textSize = buttonType.textSize

            if (textColor != -1) {
                setTextColor(textColor)
            } else {
                val color = ContextCompat.getColor(context, buttonType.textColor)

                if (color < 0) {
                    setTextColor(ContextCompat.getColorStateList(context, buttonType.textColor))
                } else {
                    setTextColor(color)
                }
            }

            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, buttonType.font)
            }
        }
    }

    fun setProgressVisibility(isVisible: Boolean) {
        inProgress = isVisible
        tvText.isVisible = !isVisible

        with(dotProgress) {
            if (isVisible) startAnimation()
            else stopAnimation()
            this.isVisible = inProgress
        }
    }

    fun setButtonType(type: ButtonType) {
        buttonType = type
        updateViewState()
    }

    fun setTextColor(@ColorRes colorRes: Int) {
        val color = ContextCompat.getColor(context, colorRes)
        if (color < 0) {
            tvText.setTextColor(ContextCompat.getColorStateList(context, colorRes))
        } else {
            tvText.setTextColor(color)
        }
    }

    fun setDotColor(@ColorRes colorRes: Int) {
        dotProgress.dotColor = ContextCompat.getColor(context, colorRes)
    }

    fun setBounceDotColor(@ColorRes colorRes: Int) {
        dotProgress.dotColor = ContextCompat.getColor(context, colorRes)
    }

    internal class SavedState : BaseSavedState {
        var inProgress = false

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            inProgress = parcel.readByte() == 1.toByte()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeByte(if (inProgress) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {
        private const val MIN_HEIGHT = 48
    }
}