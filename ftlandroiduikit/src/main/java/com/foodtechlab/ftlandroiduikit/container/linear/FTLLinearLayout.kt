package com.foodtechlab.ftlandroiduikit.container.linear

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 25.09.2020
 */
class FTLLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0,
) : LinearLayout(context, attrs, defAttrStyle), CoroutineScope {

    private val viewThemeManager: ViewThemeManager<FTLLinearLayoutTheme> =
        FTLLinearLayoutThemeManager()

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData()
                .collect { theme ->
                    onThemeChanged(theme)
                }
        }
    }

    private fun onThemeChanged(theme: FTLLinearLayoutTheme) {
        setBackgroundColor(ContextCompat.getColor(context, theme.bgColor))
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }
}

data class FTLLinearLayoutTheme(
    @ColorRes val bgColor: Int
) : ViewTheme()
