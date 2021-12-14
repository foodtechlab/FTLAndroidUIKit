package com.foodtechlab.ftlandroiduikit.button.image

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 27.04.2020
 */
class FTLImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLImageButtonTheme> =
        FTLImageButtonThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private val displayDensity = resources.displayMetrics.density

    var type = ImageButtonType.NAVIGATOR_SMALL
        set(value) {
            field = value

            minimumWidth = (value.size * displayDensity).toInt()
            minimumHeight = (value.size * displayDensity).toInt()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLImageButton) {
            val typeOrdinal = getInt(R.styleable.FTLImageButton_type, type.ordinal)
            type = ImageButtonType.values()[typeOrdinal]
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

    fun onThemeChanged(theme: FTLImageButtonTheme) {
        ImageButtonType.NAVIGATOR_SMALL.bgRes = theme.navigatorSmall
        ImageButtonType.NAVIGATOR_MEDIUM.bgRes = theme.navigatorMedium
        ImageButtonType.NAVIGATOR_LARGE.bgRes = theme.navigatorLarge

        ImageButtonType.LOCATION_SMALL.bgRes = theme.locationSmall
        ImageButtonType.LOCATION_MEDIUM.bgRes = theme.locationMedium
        ImageButtonType.LOCATION_LARGE.bgRes = theme.locationLarge

        ImageButtonType.INFO_SMALL.bgRes = theme.infoSmall
        ImageButtonType.INFO_MEDIUM.bgRes = theme.infoMedium
        ImageButtonType.INFO_LARGE.bgRes = theme.infoLarge

        ImageButtonType.REPLAY_SMALL.bgRes = theme.replaySmall
        ImageButtonType.REPLAY_MEDIUM.bgRes = theme.replayMedium
        ImageButtonType.REPLAY_LARGE.bgRes = theme.replayLarge

        ImageButtonType.TRASH_LARGE.bgRes = theme.trashLarge

        ImageButtonType.RECORD_LARGE.bgRes = theme.recordLarge
        ImageButtonType.STOP_LARGE.bgRes = theme.stopLarge

        ImageButtonType.PLAY_MEDIUM.bgRes = theme.playMedium
        ImageButtonType.PLAY_LARGE.bgRes = theme.playLarge

        ImageButtonType.PAUSE_MEDIUM.bgRes = theme.pauseMedium
        ImageButtonType.PAUSE_LARGE.bgRes = theme.pauseLarge

        type.bgRes?.let {
            background = ContextCompat.getDrawable(context, it)
        }
    }
}

data class FTLImageButtonTheme(
    @DrawableRes val navigatorSmall: Int,
    @DrawableRes val navigatorMedium: Int,
    @DrawableRes val navigatorLarge: Int,

    @DrawableRes val locationSmall: Int,
    @DrawableRes val locationMedium: Int,
    @DrawableRes val locationLarge: Int,

    @DrawableRes val infoSmall: Int,
    @DrawableRes val infoMedium: Int,
    @DrawableRes val infoLarge: Int,

    @DrawableRes val replaySmall: Int,
    @DrawableRes val replayMedium: Int,
    @DrawableRes val replayLarge: Int,

    @DrawableRes val trashLarge: Int,

    @DrawableRes val recordLarge: Int,

    @DrawableRes val stopLarge: Int,

    @DrawableRes val playMedium: Int,
    @DrawableRes val playLarge: Int,

    @DrawableRes val pauseMedium: Int,
    @DrawableRes val pauseLarge: Int
) : ViewTheme()
