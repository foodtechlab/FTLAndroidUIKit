package com.foodtechlab.ftlandroiduikit.progress.circle.scale

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.*
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToInt

class FTLCircleScaleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLCircleScaleViewTheme> =
        FTLCircleScaleViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var trackThickness: Int = 0
        get() = pbIndicator.trackThickness
        set(value) {
            field = value
            pbIndicator.trackThickness = field
        }

    var currentProgress: Int = 0
        set(value) {
            field = value
            pbIndicator.progress = field
        }

    var maxProgress: Int = 100
        set(value) {
            field = value
            pbIndicator.max = field
        }

    var trackCornerRadius: Int = 0
        get() = pbIndicator.trackCornerRadius
        set(value) {
            field = value
            pbIndicator.trackCornerRadius = field
        }

    var imageType: ImageType = ImageType.CHECKLIST
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    @Px
    var sizeProgress = context.dpToPxInt(32f)
        set(value) {
            field = value
            pbIndicator.indicatorSize = field
        }

    @ColorRes
    private var trackColor = -1

    @ColorRes
    private var backgroundTrackColor = -1

    @ColorRes
    private var imageColor = -1
    private var ivImageSlot: ImageView
    private var pbIndicator: CircularProgressIndicator

    init {
        inflate(context, R.layout.layout_ftl_circle_scale_view, this)

        ivImageSlot = findViewById(R.id.iv_circle_scale_view)
        pbIndicator = findViewById(R.id.cpi_circle_scale_view)

        context.withStyledAttributes(attrs, R.styleable.FTLCircleScaleView) {
            imageType = ImageType.values()[getInt(
                R.styleable.FTLCircleScaleView_imageType,
                imageType.ordinal
            )]
            currentProgress = getInteger(R.styleable.FTLCircleScaleView_currentProgress, 0)
            maxProgress = getInteger(R.styleable.FTLCircleScaleView_maxProgress, 100)
            sizeProgress = getDimension(
                R.styleable.FTLCircleScaleView_sizeProgress,
                context.dpToPx(32f)
            ).roundToInt()

            trackThickness = getDimension(
                R.styleable.FTLCircleScaleView_thickness,
                context.dpToPx(DEFAULT_TRACK_THICKNESS)
            ).toInt()
            trackCornerRadius = getDimension(
                R.styleable.FTLCircleScaleView_cornerRadius,
                context.dpToPx(DEFAULT_CORNERS_RADIUS)
            ).roundToInt()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged()
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun updateTrackColorTheme(@ColorRes colorRes: Int) {
        trackColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                pbIndicator.setIndicatorColor(
                    ContextCompat.getColor(
                        context,
                        if (trackColor != -1) {
                            trackColor
                        } else {
                            theme.trackColor
                        }
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

    fun updateBackgroundTrackColorTheme(@ColorRes colorRes: Int) {
        backgroundTrackColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                pbIndicator.trackColor = ContextCompat.getColor(
                    context,
                    if (backgroundTrackColor != -1) {
                        backgroundTrackColor
                    } else {
                        theme.backgroundTrackColor
                    }
                )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    fun updateImageColorTheme(@ColorRes colorRes: Int) {
        imageColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                ivImageSlot.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        if (imageColor != -1) {
                            imageColor
                        } else {
                            theme.imageColor
                        }
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

    private fun onThemeChanged() {
        updateTrackColorTheme(trackColor)
        updateBackgroundTrackColorTheme(backgroundTrackColor)
        updateImageColorTheme(imageColor)
    }

    companion object {
        private const val DEFAULT_TRACK_THICKNESS = 2F
        private const val DEFAULT_CORNERS_RADIUS = 2F
        const val TAG = "FTLCircleScaleView"
    }
}

data class FTLCircleScaleViewTheme(
    @ColorRes val backgroundTrackColor: Int,
    @ColorRes var trackColor: Int,
    @ColorRes val imageColor: Int
) : ViewTheme()
