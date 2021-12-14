package com.foodtechlab.ftlandroiduikit.textfield.multiple

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.changeColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLMultipleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), CoroutineScope {
    var imageType: ImageType = ImageType.CASH
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    var textTopStartSlot: String = ""
        set(value) {
            field = value
            tvTopStartSlot.text = field
        }

    var textTopEndSlot: String = ""
        set(value) {
            field = value
            tvTopEndSlot.text = field
        }

    var textBottomStartSlot: String = ""
        set(value) {
            field = value
            tvBottomStartSlot.text = field
        }

    var textBottomEndSlot: String = ""
        set(value) {
            field = value
            tvBottomEndSlot.text = field
        }

    @ColorInt
    var colorBottomEndSlot: Int =
        ContextCompat.getColor(context, R.color.TextOnColorAdditionalLight)
        set(value) {
            field = value
            tvBottomEndSlot.setTextColor(field)
        }

    @ColorInt
    var colorBottomStartSlot: Int =
        ContextCompat.getColor(context, R.color.TextOnColorAdditionalLight)
        set(value) {
            field = value
            tvBottomStartSlot.setTextColor(field)
        }

    var customSourceImage: Drawable?
        get() = ivImageSlot.drawable
        set(value) {
            ivImageSlot.setImageDrawable(value)
        }

    @ColorInt
    var imageBackgroundColor = ContextCompat.getColor(context, R.color.IconBackgroundBlueLight)
        set(value) {
            field = value
            ivImageSlot.background?.changeColor(value)
        }

    @ColorInt
    var imageColor = ContextCompat.getColor(context, R.color.IconPrimaryLight)
        set(value) {
            field = value
            ivImageSlot.setColorFilter(field)
        }

    private val viewThemeManager = FTLMultipleTextViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var ivImageSlot: ImageView
    private var tvTopEndSlot: TextView
    private var tvTopStartSlot: TextView
    private var tvBottomEndSlot: TextView
    private var tvBottomStartSlot: TextView

    init {
        inflate(context, R.layout.layout_ftl_multiple_text_view, this)

        orientation = HORIZONTAL

        tvTopStartSlot = findViewById(R.id.tv_top_start_slot)
        tvBottomStartSlot = findViewById(R.id.tv_bottom_start_slot)
        tvTopEndSlot = findViewById(R.id.tv_top_end_slot)
        tvBottomEndSlot = findViewById(R.id.tv_bottom_end_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLMultipleTextView) {
            imageType = ImageType.values()[getInt(R.styleable.FTLMultipleTextView_imageType, 0)]
            textTopStartSlot = getString(R.styleable.FTLMultipleTextView_textTopStartSlot) ?: ""
            textBottomStartSlot =
                getString(R.styleable.FTLMultipleTextView_textBottomStartSlot) ?: ""
            textTopEndSlot = getString(R.styleable.FTLMultipleTextView_textTopEndSlot) ?: ""
            textBottomEndSlot = getString(R.styleable.FTLMultipleTextView_textBottomEndSlot) ?: ""
            colorBottomStartSlot = getColor(
                R.styleable.FTLMultipleTextView_colorBottomStartSlot,
                ContextCompat.getColor(context, R.color.TextOnColorAdditionalLight)
            )
            colorBottomEndSlot = getColor(
                R.styleable.FTLMultipleTextView_colorBottomEndSlot,
                ContextCompat.getColor(context, R.color.TextOnColorAdditionalLight)
            )
            imageBackgroundColor = getColor(
                R.styleable.FTLMultipleTextView_imageBackgroundColor,
                ContextCompat.getColor(context, R.color.IconBackgroundBlueLight)
            )
            imageColor = getColor(
                R.styleable.FTLMultipleTextView_imageColor,
                ContextCompat.getColor(context, R.color.IconPrimaryLight)
            )
        }
    }

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

    fun onThemeChanged(theme: FTLMultipleTextViewTheme) {
        tvTopEndSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.topSlotsColor
            )
        )
        tvTopStartSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.topSlotsColor
            )
        )
    }
}

data class FTLMultipleTextViewTheme(
    @ColorRes val topSlotsColor: Int
) : ViewTheme()
