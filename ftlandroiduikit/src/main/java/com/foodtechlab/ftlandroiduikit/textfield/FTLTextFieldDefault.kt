package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType


class FTLTextFieldDefault @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var tvTextSlot: TextView
    private var ivImageSlot: ImageView
    var textForSlot: String = ""
        set(value) {
            field = value
            tvTextSlot.text = field

        }

    var imageType: ImageType = ImageType.CUSTOMER
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    init {
        inflate(context, R.layout.layout_ftl_text_field_default, this)
        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLTextFieldDefault) {
            imageType = ImageType.values()[getInt(R.styleable.FTLTextFieldDefault_imageType, 3)]
            ivImageSlot.setImageResource(imageType.imgRes)
            textForSlot = getString(R.styleable.FTLTextFieldDefault_textForSlot) ?: ""
            tvTextSlot.text = textForSlot
        }
    }
}