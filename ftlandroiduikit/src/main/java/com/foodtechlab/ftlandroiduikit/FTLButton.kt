package com.foodtechlab.ftlandroiduikit

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

/**
 * Created by Umalt on 27.04.2020
 */
class FTLButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FTLButton, defStyle, 0)

        background = ContextCompat.getDrawable(context, R.drawable.selector_ftl_button)

        typedArray.recycle()
    }
}