package com.foodtechlab.ftlandroiduikit.common.divider

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLDivider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLDividerTheme> = FTLDividerThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect {
                onThemeChanged(it)
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun onThemeChanged(theme: FTLDividerTheme) {
        setBackgroundColor(ContextCompat.getColor(context, theme.bgColor))
    }
}

data class FTLDividerTheme(
    @ColorRes var bgColor: Int
) : ViewTheme()
