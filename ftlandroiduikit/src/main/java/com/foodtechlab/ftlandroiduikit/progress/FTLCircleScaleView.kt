package com.foodtechlab.ftlandroiduikit.progress

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlin.math.roundToInt

class FTLCircleScaleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {
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
        }
        onThemeChanged(ThemeManager.theme)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        updateTrackColorTheme(trackColor)
        updateBackgroundTrackColorTheme(backgroundTrackColor)
        updateImageColorTheme(imageColor)
    }

    fun updateTrackColorTheme(@ColorRes colorRes: Int) {
        trackColor = colorRes
        pbIndicator.setIndicatorColor(
            ContextCompat.getColor(
                context,
                if (trackColor != -1)
                    trackColor
                else
                    ThemeManager.theme.ftlCircleScaleViewTheme.trackColor

            )
        )
    }

    fun updateBackgroundTrackColorTheme(@ColorRes colorRes: Int) {
        backgroundTrackColor = colorRes
        pbIndicator.trackColor = ContextCompat.getColor(
            context,
            if (backgroundTrackColor != -1)
                backgroundTrackColor
            else
                ThemeManager.theme.ftlCircleScaleViewTheme.backgroundTrackColor

        )
    }

    fun updateImageColorTheme(@ColorRes colorRes: Int) {
        imageColor = colorRes
        ivImageSlot.setColorFilter(
            ContextCompat.getColor(
                context,
                if (imageColor != -1)
                    imageColor
                else
                    ThemeManager.theme.ftlCircleScaleViewTheme.imageColor
            )
        )
    }
}