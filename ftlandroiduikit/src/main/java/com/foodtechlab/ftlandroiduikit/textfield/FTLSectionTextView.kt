package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType

class FTLSectionTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    var textForSlot: String = ""
        set(value) {
            field = value
            tvTextSlot.text = field
        }

    var imageType: ImageType = ImageType.NONE
        set(value) {
            field = value
            ivImageSlot.isVisible = imageType != ImageType.NONE
            if (value != ImageType.NONE) ivImageSlot.setImageResource(field.imgRes)
        }

    private var tvTextSlot: TextView
    private var ivImageSlot: ImageView

    init {
        inflate(context, R.layout.layout_ftl_section_text_view, this)

        orientation = HORIZONTAL
        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLDefaultTextView) {
            imageType = ImageType.values()[getInt(
                R.styleable.FTLDefaultTextView_imageType,
                imageType.ordinal
            )]
            textForSlot = getString(R.styleable.FTLDefaultTextView_textForSlot) ?: ""
            tvTextSlot.text = textForSlot
        }

        setWillNotDraw(false)
    }
}