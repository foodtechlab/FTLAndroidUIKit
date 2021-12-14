package com.foodtechlab.ftlandroiduikit.button.floating

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLFloatingActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr), CoroutineScope {
    private val ftlViewThemeManager = FTLFloatingActionButtonThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var fabImageResource = ContextCompat.getDrawable(context, R.drawable.ic_stat_24)
        set(value) {
            field = value
            setImageDrawable(field)
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLFloatingActionButton) {
            fabImageResource = getDrawable(R.styleable.FTLFloatingActionButton_fabImageResource)
                ?: ContextCompat.getDrawable(context, R.drawable.ic_stat_24)
        }
        setRippleColor(null)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            ftlViewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun onThemeChanged(theme: FTLFloatingActionButtonTheme) {
        backgroundTintList =
            ContextCompat.getColorStateList(context, theme.bgColor)
    }
}

data class FTLFloatingActionButtonTheme(
    @ColorRes val bgColor: Int
) : ViewTheme()
