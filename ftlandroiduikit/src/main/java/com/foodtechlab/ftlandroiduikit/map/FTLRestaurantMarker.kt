package com.foodtechlab.ftlandroiduikit.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.imageview.FTLPlaceholderImageView

class FTLRestaurantMarker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val tvCount: TextView
    private val vPlaceholderShadow: View
    val ivBrandLogo: FTLPlaceholderImageView

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

    var isMultiBrandMarker: Boolean = false
        set(value) {
            field = value
            if (field) ivBrandLogo.setImageResource(R.drawable.ic_multibrand_placeholder)
            vPlaceholderShadow.isVisible = field
        }

    init {
        inflate(context, R.layout.layout_ftl_restaurant_marker, this)
        tvCount = findViewById(R.id.tv_ftl_restaurant_count)
        vPlaceholderShadow = findViewById(R.id.v_ftl_restaurant_shadow)
        ivBrandLogo = findViewById(R.id.iv_ftl_restaurant_brand_logo)

        context.withStyledAttributes(attrs, R.styleable.FTLRestaurantMarker) {
            brandLogo = getDrawable(R.styleable.FTLRestaurantMarker_brandLogo)
            isMultiBrandMarker =
                getBoolean(R.styleable.FTLRestaurantMarker_isMultiBrandMarker, false)
            textOrdersCount = getString(R.styleable.FTLRestaurantMarker_textOrdersCount) ?: ""
        }
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
}