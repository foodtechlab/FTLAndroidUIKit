package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.DotsLine

class FTLRouteTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var tvAddressFrom: TextView
    private var tvAddressTo: TextView
    private var ivAddressFrom: ImageView
    private var ivAddressTo: ImageView
    private var vRoad: View
    private var dlRoad: DotsLine

    var textAddressFrom: String = ""
        set(value) {
            field = value
            tvAddressFrom.text = field
        }

    var textAddressTo: String = ""
        set(value) {
            field = value
            tvAddressTo.text = field
        }

    var isBoldStyleAddressFrom: Boolean = false
        set(value) {
            field = value
            tvAddressFrom.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    var isBoldStyleAddressTo: Boolean = true
        set(value) {
            field = value
            tvAddressTo.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    @ColorInt
    var backgroundColorRes = ContextCompat.getColor(context, R.color.AdditionalDarkBlue)
        set(value) {
            field = value
            vRoad.backgroundTintList = ColorStateList.valueOf(field)
        }

    @ColorInt
    var imageColorRes = ContextCompat.getColor(context, R.color.BackgroundPrimary)
        set(value) {
            field = value
            ivAddressFrom.setColorFilter(field)
            ivAddressTo.setColorFilter(field)
            dlRoad.lineColorRes = field
        }

    init {
        inflate(context, R.layout.layout_ftl_route_text_view, this)

        tvAddressFrom = findViewById(R.id.tv_address_from)
        tvAddressTo = findViewById(R.id.tv_address_to)
        ivAddressFrom = findViewById(R.id.iv_address_from)
        ivAddressTo = findViewById(R.id.iv_address_to)
        vRoad = findViewById(R.id.v_road)
        dlRoad = findViewById(R.id.dl_road)

        context.withStyledAttributes(attrs, R.styleable.FTLRouteTextView) {
            textAddressFrom = getString(R.styleable.FTLRouteTextView_textAddressFrom) ?: ""
            textAddressTo = getString(R.styleable.FTLRouteTextView_textAddressTo) ?: ""
            isBoldStyleAddressFrom =
                getBoolean(R.styleable.FTLRouteTextView_isBoldStyleAddressFrom, false)
            isBoldStyleAddressTo =
                getBoolean(R.styleable.FTLRouteTextView_isBoldStyleAddressTo, true)
            backgroundColorRes = getColor(
                R.styleable.FTLRouteTextView_backgroundColorRes,
                ContextCompat.getColor(context, R.color.AdditionalDarkBlue)
            )
            imageColorRes = getColor(
                R.styleable.FTLRouteTextView_imageColorRes,
                ContextCompat.getColor(context, R.color.BackgroundPrimary)
            )
        }
    }
}



