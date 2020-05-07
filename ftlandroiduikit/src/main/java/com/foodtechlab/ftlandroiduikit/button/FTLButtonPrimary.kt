package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 27.04.2020
 */
class FTLButtonPrimary @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    init {
        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.FTLButtonPrimary, defStyle, 0
            )

        background = ContextCompat.getDrawable(
            context,
            R.drawable.selector_ftl_button_primary
        )

        isAllCaps = true

        textSize = DEFAULT_TEXT_SIZE

        setTextColor(ContextCompat.getColor(context, R.color.OnBackground))

        typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)

        typedArray.recycle()
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE = 14f
    }
}