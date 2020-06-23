package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
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

    var title: CharSequence?
        get() = tvTitle.text
        set(value) {
            tvTitle.text = value?.toString()
        }

    private var buttonType = ButtonType.PRIMARY

    private val tvTitle: TextView
    private val dotProgress: DotsProgress

    init {
        inflate(context, R.layout.layout_ftl_button, this)

        minimumHeight = (MIN_HEIGHT * displayDensity).toInt()

        tvTitle = findViewById(R.id.tv_title)
        dotProgress = findViewById(R.id.dot_progress)

        context.withStyledAttributes(attrs, R.styleable.FTLButton) {
            title = getString(R.styleable.FTLButton_ftlButton_title)

            val ordinal = getInt(R.styleable.FTLButton_ftlButton_type, buttonType.ordinal)
            buttonType = ButtonType.values()[ordinal]

            updateViewState()
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

    private fun updateViewState() {
        background = buttonType.background?.let { ContextCompat.getDrawable(context, it) }

        with(dotProgress) {
            dotColor = ContextCompat.getColor(context, buttonType.dotColor)
            bounceDotColor = ContextCompat.getColor(context, buttonType.bounceDotColor)
        }

        with(tvTitle) {
            isAllCaps = buttonType.isAllCaps

            textSize = buttonType.textSize

            val color = ContextCompat.getColor(context, buttonType.textColor)

            if (color < 0) {
                setTextColor(ContextCompat.getColorStateList(context, buttonType.textColor))
            } else {
                setTextColor(color)
            }

            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, buttonType.font)
            }
        }
    }

    fun setProgressVisibility(isVisible: Boolean) {
        inProgress = isVisible
        tvTitle.isVisible = !isVisible

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