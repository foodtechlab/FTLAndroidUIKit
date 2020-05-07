package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 27.04.2020
 */
class FTLButtonSecondary @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    init {
        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.FTLButtonSecondary, defStyle, 0
            )

        background = ContextCompat.getDrawable(
            context,
            R.drawable.selector_ftl_button_secondary
        )

        isAllCaps = true

        textSize = DEFAULT_TEXT_SIZE

        setTextColor(
            ContextCompat.getColorStateList(
                context,
                R.color.selector_ftl_button_secondary
            )
        )

        if (!isInEditMode) {
            typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
        }

        gravity = Gravity.CENTER

        typedArray.recycle()
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE = 14f
    }
}