package com.foodtechlab.ftlandroiduikit.textfield.default

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
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
import com.foodtechlab.ftlandroiduikit.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToInt

class FTLDefaultTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), CoroutineScope {
    var textForSlot: String = ""
        set(value) {
            field = value
            tvTextSlot.text = field
        }

    var isBoldStyle: Boolean = false
        set(value) {
            field = value
            tvTextSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
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

    private val viewThemeManager: ViewThemeManager<FTLDefaultTextViewTheme> =
        FTLDefaultTextViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var tvTextSlot: TextView

    private var ivImageSlot: ImageView

    init {
        inflate(context, R.layout.layout_ftl_default_text_view, this)

        orientation = HORIZONTAL

        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLDefaultTextView) {
            imageType = ImageType.values()[getInt(R.styleable.FTLDefaultTextView_imageType, 3)]
            textForSlot = getString(R.styleable.FTLDefaultTextView_textForSlot) ?: ""
            isBoldStyle = getBoolean(R.styleable.FTLDefaultTextView_isBoldStyle, false)
            imageBackgroundColor = getColor(
                R.styleable.FTLDefaultTextView_imageBackgroundColor,
                ContextCompat.getColor(context, R.color.IconBackgroundBlueLight)
            )
            imageColor = getColor(
                R.styleable.FTLDefaultTextView_imageColor,
                ContextCompat.getColor(context, R.color.IconPrimaryLight)
            )
        }
        setWillNotDraw(false)
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

    fun onThemeChanged(theme: FTLDefaultTextViewTheme) {
        tvTextSlot.setTextColor(
            ContextCompat.getColor(context, theme.textColor)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (tvTextSlot.lineCount == 1) {
            val paddingTop = context.dpToPx(4f).roundToInt()
            tvTextSlot.setPadding(0, paddingTop, 0, 0)
        }
    }
}

data class FTLDefaultTextViewTheme(
    @ColorRes val textColor: Int
) : ViewTheme()
