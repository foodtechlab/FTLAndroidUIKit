package com.foodtechlab.ftlandroiduikit.imageview.circleimage

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.setPadding
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt

class FTLCircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {
    private var jobTheme: Job? = null

    var iconImage: Drawable? = null
        get() = ivImageCircle.drawable
        set(value) {
            if (value != null) {
                field = value
                ivImageCircle.setImageDrawable(field)
            }
        }

    var iconPadding: Int = context.dpToPxInt(DEFAULT_ICON_PADDING)
        set(value) {
            if (field != value) {
                field = value
                ivImageCircle.setPadding(iconPadding)
            }
        }

    @ColorInt
    var colorBackground: Int = ContextCompat.getColor(context, R.color.IconBackgroundGreyLight)
        set(value) {
            if (value != field) {
                field = value
                setCardBackgroundColor(value)
            }
        }

    var iconElevation: Int = context.dpToPxInt(DEFAULT_ICON_ELEVATION)
        set(value) {
            if (field != value) {
                field = value
                cardElevation = iconElevation.toFloat()
            }
        }

    var type: ImageCircleType = ImageCircleType.QUESTION
        set(value) {
            field = value
            ivImageCircle.setImageResource(field.circleRes)
            ivImageCircle.setPadding(context.dpToPxInt(field.paddingImage))
            cardElevation = field.elevation
            val viewThemeManager =
                when (field) {
                    ImageCircleType.DOUBLE_CHECK -> FTLCircleImageViewDoubleCheckThemeManager()
                    ImageCircleType.MAINTENANCE -> FTLCircleImageViewMaintenanceThemeManager()
                    ImageCircleType.MEDICAL -> FTLCircleImageViewMedicalThemeManager()
                    ImageCircleType.STAR -> FTLCircleImageViewStarThemeManager()
                    else -> FTLCircleImageViewQuestionThemeManager()
                }
            jobTheme?.cancel()
            onThemeChanged(viewThemeManager)
        }

    private val ivImageCircle: ImageView

    init {
        inflate(context, R.layout.layout_ftl_image_circle, this)

        ivImageCircle = findViewById(R.id.iv_image_circle)

        context.withStyledAttributes(attrs, R.styleable.FTLCircleImageView) {
            val typeOrdinal = getInt(R.styleable.FTLCircleImageView_circleType, type.ordinal)
            type = ImageCircleType.values()[typeOrdinal]
            colorBackground =
                getColor(
                    R.styleable.FTLCircleImageView_colorBackground,
                    ContextCompat.getColor(context, R.color.IconBackgroundGreyLight)
                )
            iconPadding = getDimension(
                R.styleable.FTLCircleImageView_iconPadding,
                context.dpToPx(DEFAULT_ICON_PADDING)
            ).roundToInt()
            iconImage = getDrawable(R.styleable.FTLCircleImageView_iconImage)
            iconElevation = getDimension(
                R.styleable.FTLCircleImageView_elevation, context.dpToPx(
                    DEFAULT_ICON_ELEVATION
                )
            ).roundToInt()
        }
    }

    override fun onDetachedFromWindow() {
        jobTheme?.cancel()
        super.onDetachedFromWindow()
    }

    fun onThemeChanged(viewThemeManager: ViewThemeManager<FTLCircleImageViewTheme>) {
        jobTheme = CoroutineScope(Dispatchers.Main).launch {
            viewThemeManager.mapToViewData().collect { theme ->
                colorBackground = ContextCompat.getColor(context, theme.bgColor)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        radius = if (width > height) {
            (height / 2).toFloat()
        } else {
            (width / 2).toFloat()
        }
    }

    companion object {
        const val TAG = "FTLCircleImageView"
        private const val DEFAULT_ICON_PADDING = 8F
        private const val DEFAULT_ICON_ELEVATION = 0F
    }
}

data class FTLCircleImageViewTheme(
    val bgColor: Int
) : ViewTheme()
