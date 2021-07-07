package com.foodtechlab.ftlandroiduikit.textfield.menu

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt
import com.google.android.material.textview.MaterialTextView

class FTLCellMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialTextView(context, attrs, defStyle),
    ThemeManager.ThemeChangedListener {
    init {
        updatePadding(
            context.dpToPxInt(HORIZONTAL_PADDING),
            context.dpToPxInt(VERTICAL_PADDING),
            context.dpToPxInt(HORIZONTAL_PADDING),
            context.dpToPxInt(VERTICAL_PADDING)
        )

        isSingleLine = true
        ellipsize = TextUtils.TruncateAt.END

        setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE)

        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)

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
        setTextColor(
            ContextCompat.getColor(context, theme.ftlCellMenuTheme.textColor)
        )
        highlightColor = ContextCompat.getColor(context, theme.ftlCellMenuTheme.highlightColor)
    }

    companion object {
        private const val HORIZONTAL_PADDING = 16f
        private const val VERTICAL_PADDING = 6f
        private const val TEXT_SIZE = 16f
    }
}