package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.TypedArray
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
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor

class FTLRouteTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {

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
    var routeBackgroundColor = ContextCompat.getColor(
        context,
        ThemeManager.theme.ftlRouteTextViewTheme.routeBackgroundColor
    )
        set(value) {
            field = value
            vRoad.background?.changeColor(value)
        }

    @ColorInt
    var routeItemsColor =
        ContextCompat.getColor(context, ThemeManager.theme.ftlRouteTextViewTheme.routItemsColor)
        set(value) {
            field = value
            ivAddressFrom.setColorFilter(field)
            ivAddressTo.setColorFilter(field)
            dlRoad.lineColor = field
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
            textAddressTo = getString(R.styleable.FTLRouteTextView_textAddressTo) ?: ""
            textAddressFrom = getString(R.styleable.FTLRouteTextView_textAddressFrom) ?: ""

            isBoldStyleAddressFrom = getBoolean(
                R.styleable.FTLRouteTextView_isBoldStyleAddressFrom,
                false
            )

            isBoldStyleAddressTo = getBoolean(
                R.styleable.FTLRouteTextView_isBoldStyleAddressTo,
                true
            )

            setupRouteItemsColor()

            setupRouteBackgroundColor()
        }

        onThemeChanged(ThemeManager.theme)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        tvAddressTo.setTextColor(
            ContextCompat.getColor(
                context,
                theme.ftlRouteTextViewTheme.addressToColor
            )
        )
        tvAddressFrom.setTextColor(
            ContextCompat.getColor(
                context,
                theme.ftlRouteTextViewTheme.addressFromColor
            )
        )
        routeItemsColor = ContextCompat.getColor(
            context,
            theme.ftlRouteTextViewTheme.routItemsColor
        )
        routeBackgroundColor = ContextCompat.getColor(
            context,
            theme.ftlRouteTextViewTheme.routeBackgroundColor
        )
    }

    private fun TypedArray.setupRouteItemsColor() {
        ThemeManager.Theme.LIGHT.ftlRouteTextViewTheme.routItemsColor = getResourceId(
            R.styleable.FTLRouteTextView_routeItemsColorLight,
            ThemeManager.Theme.LIGHT.ftlRouteTextViewTheme.routItemsColor
        )
        ThemeManager.Theme.DARK.ftlRouteTextViewTheme.routItemsColor = getResourceId(
            R.styleable.FTLRouteTextView_routeItemsColorDark,
            ThemeManager.Theme.DARK.ftlRouteTextViewTheme.routItemsColor
        )
        routeItemsColor =
            ContextCompat.getColor(context, ThemeManager.theme.ftlRouteTextViewTheme.routItemsColor)
    }

    private fun TypedArray.setupRouteBackgroundColor() {
        ThemeManager.Theme.LIGHT.ftlRouteTextViewTheme.routeBackgroundColor = getResourceId(
            R.styleable.FTLRouteTextView_routeBackgroundColorLight,
            ThemeManager.Theme.LIGHT.ftlRouteTextViewTheme.routeBackgroundColor
        )
        ThemeManager.Theme.DARK.ftlRouteTextViewTheme.routeBackgroundColor = getResourceId(
            R.styleable.FTLRouteTextView_routeBackgroundColorDark,
            ThemeManager.Theme.DARK.ftlRouteTextViewTheme.routeBackgroundColor
        )
        routeBackgroundColor = ContextCompat.getColor(
            context,
            ThemeManager.theme.ftlRouteTextViewTheme.routeBackgroundColor
        )
    }
}



