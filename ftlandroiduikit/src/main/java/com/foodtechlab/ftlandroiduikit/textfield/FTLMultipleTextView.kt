package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType

class FTLMultipleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var tvTopStartSlot: TextView
    private var tvTopEndSlot: TextView
    private var tvBottomStartSlot: TextView
    private var tvBottomEndSlot: TextView
    private var ivImageSlot: ImageView

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
    var colorBottomEndSlot: Int = ContextCompat.getColor(context, R.color.OnSurfacePrimaryAdditionalDark)
        set(value) {
            field = value
            tvBottomEndSlot.setTextColor(field)
        }

    @ColorInt
    var colorBottomStartSlot: Int = ContextCompat.getColor(context, R.color.OnSurfacePrimaryAdditionalDark)
        set(value) {
            field = value
            tvBottomStartSlot.setTextColor(field)
        }

    var customSourceImage: Drawable?
        get() = ivImageSlot.drawable
        set(value) {
            ivImageSlot.setImageDrawable(value)
        }

    init {
        inflate(context, R.layout.layout_ftl_multiple_text_view, this)
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
            ivImageSlot.setImageResource(imageType.imgRes)
            colorBottomStartSlot = getColor(
                R.styleable.FTLMultipleTextView_colorBottomStartSlot,
                ContextCompat.getColor(context, R.color.OnSurfacePrimaryAdditionalDark)
            )
            colorBottomEndSlot = getColor(
                R.styleable.FTLMultipleTextView_colorBottomEndSlot,
                ContextCompat.getColor(context, R.color.OnSurfacePrimaryAdditionalDark)
            )
        }
    }
}


