package com.foodtechlab.ftlandroiduikit.button.mixed

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.progress.circle.scale.FTLCircleScaleView
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLMixedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), CoroutineScope {
    var currentProgress: Int = 0
        set(value) {
            field = value
            cpiProgressSlot.currentProgress = field
        }

    var maxProgress: Int = 100
        set(value) {
            field = value
            cpiProgressSlot.maxProgress = field
        }

    var textForSlot: CharSequence?
        get() = tvTextSlot.text
        set(value) {
            tvTextSlot.text = value
        }

    var leftSlotType: MixedLeftSlotType = MixedLeftSlotType.ICON
        set(value) {
            field = value
            when (field) {
                MixedLeftSlotType.ICON -> {
                    cpiProgressSlot.isVisible = false
                    ivImageSlot.isVisible = true
                    placeTextToCenter(false)
                }
                MixedLeftSlotType.PROGRESS -> {
                    cpiProgressSlot.isVisible = true
                    ivImageSlot.isVisible = false
                    placeTextToCenter(false)
                }
                else -> {
                    cpiProgressSlot.isVisible = false
                    ivImageSlot.isVisible = false
                    placeTextToCenter(true)
                }
            }
        }

    var imageType: ImageType = ImageType.ALL_PAID
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    var imageTypeForProgress: ImageType = ImageType.CHECKLIST
        set(value) {
            field = value
            cpiProgressSlot.imageType = field
        }

    @ColorRes
    private var imageColor = -1

    @ColorRes
    private var imageBackgroundColor = -1

    @ColorRes
    private var buttonBackgroundColor = -1

    @ColorRes
    private var buttonTextColor = -1

    private val viewThemeManager: ViewThemeManager<FTLMixedButtonTheme> =
        FTLMixedButtonThemeManager()
    private var ftlMixedButtonTheme: FTLMixedButtonTheme? = viewThemeManager.lightTheme
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var tvTextSlot: TextView
    private var ivImageSlot: ImageView
    private var cpiProgressSlot: FTLCircleScaleView
    private var flLeftSlotContainer: FrameLayout

    init {
        inflate(context, R.layout.layout_ftl_mixed_button, this)
        setBackgroundResource(R.drawable.shape_ftl_mixed_button_background)

        tvTextSlot = findViewById(R.id.tv_mixed_button_text_slot)
        ivImageSlot = findViewById(R.id.iv_mixed_button_image_slot)
        cpiProgressSlot = findViewById(R.id.cpi_mixed_button_progress_slot)
        flLeftSlotContainer = findViewById(R.id.fl_mixed_button)

        context.withStyledAttributes(attrs, R.styleable.FTLMixedButton) {
            textForSlot = getText(R.styleable.FTLMixedButton_textForSlot)
            imageType = ImageType.values()[getInt(
                R.styleable.FTLMixedButton_imageType,
                imageType.ordinal
            )]
            maxProgress = getInt(R.styleable.FTLMixedButton_mixedButton_maxProgress, 100)
            currentProgress = getInt(R.styleable.FTLMixedButton_mixedButton_currentProgress, 0)
            leftSlotType = MixedLeftSlotType.values()[getInt(
                R.styleable.FTLMixedButton_mixedButton_leftSlotType,
                leftSlotType.ordinal
            )]
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

    fun updateProgressTrackColor(@ColorRes colorRes: Int) {
        cpiProgressSlot.updateTrackColorTheme(colorRes)
    }

    fun updateProgressBackgroundTrackColor(@ColorRes colorRes: Int) {
        cpiProgressSlot.updateBackgroundTrackColorTheme(colorRes)
    }

    fun updateProgressImageColor(@ColorRes colorRes: Int) {
        cpiProgressSlot.updateImageColorTheme(colorRes)
    }

    fun updateImageColorTheme(@ColorRes colorRes: Int) {
        imageColor = colorRes
        ivImageSlot.setColorFilter(
            ContextCompat.getColor(
                context,
                if (imageColor != -1) imageColor
                else ftlMixedButtonTheme!!.imageColor
            )
        )
    }

    fun updateImageBackgroundColorTheme(@ColorRes colorRes: Int) {
        imageBackgroundColor = colorRes
        ivImageSlot.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                if (imageBackgroundColor != -1) imageBackgroundColor
                else ftlMixedButtonTheme!!.imageBackgroundColor
            )
        )
    }

    fun updateBackgroundColorTheme(@ColorRes colorRes: Int) {
        buttonBackgroundColor = colorRes
        backgroundTintList = ContextCompat.getColorStateList(
            context,
            if (buttonBackgroundColor != -1) buttonBackgroundColor
            else ftlMixedButtonTheme!!.buttonBackgroundColor
        )
    }

    fun updateTextColorTheme(@ColorRes colorRes: Int) {
        buttonTextColor = colorRes
        tvTextSlot.setTextColor(
            ContextCompat.getColor(
                context,
                if (buttonTextColor != -1) buttonTextColor
                else ftlMixedButtonTheme!!.buttonTextColor
            )
        )
    }

    private fun onThemeChanged(theme: FTLMixedButtonTheme) {
        ftlMixedButtonTheme = theme
        updateImageColorTheme(imageColor)
        updateImageBackgroundColorTheme(imageColor)
        updateBackgroundColorTheme(buttonBackgroundColor)
        updateTextColorTheme(buttonTextColor)
    }

    private fun placeTextToCenter(isCenterText: Boolean) {
        val param: LayoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(
                    context.dpToPxInt(HORIZONTAL_MARGIN),
                    context.dpToPxInt(VERTICAL_MARGIN),
                    context.dpToPxInt(HORIZONTAL_MARGIN),
                    context.dpToPxInt(VERTICAL_MARGIN)
                )
            }
        if (isCenterText) {
            param.removeRule(END_OF)
            param.addRule(CENTER_HORIZONTAL, TRUE)

        } else {
            param.removeRule(CENTER_HORIZONTAL)
            param.addRule(END_OF, flLeftSlotContainer.id)
        }
        tvTextSlot.layoutParams = param
    }

    companion object {
        private const val VERTICAL_MARGIN = 12F
        private const val HORIZONTAL_MARGIN = 8F
    }
}

data class FTLMixedButtonTheme(
    @ColorRes val buttonTextColor: Int,
    @ColorRes val imageColor: Int,
    @ColorRes val imageBackgroundColor: Int,
    @ColorRes val buttonBackgroundColor: Int
) : ViewTheme()
