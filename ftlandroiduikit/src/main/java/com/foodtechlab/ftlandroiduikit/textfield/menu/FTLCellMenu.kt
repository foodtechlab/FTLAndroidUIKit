package com.foodtechlab.ftlandroiduikit.textfield.menu

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLCellMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialTextView(context, attrs, defStyle),
    CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLCellMenuTheme> =
        FTLCellMenuThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    private fun onThemeChanged(theme: FTLCellMenuTheme) {
        setTextColor(
            ContextCompat.getColor(context, theme.textColor)
        )
        highlightColor = ContextCompat.getColor(context, theme.highlightColor)
    }

    companion object {
        private const val HORIZONTAL_PADDING = 16f
        private const val VERTICAL_PADDING = 6f
        private const val TEXT_SIZE = 16f
    }
}

data class FTLCellMenuTheme(
    @ColorRes val textColor: Int,
    @ColorRes val highlightColor: Int
) : ViewTheme()
