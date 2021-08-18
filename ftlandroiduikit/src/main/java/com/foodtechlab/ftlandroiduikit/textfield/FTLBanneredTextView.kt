package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.FTLCircularCheckBox
import com.foodtechlab.ftlandroiduikit.common.OnFTLCheckedChangeListener
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


class FTLBanneredTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
    var isVisibleCheckbox: Boolean = false
        get() = cbCheckedSlot.isVisible
        private set(value) {
            field = value
            cbCheckedSlot.isVisible = field
        }

    var isCheckedState: Boolean = false
        get() = cbCheckedSlot.isChecked
        private set(value) {
            field = value
            cbCheckedSlot.isChecked = field
        }

    var isBoldStyle: Boolean = false
        set(value) {
            field = value
            tvTextSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    var textForSlot: String = ""
        set(value) {
            field = value
            tvTextSlot.text = field
        }

    var onCheckedChangeListener: OnFTLCheckedChangeListener? = null

    @ColorRes
    private var textBackgroundColor = -1

    @ColorRes
    private var textColor = -1

    private val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, context.dpToPx(CORNERS))
        .build()

    private val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

    private val tvTextSlot: AppCompatTextView
    private val cbCheckedSlot: FTLCircularCheckBox
    private val glVerticalStop: Guideline

    init {
        View.inflate(context, R.layout.layout_ftl_bunnered_text_view, this)

        cbCheckedSlot = findViewById(R.id.cb_ftl_bannered_text_view)
        glVerticalStop = findViewById(R.id.gl_ftl_bannered_text_view)
        tvTextSlot = findViewById(R.id.tv_ftl_bannered_text_view)

        context.withStyledAttributes(attrs, R.styleable.FTLBanneredTextView) {
            textForSlot = getString(R.styleable.FTLBanneredTextView_textForSlot) ?: ""
            isBoldStyle = getBoolean(R.styleable.FTLBanneredTextView_isBoldStyle, false)
            isCheckedState = getBoolean(R.styleable.FTLBanneredTextView_isCheckedState, false)
        }

        cbCheckedSlot.setOnCheckedChangeListener { view, isChecked ->
            onCheckedChangeListener?.onFTLCheckedChanged(view, isChecked)
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

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        updateTextColorTheme(textColor)
        updateBackgroundColorTheme(textBackgroundColor)
    }

    fun updateTextColorTheme(@ColorRes colorRes: Int) {
        textColor = colorRes
        tvTextSlot.setTextColor(
            ContextCompat.getColorStateList(
                context,
                if (textColor != -1) textColor
                else ThemeManager.theme.ftlBanneredTextViewTheme.textColor
            )
        )
    }

    fun updateBackgroundColorTheme(@ColorRes colorRes: Int) {
        textBackgroundColor = colorRes
        shapeDrawable.fillColor = ContextCompat.getColorStateList(
            context,
            if (textBackgroundColor != -1) textBackgroundColor
            else ThemeManager.theme.ftlBanneredTextViewTheme.backgroundColor
        )
        tvTextSlot.background = shapeDrawable
    }

    fun updateSolidCheckedColorTheme(@ColorRes colorRes: Int) {
        cbCheckedSlot.colorSolidChecked = ContextCompat.getColor(context, colorRes)
    }

    fun updateStrokeUncheckedColorTheme(@ColorRes colorRes: Int) {
        cbCheckedSlot.colorStrokeUnchecked = ContextCompat.getColor(context, colorRes)
    }

    fun setVisibilityOfCheckbox(shouldVisible: Boolean, showAnimation: Boolean) {
        // Важный момент! В случае если чекбокс нужно отобразить, необходимо это сделать уже
        // после перепривязки. В противоположном случае, необходимо сначала убрать чекбокс
        // и только после этого делать перепривязку
        val set = ConstraintSet()
        set.clone(this)

        if (shouldVisible) {
            set.connect(tvTextSlot.id, ConstraintSet.START, glVerticalStop.id, ConstraintSet.END)
        } else {
            isVisibleCheckbox = false
            set.connect(tvTextSlot.id, ConstraintSet.START, this.id, ConstraintSet.START)
        }

        if (showAnimation) {
            TransitionManager.beginDelayedTransition(this)
        }

        set.applyTo(this)

        isVisibleCheckbox = shouldVisible
    }

    companion object {
        private const val CORNERS = 8F
    }
}