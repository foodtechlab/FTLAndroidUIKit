package com.foodtechlab.ftlandroiduikit.button.image

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 27.04.2020
 */
class FTLImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle), ThemeManager.ThemeChangedListener {

    private val displayDensity = resources.displayMetrics.density

    var type = ImageButtonType.NAVIGATOR_SMALL
        private set(value) {
            field = value

            background = ContextCompat.getDrawable(context, value.bgRes)

            minimumWidth = (value.size * displayDensity).toInt()
            minimumHeight = (value.size * displayDensity).toInt()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLImageButton) {
            val typeOrdinal = getInt(R.styleable.FTLImageButton_type, type.ordinal)
            type = ImageButtonType.values()[typeOrdinal]
        }
        onThemeChanged(ThemeManager.theme)
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
        ImageButtonType.NAVIGATOR_SMALL.bgRes = theme.ftlImageButtonTheme.navigatorSmall
        ImageButtonType.NAVIGATOR_MEDIUM.bgRes = theme.ftlImageButtonTheme.navigatorMedium
        ImageButtonType.NAVIGATOR_LARGE.bgRes = theme.ftlImageButtonTheme.navigatorLarge
        ImageButtonType.LOCATION_SMALL.bgRes = theme.ftlImageButtonTheme.locationSmall
        ImageButtonType.LOCATION_MEDIUM.bgRes = theme.ftlImageButtonTheme.locationMedium
        ImageButtonType.LOCATION_LARGE.bgRes = theme.ftlImageButtonTheme.locationLarge
        ImageButtonType.INFO_SMALL.bgRes = theme.ftlImageButtonTheme.infoSmall
        ImageButtonType.INFO_MEDIUM.bgRes = theme.ftlImageButtonTheme.infoMedium
        ImageButtonType.INFO_LARGE.bgRes = theme.ftlImageButtonTheme.infoLarge
        ImageButtonType.REPLAY_SMALL.bgRes = theme.ftlImageButtonTheme.replaySmall
        ImageButtonType.REPLAY_MEDIUM.bgRes = theme.ftlImageButtonTheme.replayMedium
        ImageButtonType.REPLAY_LARGE.bgRes = theme.ftlImageButtonTheme.replayLarge

        background = ContextCompat.getDrawable(context, type.bgRes)
    }
}