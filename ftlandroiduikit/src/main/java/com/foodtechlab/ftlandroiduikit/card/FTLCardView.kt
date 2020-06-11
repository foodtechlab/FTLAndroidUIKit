package com.foodtechlab.ftlandroiduikit.card

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    init {
        context.withStyledAttributes(attrs, R.styleable.CardView, R.style.FTLCardViewStyle) {
            radius = context.dpToPx(CARD_RADIUS_DEFAULT)
            cardElevation = context.dpToPx(CARD_ELEVATION_DEFAULT)
            setCardBackgroundColor(ContextCompat.getColor(context, R.color.BackgroundPrimary))
            useCompatPadding = true
        }
        setWillNotDraw(false)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        val paddingValue = context.dpToPx(MARGIN_DEFAULT).toInt()
        super.setPadding(paddingValue, paddingValue, paddingValue, paddingValue)
    }

    companion object {
        private const val MARGIN_DEFAULT = 8f
        private const val CARD_RADIUS_DEFAULT = 12f
        private const val CARD_ELEVATION_DEFAULT = 4f
    }
}