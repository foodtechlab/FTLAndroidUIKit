package com.foodtechlab.ftlandroiduikit.button.additional

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 27.04.2020
 */
class FTLButtonAdditional @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    private val displayDensity = resources.displayMetrics.density

    var size = AdditionalButtonSize.SMALL
        private set(value) {
            field = value
            minimumWidth = (value.size * displayDensity).toInt()
            minimumHeight = (value.size * displayDensity).toInt()
        }

    var type = AdditionalButtonType.TEXT
        private set(value) {
            field = value
            background = value.bgRes?.let { ContextCompat.getDrawable(context, it) }
        }

    init {
        textSize = DEFAULT_TEXT_SIZE

        setTextColor(
            ContextCompat.getColorStateList(
                context,
                R.color.selector_ftl_button_additional
            )
        )

        minimumWidth = (48 * displayDensity).toInt()
        minimumHeight = (48 * displayDensity).toInt()

        if (!isInEditMode) {
            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

        gravity = Gravity.CENTER

        context.withStyledAttributes(attrs, R.styleable.FTLButtonAdditional) {
            val typeOrdinal =
                getInt(R.styleable.FTLButtonAdditional_type, AdditionalButtonType.TEXT.ordinal)
            type = AdditionalButtonType.values()[typeOrdinal]

            val sizeOrdinal =
                getInt(R.styleable.FTLButtonAdditional_size, AdditionalButtonSize.SMALL.ordinal)
            size = AdditionalButtonSize.values()[sizeOrdinal]
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(if (this.type == AdditionalButtonType.TEXT) text else null, type)
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE = 16f
    }
}