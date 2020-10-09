package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor

class FTLSectionTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {

    var textForSlot: String = ""
        set(value) {
            field = value
            tvTextSlot.text = field
        }

    var imageType: ImageType = ImageType.NONE
        set(value) {
            field = value
            ivImageSlot.isVisible = imageType != ImageType.NONE
            if (value != ImageType.NONE) ivImageSlot.setImageResource(field.imgRes)
        }

    private var tvTextSlot: TextView
    private var ivImageSlot: ImageView
    private var ivRightArrow: ImageView

    init {
        inflate(context, R.layout.layout_ftl_section_text_view, this)

        orientation = HORIZONTAL

        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)
        ivRightArrow = findViewById(R.id.iv_right_arrow)

        context.withStyledAttributes(attrs, R.styleable.FTLDefaultTextView) {
            imageType = ImageType.values()[getInt(
                R.styleable.FTLDefaultTextView_imageType,
                imageType.ordinal
            )]
            textForSlot = getString(R.styleable.FTLDefaultTextView_textForSlot) ?: ""
        }
        onThemeChanged(ThemeManager.theme)
        setWillNotDraw(false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        tvTextSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.ftlSectionTextViewTheme.textColor
            )
        )
        ivRightArrow.drawable.changeColor(
            ContextCompat.getColor(
                context,
                theme.ftlSectionTextViewTheme.arrowColor
            )
        )

        with(ivImageSlot) {
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    ThemeManager.theme.ftlSectionTextViewTheme.defaultImageBgColor
                )
            )
            setColorFilter(
                ContextCompat.getColor(
                    context,
                    ThemeManager.theme.ftlSectionTextViewTheme.defaultImageColor
                )
            )
        }
    }

    fun updateImageBackgroundColors(colorForLightTheme: Int, colorForDarkTheme: Int) {
        ThemeManager.Theme.LIGHT.ftlSectionTextViewTheme.defaultImageBgColor = colorForLightTheme
        ThemeManager.Theme.DARK.ftlSectionTextViewTheme.defaultImageBgColor = colorForDarkTheme
        ivImageSlot.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlSectionTextViewTheme.defaultImageBgColor
            )
        )
    }

    fun updateImageColors(colorForLightTheme: Int, colorForDarkTheme: Int) {
        ThemeManager.Theme.LIGHT.ftlSectionTextViewTheme.defaultImageColor = colorForLightTheme
        ThemeManager.Theme.DARK.ftlSectionTextViewTheme.defaultImageColor = colorForDarkTheme
        ivImageSlot.setColorFilter(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlSectionTextViewTheme.defaultImageColor
            )
        )
    }
}