package com.foodtechlab.ftlandroiduikit.textfield.double

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class FTLDoubleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), CoroutineScope {
    var isBoldStyleForStartSlot: Boolean = false
        set(value) {
            field = value
            tvStartSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    var isBoldStyleForEndSlot: Boolean = true
        set(value) {
            field = value
            tvEndSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    var isVisibleImageSlot: Boolean = false
        set(value) {
            field = value
            ivImageSlot.visibility = if (field) View.VISIBLE else View.INVISIBLE
        }

    var textForStartSlot: String = ""
        set(value) {
            field = value
            tvStartSlot.text = field
        }

    var textForEndSlot: String = ""
        set(value) {
            field = value
            tvEndSlot.text = field
        }

    var imageType: ImageType = ImageType.CUSTOMER
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
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

    private var ivImageSlot: ImageView

    private var tvEndSlot: TextView
    private var tvStartSlot: TextView

    private val viewThemeManager: ViewThemeManager<FTLDoubleTextViewTheme> =
        FTLDoubleTextViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        inflate(context, R.layout.layout_ftl_double_text_view, this)
        tvStartSlot = findViewById(R.id.tv_start_slot)
        tvEndSlot = findViewById(R.id.tv_end_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLDoubleTextView) {
            imageType = ImageType.values()[getInt(R.styleable.FTLDoubleTextView_imageType, 3)]
            textForStartSlot = getString(R.styleable.FTLDoubleTextView_textForStartSlot) ?: ""
            textForEndSlot = getString(R.styleable.FTLDoubleTextView_textForEndSlot) ?: ""
            isBoldStyleForStartSlot =
                getBoolean(R.styleable.FTLDoubleTextView_isBoldStyleForStartSlot, false)
            isBoldStyleForEndSlot =
                getBoolean(R.styleable.FTLDoubleTextView_isBoldStyleForEndSlot, true)
            isVisibleImageSlot = getBoolean(R.styleable.FTLDoubleTextView_isVisibleImageSlot, false)
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

    fun onThemeChanged(theme: FTLDoubleTextViewTheme) {
        tvStartSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.textColor
            )
        )
        tvEndSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.textColor
            )
        )
    }
}

data class FTLDoubleTextViewTheme(
    @ColorRes var textColor: Int
) : ViewTheme()
