package com.foodtechlab.ftlandroiduikit.container.coordinator

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class FTLCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : CoordinatorLayout(context, attrs, defAttrStyle), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLCoordinatorLayoutTheme> =
        FTLCoordinatorLayoutThemeManager()
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
                    setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            theme.bgColor
                        )
                    )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    fun onThemeChanged(theme: FTLCoordinatorLayoutTheme) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.bgColor
            )
        )
    }

    companion object {
        private const val TAG = "FTLCoordinatorLayout"
    }
}

data class FTLCoordinatorLayoutTheme(
    @ColorRes var bgColor: Int
) : ViewTheme()
