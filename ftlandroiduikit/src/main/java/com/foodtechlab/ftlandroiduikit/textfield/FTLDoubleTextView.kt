package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ThemeManager


class FTLDoubleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {

    var isBoldStyleForStartSlot: Boolean = false
        set(value) {
            field = value
            tvStartSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    var isBoldStyleForEndSlot: Boolean = true
        set(value) {
            field = value
            tvEndSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    var isVisibleImageSlot: Boolean = false
        set(value) {
            field = value
            ivImageSlot.visibility = if (field) View.VISIBLE else View.INVISIBLE
        }

    var textForStartSlot: String = ""
        set(value) {
            field = value
            tvStartSlot.text = field
        }

    var textForEndSlot: String = ""
        set(value) {
            field = value
            tvEndSlot.text = field
        }

    var imageType: ImageType = ImageType.CUSTOMER
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    private var ivImageSlot: ImageView

    private var tvEndSlot: TextView
    private var tvStartSlot: TextView

    init {
        inflate(context, R.layout.layout_ftl_double_text_view, this)
        tvStartSlot = findViewById(R.id.tv_start_slot)
        tvEndSlot = findViewById(R.id.tv_end_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLDoubleTextView) {
            imageType = ImageType.values()[getInt(R.styleable.FTLDoubleTextView_imageType, 3)]
            textForStartSlot = getString(R.styleable.FTLDoubleTextView_textForStartSlot) ?: ""
            textForEndSlot = getString(R.styleable.FTLDoubleTextView_textForEndSlot) ?: ""
            isBoldStyleForStartSlot =
                getBoolean(R.styleable.FTLDoubleTextView_isBoldStyleForStartSlot, false)
            isBoldStyleForEndSlot =
                getBoolean(R.styleable.FTLDoubleTextView_isBoldStyleForEndSlot, true)
            isVisibleImageSlot = getBoolean(R.styleable.FTLDoubleTextView_isVisibleImageSlot, false)
        }
        onThemeChanged(ThemeManager.theme)
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
        tvStartSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.ftlDoubleTextViewTheme.textColor
            )
        )
        tvEndSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.ftlDoubleTextViewTheme.textColor
            )
        )
        with(ivImageSlot) {
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    ThemeManager.theme.ftlDoubleTextViewTheme.defaultImageBgColor
                )
            )
            setColorFilter(
                ContextCompat.getColor(
                    context,
                    ThemeManager.theme.ftlDoubleTextViewTheme.defaultImageColor
                )
            )
        }
    }

    fun updateImageBackgroundColors(colorForLightTheme: Int, colorForDarkTheme: Int) {
        ThemeManager.Theme.LIGHT.ftlDoubleTextViewTheme.defaultImageBgColor = colorForLightTheme
        ThemeManager.Theme.DARK.ftlDoubleTextViewTheme.defaultImageBgColor = colorForDarkTheme
        ivImageSlot.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlDoubleTextViewTheme.defaultImageBgColor
            )
        )
    }

    fun updateImageColors(colorForLightTheme: Int, colorForDarkTheme: Int) {
        ThemeManager.Theme.LIGHT.ftlDoubleTextViewTheme.defaultImageColor = colorForLightTheme
        ThemeManager.Theme.DARK.ftlDoubleTextViewTheme.defaultImageColor = colorForDarkTheme
        ivImageSlot.setColorFilter(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlDoubleTextViewTheme.defaultImageColor
            )
        )
    }
}