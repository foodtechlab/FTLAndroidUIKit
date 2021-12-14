package com.foodtechlab.ftlandroiduikit.map.restaurant.marker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.imageview.placeholder.FTLPlaceholderImageView
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLRestaurantMarker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLRestaurantMarkerTheme> =
        FTLRestaurantMarkerThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var isMultiBrandMarker: Boolean = false
        set(value) {
            field = value
            if (field) ivBrandLogo.setImageResource(R.drawable.ic_multibrand_placeholder)
            vPlaceholderShadow.isVisible = field
        }

    var textOrdersCount: String = ""
        set(value) {
            field = value
            tvCount.text = field
        }

    var brandLogo: Drawable? = null
        get() = ivBrandLogo.drawable
        set(value) {
            field = value
            ivBrandLogo.setImageDrawable(field)
        }

    private val tvCount: TextView

    private val vPlaceholderShadow: View

    private val llRoot: LinearLayout

    val ivBrandLogo: FTLPlaceholderImageView

    init {
        inflate(context, R.layout.layout_ftl_restaurant_marker, this)

        orientation = HORIZONTAL

        tvCount = findViewById(R.id.tv_ftl_restaurant_count)
        vPlaceholderShadow = findViewById(R.id.v_ftl_restaurant_shadow)
        ivBrandLogo = findViewById(R.id.iv_ftl_restaurant_brand_logo)
        llRoot = findViewById(R.id.ll_ftl_restaurant_marker_root)

        context.withStyledAttributes(attrs, R.styleable.FTLRestaurantMarker) {
            brandLogo = getDrawable(R.styleable.FTLRestaurantMarker_brandLogo)
            isMultiBrandMarker = getBoolean(
                R.styleable.FTLRestaurantMarker_isMultiBrandMarker,
                false
            )
            textOrdersCount = getString(R.styleable.FTLRestaurantMarker_textOrdersCount) ?: ""
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun getMarkerBitmap(): Bitmap {
        measure(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val bitmap = Bitmap.createBitmap(
            measuredWidth,
            measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        layout(0, 0, measuredWidth, measuredHeight)
        draw(Canvas(bitmap))
        return bitmap
    }


    private fun onThemeChanged(theme: FTLRestaurantMarkerTheme) {
        llRoot.background = ContextCompat.getDrawable(
            context,
            theme.background
        )
        tvCount.setTextColor(
            ContextCompat.getColor(context, theme.textColor)
        )
    }
}

data class FTLRestaurantMarkerTheme(
    @DrawableRes val background: Int,
    @ColorRes val textColor: Int
) : ViewTheme()
