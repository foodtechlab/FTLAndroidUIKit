package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.FTLButtonDotsProgress
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 23.06.2020
 */
class FTLButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

    var inProgress = false
    var isWrapContentOnHeight = false
    var text: CharSequence?
        get() = tvText.text
        set(value) {
            tvText.text = value?.toString()
        }

    @ColorRes
    private var textColorRes = -1

    @ColorRes
    private var dotColorRes = -1

    @ColorRes
    private var bounceDotColorRes = -1
    private var buttonType = ButtonType.PRIMARY
    private val displayDensity = resources.displayMetrics.density
    private var backgroundDrawableLightRes: Drawable? = null
    private var backgroundDrawableDarkRes: Drawable? = null
    private var clickListener: OnClickListener? = null
    private var onPreDrawListener: ViewTreeObserver.OnPreDrawListener? = null
    private val fadeTransition = Fade().apply { duration = 200 }
    private val tvText: TextView
    private val dotProgress: FTLButtonDotsProgress

    init {
        inflate(context, R.layout.layout_ftl_button, this)

        minimumHeight = (MIN_HEIGHT * displayDensity).toInt()

        tvText = findViewById(R.id.tv_text)
        dotProgress = findViewById(R.id.dot_progress)
        context.withStyledAttributes(attrs, R.styleable.FTLButton) {
            text = getString(R.styleable.FTLButton_ftlButton_text)

            val ordinal = getInt(R.styleable.FTLButton_ftlButton_type, buttonType.ordinal)
            buttonType = ButtonType.values()[ordinal]
            onThemeChanged(ThemeManager.theme)
            updateViewState(
                getColor(R.styleable.FTLButton_ftlButton_textColor, -1),
                getColor(R.styleable.FTLButton_ftlButton_dotColor, -1),
                getColor(R.styleable.FTLButton_ftlButton_bounceDotColor, -1),
                getColorStateList(R.styleable.FTLButton_ftlButton_textColor),
                getDrawable(R.styleable.FTLButton_ftlButton_backgroundDrawable)
            )
        }

        super.setOnClickListener { if (!inProgress) clickListener?.onClick(it) }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
        setProgressVisibility(inProgress)
        onPreDrawListener?.let {
            tvText.viewTreeObserver.removeOnPreDrawListener(it)
        }
        onPreDrawListener = ViewTreeObserver.OnPreDrawListener {
            if (tvText.width > 0) {
                dotProgress.updateLayoutParams {
                    width = tvText.width
                    height = tvText.height
                }
                if (isWrapContentOnHeight) {
                    minimumHeight = tvText.height
                }
                tvText.viewTreeObserver.removeOnPreDrawListener(onPreDrawListener)
            }
            true
        }
        tvText.viewTreeObserver.addOnPreDrawListener(onPreDrawListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
        tvText.viewTreeObserver.removeOnPreDrawListener(onPreDrawListener)
        onPreDrawListener = null
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        val dotThemeColor: Int
        val bounceDotThemeColor: Int
        when (buttonType) {
            ButtonType.ADDITIONAL -> {
                ButtonType.ADDITIONAL.dotColor = theme.ftlButtonAdditionalTheme.dotColor
                ButtonType.ADDITIONAL.bounceDotColor = theme.ftlButtonAdditionalTheme.bounceDotColor
                ButtonType.ADDITIONAL.textColor = theme.ftlButtonAdditionalTheme.textColor
                dotThemeColor = ButtonType.ADDITIONAL.dotColor
                bounceDotThemeColor = ButtonType.ADDITIONAL.bounceDotColor
            }
            ButtonType.CANCEL -> {
                ButtonType.CANCEL.dotColor = theme.ftlButtonCancelTheme.dotColor
                ButtonType.CANCEL.bounceDotColor = theme.ftlButtonCancelTheme.bounceDotColor
                ButtonType.CANCEL.background = theme.ftlButtonCancelTheme.bgDrawable
                ButtonType.CANCEL.textColor = theme.ftlButtonCancelTheme.textColor
                dotThemeColor = ButtonType.CANCEL.dotColor
                bounceDotThemeColor = ButtonType.CANCEL.bounceDotColor
            }
            ButtonType.SECONDARY -> {
                ButtonType.SECONDARY.dotColor = theme.ftlButtonSecondaryTheme.dotColor
                ButtonType.SECONDARY.bounceDotColor = theme.ftlButtonSecondaryTheme.bounceDotColor
                ButtonType.SECONDARY.background = theme.ftlButtonSecondaryTheme.bgDrawable
                ButtonType.SECONDARY.textColor = theme.ftlButtonSecondaryTheme.textColor
                dotThemeColor = ButtonType.SECONDARY.dotColor
                bounceDotThemeColor = ButtonType.SECONDARY.bounceDotColor
            }
            else -> {
                ButtonType.PRIMARY.dotColor = theme.ftlButtonPrimaryTheme.dotColor
                ButtonType.PRIMARY.bounceDotColor = theme.ftlButtonPrimaryTheme.bounceDotColor
                ButtonType.PRIMARY.background = theme.ftlButtonPrimaryTheme.bgDrawable
                ButtonType.PRIMARY.textColor = theme.ftlButtonPrimaryTheme.textColor
                dotThemeColor = ButtonType.PRIMARY.dotColor
                bounceDotThemeColor = ButtonType.PRIMARY.bounceDotColor
            }
        }

        updateTextColor(textColorRes)
        updateBackgroundDrawable(backgroundDrawableLightRes, backgroundDrawableDarkRes)

        theme.ftlButtonDotsProgressTheme.dotColor =
            if (dotColorRes != -1) dotColorRes else dotThemeColor
        theme.ftlButtonDotsProgressTheme.bounceDotColor =
            if (bounceDotColorRes != -1) bounceDotColorRes else bounceDotThemeColor
    }

    override fun onSaveInstanceState(): Parcelable =
        SavedState(super.onSaveInstanceState()).apply {
            inProgress = this@FTLButton.inProgress
            textColorRes = this@FTLButton.textColorRes
            dotColorRes = this@FTLButton.dotColorRes
            bounceDotColorRes = this@FTLButton.bounceDotColorRes
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            setProgressVisibility(state.inProgress)
            updateTextColor(state.textColorRes)
            updateDotColor(state.dotColorRes)
            updateBounceDotColor(state.bounceDotColorRes)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    fun setProgressVisibility(isVisible: Boolean) {
        TransitionManager.beginDelayedTransition(this, fadeTransition)
        inProgress = isVisible
        tvText.isVisible = !isVisible

        with(dotProgress) {
            if (isVisible) startAnimation()
            else stopAnimation()
            this.isVisible = inProgress
        }
    }

    fun setButtonType(type: ButtonType) {
        clearCustomColors()
        buttonType = type
        onThemeChanged(ThemeManager.theme)
        updateViewState()
    }

    fun updateTextColor(@ColorRes colorRes: Int) {
        textColorRes = colorRes
        if (textColorRes != -1) {
            val colorStateList = ContextCompat.getColorStateList(context, textColorRes)
            if (colorStateList != null) {
                tvText.setTextColor(colorStateList)
            } else {
                tvText.setTextColor(ContextCompat.getColor(context, textColorRes))
            }
        } else {
            setTextColorFromType()
        }
    }

    fun updateDotColor(@ColorRes colorRes: Int) {
        dotColorRes = colorRes
        dotProgress.dotColor = ContextCompat.getColor(
            context,
            if (dotColorRes != -1) dotColorRes else buttonType.dotColor
        )
    }

    fun updateBounceDotColor(@ColorRes colorRes: Int) {
        bounceDotColorRes = colorRes
        dotProgress.bounceDotColor = ContextCompat.getColor(
            context,
            if (bounceDotColorRes != -1) bounceDotColorRes else buttonType.bounceDotColor
        )
    }

    fun updateBackgroundDrawable(drawableForLight: Drawable?, drawableForDark: Drawable?) {
        backgroundDrawableLightRes = drawableForLight
        backgroundDrawableDarkRes = drawableForDark
        val backgroundDrawableRes = when (ThemeManager.theme) {
            ThemeManager.Theme.LIGHT -> backgroundDrawableLightRes
            ThemeManager.Theme.DARK -> backgroundDrawableDarkRes
        }
        background = backgroundDrawableRes ?: buttonType.background?.let {
            ContextCompat.getDrawable(
                context,
                it
            )
        }
    }

    private fun updateViewState(
        @ColorInt textColor: Int = -1,
        @ColorInt dotColor: Int = -1,
        @ColorInt bounceDotColor: Int = -1,
        texColorStateList: ColorStateList? = null,
        backgroundDrawableRes: Drawable? = null
    ) {
        background = backgroundDrawableRes ?: buttonType.background?.let {
            ContextCompat.getDrawable(
                context,
                it
            )
        }

        with(dotProgress) {
            this.dotColor = if (dotColor != -1) {
                dotColor
            } else {
                ContextCompat.getColor(context, buttonType.dotColor)
            }

            this.bounceDotColor = if (bounceDotColor != -1) {
                bounceDotColor
            } else {
                ContextCompat.getColor(context, buttonType.bounceDotColor)
            }
        }

        with(tvText) {
            isAllCaps = buttonType.isAllCaps

            textSize = buttonType.textSize

            when {
                texColorStateList != null -> setTextColor(texColorStateList)
                textColor != -1 -> setTextColor(textColor)
                else -> setTextColorFromType()
            }

            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, buttonType.font)
            }
        }
    }

    private fun clearCustomColors() {
        textColorRes = -1
        dotColorRes = -1
        bounceDotColorRes = -1
    }

    private fun setTextColorFromType() {
        val color = ContextCompat.getColor(context, buttonType.textColor)
        if (color < 0) {
            tvText.setTextColor(ContextCompat.getColorStateList(context, buttonType.textColor))
        } else {
            tvText.setTextColor(color)
        }
    }

    internal class SavedState : BaseSavedState {
        var inProgress = false

        @ColorRes
        var textColorRes = -1

        @ColorRes
        var dotColorRes = -1

        @ColorRes
        var bounceDotColorRes = -1

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            inProgress = parcel.readByte() == 1.toByte()
            textColorRes = parcel.readInt()
            dotColorRes = parcel.readInt()
            bounceDotColorRes = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeByte(if (inProgress) 1 else 0)
            parcel.writeInt(textColorRes)
            parcel.writeInt(dotColorRes)
            parcel.writeInt(bounceDotColorRes)
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