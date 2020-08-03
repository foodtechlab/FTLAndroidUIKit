package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.*
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher
import com.foodtechlab.ftlandroiduikit.util.dpToPx


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
            tvTextSlot.updateMargins(tvTextSlot.lineCount == 1 && imageType != ImageType.NONE)
        }

    private var tvTextSlot: TextView
    private var ivImageSlot: ImageView

    init {
        inflate(context, R.layout.layout_ftl_section_text_view, this)

        orientation = HORIZONTAL

        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        tvTextSlot.addTextChangedListener(object : TextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                tvTextSlot.updateMargins(tvTextSlot.lineCount == 1 && imageType != ImageType.NONE)
            }
        })

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

    private fun View.updateMargins(setMargin: Boolean) {
        val lParams = layoutParams as LayoutParams
        val marginTop = context.dpToPx(if (setMargin) 4f else 0f).toInt()
        lParams.updateMargins(
            marginStart,
            marginTop,
            marginLeft,
            marginBottom
        )
        layoutParams = lParams
    }
}