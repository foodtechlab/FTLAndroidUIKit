package com.foodtechlab.ftlandroiduikit.util

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.coroutines.CoroutineContext
import kotlin.math.hypot

/**
 * Created by Umalt on 25.09.2020
 */
object ThemeManager : CoroutineScope {
    private val _stateTheme =
        MutableSharedFlow<Theme>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val stateTheme = _stateTheme.asSharedFlow()

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var theme = Theme.LIGHT
        set(value) {
            field = value
            // new flow
            launch {
                _stateTheme.emit(value)
            }
        }

    private var animator: Animator? = null

    fun setTheme(
        theme: Theme,
        screenImageView: ImageView,
        container: ViewGroup,
        animate: Boolean = true,
        centerX: Int? = null,
        centerY: Int? = null,
    ) {
        if (!animate) {
            this.theme = theme
            return
        } else {
            animator?.cancel()
        }

        if (screenImageView.isVisible) return

        val w = container.measuredWidth
        val h = container.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        container.draw(Canvas(bitmap))

        screenImageView.setImageBitmap(bitmap)
        screenImageView.isVisible = true

        this.theme = theme

        val finalRadius = hypot(w.toFloat(), h.toFloat())

        val cX = centerX ?: w / 2
        val cY = centerY ?: h / 2

        animator = ViewAnimationUtils.createCircularReveal(screenImageView, cX, cY, finalRadius, 0f)
            .apply {
                duration = 400L
                doOnCancel {
                    screenImageView.setImageDrawable(null)
                    screenImageView.isVisible = false
                }
                doOnEnd {
                    screenImageView.setImageDrawable(null)
                    screenImageView.isVisible = false
                    animator = null
                }
                start()
            }
    }

    enum class Theme{
        LIGHT,
        DARK
    }


    interface ThemeChangedListener {
        fun onThemeChanged(theme: Theme)
    }
}