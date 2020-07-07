package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.updateMargins
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLSectionTextView @JvmOverloads constructor(
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

    var imageType: ImageType = ImageType.FILE
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    init {
        inflate(context, R.layout.layout_ftl_section_text_view, this)
        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLDefaultTextView) {
            imageType = ImageType.values()[getInt(R.styleable.FTLDefaultTextView_imageType, 3)]
            ivImageSlot.setImageResource(imageType.imgRes)
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

    override fun onDraw(canvas: Canvas) {
        tvTextSlot.updateMargins(tvTextSlot.lineCount == 1)
    }
}