package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType


class FTLDoubleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var tvStartSlot: TextView
    private var tvEndSlot: TextView
    private var ivImageSlot: ImageView
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
            ivImageSlot.visibility = if(field) View.VISIBLE else View.INVISIBLE
        }

    @ColorRes
    var backgroundColorRes = -1
        set(value) {
            field = value
            ivImageSlot.backgroundTintList = ColorStateList.valueOf(field)
        }

    @ColorRes
    var imageColorRes = -1
        set(value) {
            field = value
            ivImageSlot.setColorFilter(field)
        }

    init {
        inflate(context, R.layout.layout_ftl_double_text_view, this)
        tvStartSlot = findViewById(R.id.tv_start_slot)
        tvEndSlot = findViewById(R.id.tv_end_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLDoubleTextView) {
            imageType = ImageType.values()[getInt(R.styleable.FTLDoubleTextView_imageType, 3)]
            textForStartSlot = getString(R.styleable.FTLDoubleTextView_textForStartSlot) ?: ""
            textForEndSlot = getString(R.styleable.FTLDoubleTextView_textForEndSlot) ?: ""
            isBoldStyleForStartSlot = getBoolean(R.styleable.FTLDoubleTextView_isBoldStyleForStartSlot, false)
            isBoldStyleForEndSlot = getBoolean(R.styleable.FTLDoubleTextView_isBoldStyleForEndSlot, true)
            isVisibleImageSlot = getBoolean(R.styleable.FTLDoubleTextView_isVisibleImageSlot, false)
            backgroundColorRes = getColor(
                R.styleable.FTLDoubleTextView_backgroundColorRes,
                ContextCompat.getColor(context, R.color.AdditionalDarkBlue)
            )
            imageColorRes = getColor(
                R.styleable.FTLDoubleTextView_imageColorRes,
                ContextCompat.getColor(context, R.color.BackgroundPrimary)
            )
        }
    }
}