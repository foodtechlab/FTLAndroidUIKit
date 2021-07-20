package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
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
import com.foodtechlab.ftlandroiduikit.progress.FTLCircleScaleView
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.textfield.helper.SectionBottomSlotType
import com.foodtechlab.ftlandroiduikit.textfield.helper.SectionLeftSlotType
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt

class FTLSectionTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {
    var currentProgress: Int = 0
        set(value) {
            field = value
            cpiProgress.currentProgress = field
        }

    var maxProgress: Int = 100
        set(value) {
            field = value
            cpiProgress.maxProgress = field
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
                    cpiProgress.isVisible = false
                    ivImageSlot.isVisible = true
                }
                SectionLeftSlotType.PROGRESS -> {
                    changeStartPaddingForMiddleColumn(true)
                    cpiProgress.isVisible = true
                    ivImageSlot.isVisible = false
                }
                else -> {
                    changeStartPaddingForMiddleColumn(false)
                    cpiProgress.isVisible = false
                    ivImageSlot.isVisible = false
                }
            }
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
            if (value != ImageType.NONE) ivImageSlot.setImageResource(field.imgRes)
        }

    @ColorRes
    private var imageBackgroundLightColor = R.color.IconBackgroundDefaultLight

    @ColorRes
    private var imageBackgroundDarkColor = R.color.IconBackgroundDefaultDark

    @ColorRes
    private var imageLightColor = R.color.IconBlueLight

    @ColorRes
    private var imageDarkColor = R.color.IconBlueDark

    private var tvTopTextSlot: TextView
    private var tvBottomTextSlot: TextView
    private var ivImageSlot: ImageView
    private var ivRightArrow: ImageView
    private var cpiProgress: FTLCircleScaleView
    private var clContainer: ConstraintLayout
    private var llIconsContainer: LinearLayout

    init {
        inflate(context, R.layout.layout_ftl_section_text_view, this)

        tvTopTextSlot = findViewById(R.id.tv_section_text_view_top_slot)
        tvBottomTextSlot = findViewById(R.id.tv_section_text_view_bottom_slot)
        ivImageSlot = findViewById(R.id.iv_section_text_view_image_slot)
        ivRightArrow = findViewById(R.id.iv_section_text_view_right_arrow)
        cpiProgress = findViewById(R.id.cpi_section_text_view_progress_slot)
        clContainer = findViewById(R.id.cl_section_text_view_container)
        llIconsContainer = findViewById(R.id.ll_section_text_view_bottom_slot)

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
        }
        onThemeChanged(ThemeManager.theme)
        setWillNotDraw(false)
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
        tvTopTextSlot.setTextColor(
            ContextCompat.getColor(
                context,
                theme.ftlSectionTextViewTheme.textColor
            )
        )
        ivRightArrow.drawable.changeColor(
            ContextCompat.getColor(
                context,
                theme.ftlSectionTextViewTheme.arrowColor
            )
        )

        with(ivImageSlot) {
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    when (theme) {
                        ThemeManager.Theme.LIGHT -> imageBackgroundLightColor
                        ThemeManager.Theme.DARK -> imageBackgroundDarkColor
                    }
                )
            )
            drawable.changeColor(
                ContextCompat.getColor(
                    context,
                    when (theme) {
                        ThemeManager.Theme.LIGHT -> imageLightColor
                        ThemeManager.Theme.DARK -> imageDarkColor
                    }
                )
            )
        }
    }

    fun updateProgressTrackColor(@ColorRes colorRes: Int) {
        cpiProgress.updateTrackColorTheme(colorRes)
    }

    fun updateProgressBackgroundTrackColor(@ColorRes colorRes: Int) {
        cpiProgress.updateBackgroundTrackColorTheme(colorRes)
    }

    fun updateProgressImageColor(@ColorRes colorRes: Int) {
        cpiProgress.updateImageColorTheme(colorRes)
    }

    fun updateImageBackgroundColors(
        @ColorRes colorForLightTheme: Int,
        @ColorRes colorForDarkTheme: Int
    ) {
        imageBackgroundLightColor = colorForLightTheme
        imageBackgroundDarkColor = colorForDarkTheme
        onThemeChanged(ThemeManager.theme)
    }

    fun updateImageColors(@ColorRes colorForLightTheme: Int, @ColorRes colorForDarkTheme: Int) {
        imageLightColor = colorForLightTheme
        imageDarkColor = colorForDarkTheme
        onThemeChanged(ThemeManager.theme)
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
        private const val TINY_ICON_SIZE = 16F
        private const val TINY_ICON_HORIZONTAL_MARGIN = 8F
    }
}