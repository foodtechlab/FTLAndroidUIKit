package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.chip.Chip

class FTLTagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Chip(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
    var tagText: String? = null
        set(value) {
            field = value
            text = field
        }

    var tagBackgroundColor = ContextCompat.getColorStateList(context, R.color.TagBackgroundLight)
        set(value) {
            field = value
            chipBackgroundColor = field
        }

    var tagBorderColor = ContextCompat.getColorStateList(context, R.color.TagBorderLight)
        set(value) {
            field = value
            chipStrokeColor = field
        }

    var tagTextColor = ContextCompat.getColorStateList(context, R.color.TextOnColorAdditionalLight)
        set(value) {
            field = value
            setTextColor(field)
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLTagView) {
            tagText = getString(R.styleable.FTLTagView_android_text)
        }

        setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE)
        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
        chipStrokeWidth = context.dpToPx(STROKE_WIDTH)
        isSingleLine = true
        ellipsize = TextUtils.TruncateAt.END
        onThemeChanged(ThemeManager.theme)
        chipEndPadding = context.dpToPx(HORIZONTAL_PADDING)
        chipStartPadding = context.dpToPx(HORIZONTAL_PADDING)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        tagBackgroundColor = ContextCompat.getColorStateList(
            context,
            theme.ftlTagViewTheme.backgroundColor
        )
        tagBorderColor = ContextCompat.getColorStateList(context,  theme.ftlTagViewTheme.borderColor)
        tagTextColor = ContextCompat.getColorStateList(
            context,
            theme.ftlTagViewTheme.textColor
        )
    }

    companion object {
      const val STROKE_WIDTH = 1F
      const val TEXT_SIZE = 14F
      const val HORIZONTAL_PADDING = 8F
    }
}