package com.foodtechlab.ftlandroiduikit.container.constraint

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class FTLConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : ConstraintLayout(context, attrs, defAttrStyle), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLConstraintLayoutTheme> =
        FTLConstraintLayoutThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    fun updateBackgroundColor(@ColorRes lightColor: Int, @ColorRes darkColor: Int) {
        viewThemeManager.darkTheme = viewThemeManager.darkTheme?.copy(bgColor = darkColor)
        viewThemeManager.lightTheme = viewThemeManager.lightTheme.copy(bgColor = lightColor)
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
                try {
                    this.cancel()
                } catch (e: CancellationException) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    private fun onThemeChanged(theme: FTLConstraintLayoutTheme) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.bgColor
            )
        )
    }

    companion object {
        private const val TAG = "FTLConstraintLayout"
    }
}

data class FTLConstraintLayoutTheme(
    @ColorRes var bgColor: Int
) : ViewTheme()
