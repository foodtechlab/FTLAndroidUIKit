package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.*
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLDefaultTextView @JvmOverloads constructor(
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

    var isBoldStyle: Boolean = false
        set(value) {
            field = value
            tvTextSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    init {
        inflate(context, R.layout.layout_ftl_default_text_view, this)
        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLDefaultTextView) {
            imageType = ImageType.values()[getInt(R.styleable.FTLDefaultTextView_imageType, 3)]
            ivImageSlot.setImageResource(imageType.imgRes)
            textForSlot = getString(R.styleable.FTLDefaultTextView_textForSlot) ?: ""
            isBoldStyle = getBoolean(R.styleable.FTLDefaultTextView_isBoldStyle, false)
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