package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FTLFloatingActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr) {

    var fabImageResource = ContextCompat.getDrawable(context, R.drawable.ic_stat_24)
        set(value) {
            field = value
            setImageDrawable(field)
        }

    var fabBackgroundTintList = ContextCompat.getColorStateList(context, R.color.selector_ftl_fab)
        set(value) {
            field = value
            backgroundTintList = field
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLFloatingActionButton) {
            fabImageResource = getDrawable(R.styleable.FTLFloatingActionButton_fabImageResource)
                ?: ContextCompat.getDrawable(context, R.drawable.ic_stat_24)
            fabBackgroundTintList =
                getColorStateList(R.styleable.FTLFloatingActionButton_fabBackgroundTintList)
                    ?: ContextCompat.getColorStateList(context, R.color.selector_ftl_fab)
        }
    }
}