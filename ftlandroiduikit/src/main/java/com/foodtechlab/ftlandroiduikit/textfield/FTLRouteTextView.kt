package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R

class FTLRouteTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var tvAddressFrom: TextView
    private var tvAddressTo: TextView

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

    init {
        inflate(context, R.layout.layout_ftl_route_text_view, this)

        tvAddressFrom = findViewById(R.id.tv_address_from)
        tvAddressTo = findViewById(R.id.tv_address_to)

        context.withStyledAttributes(attrs, R.styleable.FTLRouteTextView) {
            textAddressFrom = getString(R.styleable.FTLRouteTextView_textAddressFrom) ?: ""
            textAddressTo = getString(R.styleable.FTLRouteTextView_textAddressTo) ?: ""
            isBoldStyleAddressFrom =
                getBoolean(R.styleable.FTLRouteTextView_isBoldStyleAddressFrom, false)
            isBoldStyleAddressTo =
                getBoolean(R.styleable.FTLRouteTextView_isBoldStyleAddressTo, true)
        }
    }
}



