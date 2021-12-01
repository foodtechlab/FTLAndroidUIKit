package com.foodtechlab.ftlandroiduikit.card

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle), CoroutineScope {
    init {
        context.withStyledAttributes(
            attrs,
            R.styleable.CardView,
            defStyleRes = R.style.FTLCardViewStyle
        ) {
            radius = context.dpToPx(CARD_RADIUS_DEFAULT)
            cardElevation = context.dpToPx(CARD_ELEVATION_DEFAULT)
        }
    }

    private var viewThemeManager: ViewThemeManager<FTLCardViewTheme>? = null
    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewThemeManager = FTLCardViewThemeManager()
        launch {
            viewThemeManager?.mapToViewData()?.collect { theme ->
                theme?.let {
                    setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            it.bgColor
                        )
                    )
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    companion object {
        private const val CARD_RADIUS_DEFAULT = 12f
        private const val CARD_ELEVATION_DEFAULT = 4f
    }
}

data class FTLCardViewTheme(
    @ColorRes val bgColor: Int
) : ViewTheme()
