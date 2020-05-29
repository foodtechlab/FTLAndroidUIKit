package com.foodtechlab.ftlandroiduikit.card

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.setMargins
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    init {
        context.withStyledAttributes(attrs, R.styleable.CardView, R.style.FTLCardStyle) {
            radius = context.dpToPx(CARD_RADIUS_DEFAULT)
            cardElevation = context.dpToPx(CARD_ELEVATION_DEFAULT)
            setCardBackgroundColor(ContextCompat.getColor(context, R.color.OnBackground))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val margins = layoutParams as MarginLayoutParams
        margins.setMargins(context.dpToPx(MARGIN_DEFAULT).toInt())
        layoutParams = margins
    }

    companion object {
        private const val MARGIN_DEFAULT = 8f
        private const val CARD_RADIUS_DEFAULT = 12f
        private const val CARD_ELEVATION_DEFAULT = 4f
    }
}