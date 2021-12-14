package com.foodtechlab.ftlandroiduikit.textfield.section

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.circular.FTLCircularCheckBox
import com.foodtechlab.ftlandroiduikit.progress.circle.scale.FTLCircleScaleView
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.textfield.helper.SectionBottomSlotType
import com.foodtechlab.ftlandroiduikit.textfield.helper.SectionLeftSlotType
import com.foodtechlab.ftlandroiduikit.textfield.helper.SectionRightSlotType
import com.foodtechlab.ftlandroiduikit.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class FTLSectionTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), CoroutineScope {
    var isChecked: Boolean = false
        get() = cbRightSlot.isChecked
        set(value) {
            field = value
            cbRightSlot.isChecked = field
        }

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

    var textForTopSlot: String = ""
        set(value) {
            field = value
            tvTopTextSlot.text = field
        }

    var textForBottomSlot: String? = null
        set(value) {
            field = value
            tvBottomTextSlot.text = field
        }

    var leftSlotType: SectionLeftSlotType = SectionLeftSlotType.ICON
        set(value) {
            field = value
            when (field) {
                SectionLeftSlotType.ICON -> {
                    changeStartPaddingForMiddleColumn(true)
                    cpiProgressSlot.isVisible = false
                    ivImageLeftSlot.isVisible = true
                }
                SectionLeftSlotType.PROGRESS -> {
                    changeStartPaddingForMiddleColumn(true)
                    cpiProgressSlot.isVisible = true
                    ivImageLeftSlot.isVisible = false
                }
                else -> {
                    changeStartPaddingForMiddleColumn(false)
                    cpiProgressSlot.isVisible = false
                    ivImageLeftSlot.isVisible = false
                }
            }
        }

    var rightSlotType: SectionRightSlotType = SectionRightSlotType.ICON
        set(value) {
            field = value
            cbRightSlot.isVisible = field != SectionRightSlotType.ICON
            ivImageRightSlot.isVisible = field == SectionRightSlotType.ICON
        }

    var bottomSlotType: SectionBottomSlotType = SectionBottomSlotType.NONE
        set(value) {
            field = value
            when (field) {
                SectionBottomSlotType.ICONS -> {
                    changeConstraintForMiddleTopSlot(true)
                    llIconsContainer.isVisible = true
                    tvBottomTextSlot.isVisible = false
                }
                SectionBottomSlotType.TEXT -> {
                    changeConstraintForMiddleTopSlot(true)
                    llIconsContainer.isVisible = false
                    tvBottomTextSlot.isVisible = true
                }
                else -> {
                    changeConstraintForMiddleTopSlot(false)
                    llIconsContainer.isVisible = false
                    tvBottomTextSlot.isVisible = false
                }
            }
        }

    var imageType: ImageType = ImageType.NONE
        set(value) {
            field = value
            if (value != ImageType.NONE) ivImageLeftSlot.setImageResource(field.imgRes)
        }

    var imageTypeForProgress: ImageType = ImageType.CHECKLIST
        set(value) {
            field = value
            cpiProgressSlot.imageType = field
        }

    var rightSlotDrawable: Drawable?
        get() = ivImageRightSlot.drawable
        set(value) {
            ivImageRightSlot.setImageDrawable(value)
        }

    var onFTLSectionTextViewClickListener: OnFTLSectionTextViewClickListener? = null
    var onFTLSectionTextViewCheckChangeListener: CompoundButton.OnCheckedChangeListener? = null

    @ColorRes
    private var imageBackgroundLightColor = R.color.IconBackgroundDefaultLight

    @ColorRes
    private var imageBackgroundDarkColor = R.color.IconBackgroundDefaultDark

    @ColorRes
    private var imageLightColor = R.color.IconBlueLight

    @ColorRes
    private var imageDarkColor = R.color.IconBlueDark

    @ColorRes
    private var rightImageColor = -1

    private val viewThemeManager: ViewThemeManager<FTLSectionTextViewTheme> =
        FTLSectionTextViewThemeManager()
    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var tvTopTextSlot: TextView
    private var tvBottomTextSlot: TextView
    private var ivImageLeftSlot: ImageView
    private var ivImageRightSlot: ImageView
    private var cpiProgressSlot: FTLCircleScaleView
    private var clContainer: ConstraintLayout
    private var cbRightSlot: FTLCircularCheckBox
    private var llIconsContainer: LinearLayout

    init {
        inflate(context, R.layout.layout_ftl_section_text_view, this)

        tvTopTextSlot = findViewById(R.id.tv_section_text_view_top_slot)
        tvBottomTextSlot = findViewById(R.id.tv_section_text_view_bottom_slot)
        ivImageLeftSlot = findViewById(R.id.iv_section_text_view_image_slot)
        ivImageRightSlot = findViewById(R.id.iv_section_text_view_right_slot)
        cpiProgressSlot = findViewById(R.id.cpi_section_text_view_progress_slot)
        clContainer = findViewById(R.id.cl_section_text_view_container)
        llIconsContainer = findViewById(R.id.ll_section_text_view_bottom_slot)
        cbRightSlot = findViewById(R.id.cb_section_text_view_right_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLSectionTextView) {
            imageType = ImageType.values()[getInt(
                R.styleable.FTLSectionTextView_imageType,
                imageType.ordinal
            )]
            textForTopSlot = getString(R.styleable.FTLSectionTextView_textForTopSlot) ?: ""
            textForBottomSlot = getString(R.styleable.FTLSectionTextView_textForBottomSlot)
            leftSlotType = SectionLeftSlotType.values()[getInt(
                R.styleable.FTLSectionTextView_leftSlotType,
                leftSlotType.ordinal
            )]
            bottomSlotType = SectionBottomSlotType.values()[getInt(
                R.styleable.FTLSectionTextView_bottomSlotType,
                bottomSlotType.ordinal
            )]
            rightSlotType = SectionRightSlotType.values()[getInt(
                R.styleable.FTLSectionTextView_rightSlotType,
                rightSlotType.ordinal
            )]
            if (hasValue(R.styleable.FTLSectionTextView_rightSlotDrawable)) {
                rightSlotDrawable = getDrawable(R.styleable.FTLSectionTextView_rightSlotDrawable)
            }
        }

        ivImageRightSlot.setOnClickListener {
            onFTLSectionTextViewClickListener?.onChildClick(it)
        }
        cbRightSlot.setOnCheckedChangeListener { buttonView, isChecked ->
            onFTLSectionTextViewCheckChangeListener?.onCheckedChanged(buttonView, isChecked)
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

    fun onThemeChanged(theme: FTLSectionTextViewTheme) {
        tvTopTextSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.textColor
            )
        )

        updateRightImageColorTheme(rightImageColor)

        with(ivImageLeftSlot) {
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    when (ThemeManager.theme) {
                        ThemeManager.Theme.LIGHT -> imageBackgroundLightColor
                        ThemeManager.Theme.DARK -> imageBackgroundDarkColor
                    }
                )
            )
            drawable.changeColor(
                ContextCompat.getColor(
                    context,
                    when (ThemeManager.theme) {
                        ThemeManager.Theme.LIGHT -> imageLightColor
                        ThemeManager.Theme.DARK -> imageDarkColor
                    }
                )
            )
        }
    }

    fun updateRightImageColorTheme(@ColorRes colorRes: Int) {
        rightImageColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                ivImageRightSlot.drawable.mutate().changeColor(
                    ContextCompat.getColor(
                        context,
                        if (rightImageColor != -1)
                            rightImageColor
                        else
                            theme.rightImageColor
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

    fun setRippleBackgroundForRightImage() {
        val typedValue = TypedValue()
        context.theme
            .resolveAttribute(
                android.R.attr.selectableItemBackgroundBorderless,
                typedValue,
                true
            )
        ivImageRightSlot.setBackgroundResource(typedValue.resourceId)
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

    fun updateImageBackgroundColors(
        @ColorRes colorForLightTheme: Int,
        @ColorRes colorForDarkTheme: Int
    ) {
        imageBackgroundLightColor = colorForLightTheme
        imageBackgroundDarkColor = colorForDarkTheme
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    fun updateImageColors(@ColorRes colorForLightTheme: Int, @ColorRes colorForDarkTheme: Int) {
        imageLightColor = colorForLightTheme
        imageDarkColor = colorForDarkTheme
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    fun addIconsForBottomSlot(icons: List<Drawable?>) {
        llIconsContainer.removeAllViews()
        if (icons.size > 5) throw Exception("Too many items count: ${icons.size}. Max count for list = 5")
        icons.forEach {
            val icon = ImageView(context)
            val sizeIcon = context.dpToPxInt(TINY_ICON_SIZE)
            val layoutParams = LinearLayout.LayoutParams(sizeIcon, sizeIcon).apply {
                marginEnd = context.dpToPxInt(TINY_ICON_HORIZONTAL_MARGIN)
            }
            icon.layoutParams = layoutParams
            icon.setImageDrawable(it)
            llIconsContainer.addView(icon)
        }
    }

    fun removeIconsFromBottomSlot() {
        llIconsContainer.removeAllViews()
    }

    private fun changeStartPaddingForMiddleColumn(shouldAddPadding: Boolean) {
        val updatedPaddingLeft = if (shouldAddPadding) context.dpToPx(8f).toInt() else 0
        with(tvTopTextSlot) {
            updatePadding(updatedPaddingLeft, paddingTop, paddingRight, paddingBottom)
        }
        with(tvBottomTextSlot) {
            updatePadding(updatedPaddingLeft, paddingTop, paddingRight, paddingBottom)
        }
    }

    private fun changeConstraintForMiddleTopSlot(isVisibleMiddleBottomSlot: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(clContainer)
        if (isVisibleMiddleBottomSlot) {
            constraintSet.clear(R.id.tv_section_text_view_top_slot, ConstraintSet.BOTTOM)
            constraintSet.connect(
                R.id.tv_section_text_view_top_slot,
                ConstraintSet.BOTTOM,
                R.id.b_section_text_view_bottom_row,
                ConstraintSet.TOP
            )
        } else {
            constraintSet.clear(R.id.tv_section_text_view_top_slot, ConstraintSet.BOTTOM)
            constraintSet.connect(
                R.id.tv_section_text_view_top_slot,
                ConstraintSet.BOTTOM,
                R.id.cl_section_text_view_container,
                ConstraintSet.BOTTOM
            )
        }
        constraintSet.applyTo(clContainer)
    }

    companion object {
        private const val TAG = "FTLSectionTextView"
        private const val TINY_ICON_SIZE = 16F
        private const val TINY_ICON_HORIZONTAL_MARGIN = 8F
    }
}

data class FTLSectionTextViewTheme(
    @ColorRes var textColor: Int,
    @ColorRes var rightImageColor: Int
) : ViewTheme()
