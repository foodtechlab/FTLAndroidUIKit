package com.foodtechlab.ftlandroiduikit.button.image

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 27.04.2020
 */
class FTLImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    private val displayDensity = resources.displayMetrics.density

    var type = ImageButtonType.NAVIGATOR_SMALL
        private set(value) {
            field = value

            background = ContextCompat.getDrawable(context, value.bgRes)

            minimumWidth = (value.size * displayDensity).toInt()
            minimumHeight = (value.size * displayDensity).toInt()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLImageButton) {
            val typeOrdinal = getInt(R.styleable.FTLImageButton_type, type.ordinal)
            type = ImageButtonType.values()[typeOrdinal]
        }
    }
}