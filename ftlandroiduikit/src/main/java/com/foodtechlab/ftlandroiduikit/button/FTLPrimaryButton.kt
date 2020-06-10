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
class FTLPrimaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    init {
        background = ContextCompat.getDrawable(
            context,
            R.drawable.selector_ftl_primary_button
        )

        isAllCaps = true

        textSize = DEFAULT_TEXT_SIZE

        setTextColor(ContextCompat.getColor(context, R.color.BackgroundPrimary))

        if (!isInEditMode) {
            typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
        }

        gravity = Gravity.CENTER
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE = 14f
    }
}