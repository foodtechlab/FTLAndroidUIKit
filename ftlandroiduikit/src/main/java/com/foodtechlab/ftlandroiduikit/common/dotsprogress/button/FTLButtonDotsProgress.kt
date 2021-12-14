package com.foodtechlab.ftlandroiduikit.common.dotsprogress.button

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.BaseDotsProgress
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.FTLDotsProgressTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Umalt on 18.06.2020
 */
class FTLButtonDotsProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseDotsProgress(context, attrs, defStyleAttr) {
    override val viewThemeManager: ViewThemeManager<FTLDotsProgressTheme>
        get() = FTLButtonDotsProgressThemeManager()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
            }
        }
    }

    fun onThemeChanged(theme: FTLDotsProgressTheme) {
        dotColor = ContextCompat.getColor(context, theme.dotColor)
        bounceDotColor =
            ContextCompat.getColor(context, theme.bounceDotColor)
    }

    override fun TypedArray.setupDotColor() {
        viewThemeManager.lightTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_dot_color_light,
            viewThemeManager.lightTheme.dotColor
        )
        viewThemeManager.darkTheme?.let {
            it.dotColor = getResourceId(
                R.styleable.FTLDotsProgress_dot_color_dark,
                it.dotColor
            )
        }
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                dotColor =
                    ContextCompat.getColor(
                        context,
                        theme.dotColor
                    )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG,e.message.toString())
                }
            }
        }
    }

    override fun TypedArray.setupBounceDotColor() {
        viewThemeManager.lightTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_bounce_dot_color_light,
            viewThemeManager.lightTheme.bounceDotColor
        )
        viewThemeManager.darkTheme?.let {
            it.bounceDotColor = getResourceId(
                R.styleable.FTLDotsProgress_bounce_dot_color_dark,
                it.bounceDotColor
            )
        }
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                bounceDotColor =
                    ContextCompat.getColor(
                        context,
                        theme.bounceDotColor
                    )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    companion object {
        private const val TAG = "FTLButtonDotsProgress"
    }
}