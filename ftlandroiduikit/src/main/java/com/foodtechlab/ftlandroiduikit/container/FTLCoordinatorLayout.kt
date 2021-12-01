package com.foodtechlab.ftlandroiduikit.container

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class FTLCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : CoordinatorLayout(context, attrs, defAttrStyle), CoroutineScope {
    private var viewThemeManager: ViewThemeManager<FTLCoordinatorLayoutTheme>? = null

    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onAttachedToWindow() {
        viewThemeManager = FTLCoordinatorLayoutThemeManager()
        launch {
            viewThemeManager?.mapToViewData()?.collect { theme ->
                theme?.let {
                    setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            it.bgColor
                        )
                    )
                }
            }
        }
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun updateBackgroundColor(@ColorRes lightColor: Int, @ColorRes darkColor: Int) {
        viewThemeManager?.darkTheme = viewThemeManager?.darkTheme?.copy(bgColor = darkColor)
        viewThemeManager?.lightTheme = viewThemeManager?.lightTheme?.copy(bgColor = lightColor)
        val job = launch {
            viewThemeManager?.mapToViewData()?.collect { theme ->
                theme?.let {
                    setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            it.bgColor
                        )
                    )
                }
            }
        }
        job.cancel()
    }
}

data class FTLCoordinatorLayoutTheme(
    @ColorRes var bgColor: Int
) : ViewTheme()
