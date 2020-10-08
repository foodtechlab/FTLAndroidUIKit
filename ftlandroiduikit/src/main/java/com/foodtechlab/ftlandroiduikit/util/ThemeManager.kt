package com.foodtechlab.ftlandroiduikit.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import kotlin.math.hypot

/**
 * Created by Umalt on 25.09.2020
 */
object ThemeManager {

    var theme = Theme.LIGHT
        set(value) {
            field = value
            listeners.forEach { it.onThemeChanged(value) }
        }

    private val listeners = mutableListOf<ThemeChangedListener>()

    fun addListener(listener: ThemeChangedListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: ThemeChangedListener) {
        listeners.remove(listener)
    }

    fun removeListeners() {
        listeners.clear()
    }

    fun setTheme(
        theme: Theme,
        screenImageView: ImageView,
        container: ViewGroup,
        animate: Boolean = true,
        centerX: Int? = null,
        centerY: Int? = null,
    ) {
        if (!animate) {
            this.theme = theme
            return
        }

        if (screenImageView.isVisible) return

        val w = container.measuredWidth
        val h = container.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        container.draw(Canvas(bitmap))

        screenImageView.setImageBitmap(bitmap)
        screenImageView.isVisible = true

        val finalRadius = hypot(w.toFloat(), h.toFloat())

        this.theme = theme

        val cX = centerX ?: w / 2
        val cY = centerY ?: h / 2

        ViewAnimationUtils.createCircularReveal(screenImageView, cX, cY, finalRadius, 0f).apply {
            duration = 400L
            doOnEnd {
                screenImageView.setImageDrawable(null)
                screenImageView.isVisible = false
            }
            start()
        }
    }

    enum class Theme(
        val ftlLinearLayoutTheme: FTLLinearLayoutTheme,
        val ftlToolbarTheme: FTLToolbarTheme,
        val ftlToolbarDotsProgressTheme: DotsProgressTheme,
        val ftlButtonDotsProgressTheme: DotsProgressTheme,
        val ftlTimerDotsProgressTheme: DotsProgressTheme,
        val ftlPlaceholderImageViewTheme: FTLPlaceholderImageViewTheme,
        val ftlDeliveryTimeViewUsualTheme: FTLDeliveryTimeViewTheme,
        val ftlDeliveryTimeViewUrgentTheme: FTLDeliveryTimeViewTheme,
        val ftlDeliveryTimeViewDeliveredTheme: FTLDeliveryTimeViewTheme,
        val ftlDeliveryTimeViewDeliveredLateTheme: FTLDeliveryTimeViewTheme,
        val ftlDeliveryTimeViewCanceledTheme: FTLDeliveryTimeViewTheme,
        val ftlDeliveryTimeViewInProgressTheme: FTLDeliveryTimeViewTheme,
        val ftlDeliveryTimeViewInProgressLateTheme: FTLDeliveryTimeViewTheme,
        val ftlCircleProgressIndicatorTheme: FTLCircleProgressIndicatorTheme,
        val ftlProgressDialogTheme: FTLProgressDialogTheme,
        val ftlRadioButtonTheme: FTLRadioButtonTheme,
        val ftlSwitchTheme: FTLSwitchTheme,
        val ftlEmptyListImageViewTheme: FTLEmptyListImageViewTheme,
        val ftlCardViewTheme: FTLCardViewTheme,
        val ftlShimmerViewTheme: FTLShimmerViewTheme,
        val ftlShimmerFrameLayoutTheme: FTLShimmerFrameLayoutTheme,
        val ftlFloatingActionButtonTheme: FTLFloatingActionButtonTheme,
        val ftlImageButtonTheme: FTLImageButtonTheme,
        val ftlBottomSheetTheme: FTLBottomSheetTheme,
        val ftlEditTextDefaultTheme: FTLEditTextDefaultTheme,
        val ftlButtonAdditionalTheme: FTLButtonAdditionalTheme,
        val ftlButtonPrimaryTheme: FTLButtonPrimaryTheme,
        val ftlButtonSecondaryTheme: FTLButtonSecondaryTheme,
        val ftlButtonCancelTheme: FTLButtonCancelTheme
    ) {
        LIGHT(
            FTLLinearLayoutTheme(R.color.OnPrimary),
            FTLToolbarTheme(
                R.color.SurfaceSecondLight,
                R.color.TextPrimaryLight,
                R.color.TextSuccessEnabledLight,
                R.color.TextInfoEnabledLight,
                R.color.IconSecondaryLight,
                R.color.IconSecondaryLight,
                R.color.ErrorSuccessLight,
                R.color.ErrorDangerLight,
                R.color.ErrorSuccessLight,
                R.color.ErrorDangerLight
            ),
            DotsProgressTheme(
                R.color.IconGreyLightOpacity60,
                R.color.IconGreyLightOpacity80
            ),
            DotsProgressTheme(
                R.color.TextOnColorPrimaryLightOpacity60,
                R.color.TextOnColorPrimaryLight
            ),
            DotsProgressTheme(
                R.color.IconGreyLightOpacity60,
                R.color.IconGreyLightOpacity80
            ),
            FTLPlaceholderImageViewTheme(R.drawable.ic_restaurant_placeholder_light),
            FTLDeliveryTimeViewTheme(
                R.color.TimeUsualLight,
                R.color.TimeDefaultLight,
                R.color.IconSecondaryLight
            ), // usual
            FTLDeliveryTimeViewTheme(
                R.color.TextOnColorPrimaryLight,
                R.color.TimeUrgentLight,
                R.color.IconPrimaryLight
            ), // urgent
            FTLDeliveryTimeViewTheme(
                R.color.TimerPositiveLight,
                R.color.TimerBackgroundLight,
                R.color.TimerPositiveLight
            ), // delivered
            FTLDeliveryTimeViewTheme(
                R.color.TimerNegativeLight,
                R.color.TimerBackgroundLight,
                R.color.TimerNegativeLight
            ), // delivered late
            FTLDeliveryTimeViewTheme(
                R.color.TextOnColorAdditionalLight,
                R.color.TimerBackgroundLight,
                R.color.IconGreyLight
            ), // canceled
            FTLDeliveryTimeViewTheme(
                R.color.TextOnColorSecondaryLight,
                android.R.color.transparent,
                R.color.IconSecondaryLight
            ), // in progress
            FTLDeliveryTimeViewTheme(
                R.color.TimerNegativeLight,
                android.R.color.transparent,
                R.color.TimerNegativeLight
            ), // in progress late
            FTLCircleProgressIndicatorTheme(R.color.SurfaceFourthLight),
            FTLProgressDialogTheme(R.color.TextOnColorAdditionalLight, R.color.SurfaceFourthLight),
            FTLRadioButtonTheme(R.color.TextPrimaryLight, R.color.ButtonSecondaryEnableLight),
            FTLSwitchTheme(
                R.color.TextPrimaryLight,
                R.color.SwitchTrackEnableLight,
                R.color.ButtonSecondaryEnableLight,
                R.color.ButtonSecondaryEnableLight
            ),
            FTLEmptyListImageViewTheme(R.drawable.ic_placeholder_empty_order_list_light),
            FTLCardViewTheme(R.color.SurfaceFirstLight),
            FTLShimmerViewTheme(R.color.ShimmerBackgroundLight),
            FTLShimmerFrameLayoutTheme(R.color.ShimmerBaseLight, R.color.ShimmerHighlightingLight),
            FTLFloatingActionButtonTheme(R.color.selector_ftl_fab_light),
            FTLImageButtonTheme(
                R.drawable.selector_additional_button_navigator_small_light,
                R.drawable.selector_additional_button_navigator_medium_light,
                R.drawable.selector_additional_button_navigator_large_light,
                R.drawable.selector_additional_button_location_small_light,
                R.drawable.selector_additional_button_location_medium_light,
                R.drawable.selector_additional_button_location_large_light,
                R.drawable.selector_additional_button_info_small_light,
                R.drawable.selector_additional_button_info_medium_light,
                R.drawable.selector_additional_button_info_large_light,
                R.drawable.selector_additional_button_replay_small_light,
                R.drawable.selector_additional_button_replay_medium_light,
                R.drawable.selector_additional_button_replay_large_light
            ),
            FTLBottomSheetTheme(R.color.TextPrimaryLight, R.color.SurfaceFourthLight),
            FTLEditTextDefaultTheme(
                R.color.TextPrimaryLight,
                R.color.TextPlaceholderLight,
                R.color.TextViewPrimaryLight,
                R.color.TextViewDividerEnabledLight,
                R.color.TextViewDividerErrorLight,
                R.color.TextViewDividerFocusedLight
            ),
            FTLButtonAdditionalTheme(
                R.color.selector_ftl_button_additional_light,
                R.color.TextInfoPressedLightOpacity60,
                R.color.TextInfoPressedLight
            ),
            FTLButtonPrimaryTheme(
                R.color.selector_ftl_button_primary_or_secondary_text_light,
                R.color.TextOnColorPrimaryLightOpacity60,
                R.color.TextOnColorPrimaryLight,
                R.drawable.selector_ftl_button_primary_light
            ),
            FTLButtonSecondaryTheme(
                R.color.selector_ftl_button_primary_or_secondary_text_light,
                R.color.TextOnColorPrimaryLightOpacity60,
                R.color.TextOnColorPrimaryLight,
                R.drawable.selector_ftl_button_secondary_light
            ),
            FTLButtonCancelTheme(
                R.color.selector_ftl_button_cancel_text_light,
                R.color.TextDangerEnabledLightOpacity60,
                R.color.TextDangerEnabledLight,
                R.drawable.selector_ftl_button_cancel_light
            )
        ),
        DARK(
            FTLLinearLayoutTheme(R.color.OnSurfaceSecondary),
            FTLToolbarTheme(
                R.color.SurfaceSecondDark,
                R.color.TextPrimaryDark,
                R.color.TextSuccessEnabledDark,
                R.color.TextInfoEnabledDark,
                R.color.IconSecondaryDark,
                R.color.IconSecondaryDark,
                R.color.ErrorSuccessDark,
                R.color.ErrorDangerDark,
                R.color.ErrorSuccessDark,
                R.color.ErrorDangerDark
            ),
            DotsProgressTheme(
                R.color.IconGreyLightOpacity60,
                R.color.IconGreyLightOpacity80
            ),
            DotsProgressTheme(
                R.color.TextOnColorPrimaryDarkOpacity60,
                R.color.TextOnColorPrimaryDark
            ),
            DotsProgressTheme(
                R.color.IconGreyLightOpacity60,
                R.color.IconGreyLightOpacity80
            ),
            FTLPlaceholderImageViewTheme(R.drawable.ic_restaurant_placeholder_dark),
            FTLDeliveryTimeViewTheme(
                R.color.TimeUsualDark,
                R.color.TimeDefaultDark,
                R.color.IconSecondaryDark
            ), // usual
            FTLDeliveryTimeViewTheme(
                R.color.TextOnColorPrimaryDark,
                R.color.TimeUrgentDark,
                R.color.IconPrimaryDark
            ), // urgent
            FTLDeliveryTimeViewTheme(
                R.color.TimerPositiveDark,
                R.color.TimerBackgroundDark,
                R.color.TimerPositiveDark
            ), // delivered
            FTLDeliveryTimeViewTheme(
                R.color.TimerNegativeDark,
                R.color.TimerBackgroundDark,
                R.color.TimerNegativeDark
            ), // delivered late
            FTLDeliveryTimeViewTheme(
                R.color.TextOnColorAdditionalDark,
                R.color.TimerBackgroundDark,
                R.color.IconGreyDark
            ), // canceled
            FTLDeliveryTimeViewTheme(
                R.color.TextOnColorSecondaryDark,
                android.R.color.transparent,
                R.color.IconSecondaryDark
            ), // in progress
            FTLDeliveryTimeViewTheme(
                R.color.TimerNegativeDark,
                android.R.color.transparent,
                R.color.TimerNegativeDark
            ), // in progress late
            FTLCircleProgressIndicatorTheme(R.color.SurfaceFourthDark),
            FTLProgressDialogTheme(R.color.TextOnColorAdditionalDark, R.color.SurfaceFourthDark),
            FTLRadioButtonTheme(R.color.TextPrimaryDark, R.color.ButtonSecondaryEnableDark),
            FTLSwitchTheme(
                R.color.TextPrimaryDark,
                R.color.SwitchTrackEnableDark,
                R.color.ButtonSecondaryEnableDark,
                R.color.ButtonSecondaryEnableDark
            ),
            FTLEmptyListImageViewTheme(R.drawable.ic_placeholder_empty_order_list_dark),
            FTLCardViewTheme(R.color.SurfaceFirstDark),
            FTLShimmerViewTheme(R.color.ShimmerBackgroundDark),
            FTLShimmerFrameLayoutTheme(R.color.ShimmerBaseDark, R.color.ShimmerHighlightingDark),
            FTLFloatingActionButtonTheme(R.color.selector_ftl_fab_dark),
            FTLImageButtonTheme(
                R.drawable.selector_additional_button_navigator_small_dark,
                R.drawable.selector_additional_button_navigator_medium_dark,
                R.drawable.selector_additional_button_navigator_large_dark,
                R.drawable.selector_additional_button_location_small_dark,
                R.drawable.selector_additional_button_location_medium_dark,
                R.drawable.selector_additional_button_location_large_dark,
                R.drawable.selector_additional_button_info_small_dark,
                R.drawable.selector_additional_button_info_medium_dark,
                R.drawable.selector_additional_button_info_large_dark,
                R.drawable.selector_additional_button_replay_small_dark,
                R.drawable.selector_additional_button_replay_medium_dark,
                R.drawable.selector_additional_button_replay_large_dark
            ),
            FTLBottomSheetTheme(R.color.TextPrimaryDark, R.color.SurfaceFourthDark),
            FTLEditTextDefaultTheme(
                R.color.TextPrimaryDark,
                R.color.TextPlaceholderDark,
                R.color.TextViewPrimaryDark,
                R.color.TextViewDividerEnabledDark,
                R.color.TextViewDividerErrorDark,
                R.color.TextViewDividerFocusedDark
            ),
            FTLButtonAdditionalTheme(
                R.color.selector_ftl_button_additional_dark,
                R.color.TextInfoPressedDarkOpacity60,
                R.color.TextInfoPressedDark
            ),
            FTLButtonPrimaryTheme(
                R.color.selector_ftl_button_primary_or_secondary_text_dark,
                R.color.TextOnColorPrimaryDarkOpacity60,
                R.color.TextOnColorPrimaryDark,
                R.drawable.selector_ftl_button_primary_dark
            ),
            FTLButtonSecondaryTheme(
                R.color.selector_ftl_button_primary_or_secondary_text_dark,
                R.color.TextOnColorPrimaryDarkOpacity60,
                R.color.TextOnColorPrimaryDark,
                R.drawable.selector_ftl_button_secondary_dark
            ),
            FTLButtonCancelTheme(
                R.color.selector_ftl_button_cancel_text_dark,
                R.color.TextDangerEnabledLightOpacity60,
                R.color.TextDangerEnabledLight,
                R.drawable.selector_ftl_button_cancel_dark
            )
        )
    }

    data class FTLToolbarTheme(
        @ColorRes val bgColor: Int,
        @ColorRes var titleColor: Int,
        @ColorRes var subtitleColor: Int,
        @ColorRes var actionColor: Int,
        @ColorRes var startIconColor: Int,
        @ColorRes var endIconColor: Int,
        @ColorRes var socketConnected: Int,
        @ColorRes var socketDisconnected: Int,
        @ColorRes var networkConnected: Int,
        @ColorRes var networkDisconnected: Int,
        @DrawableRes var logoIcon: Int = -1
    )

    data class DotsProgressTheme(
        @ColorRes var dotColor: Int,
        @ColorRes var bounceDotColor: Int
    )

    data class FTLPlaceholderImageViewTheme(
        @DrawableRes var placeholder: Int
    )

    data class FTLLinearLayoutTheme(
        @ColorRes val bgColor: Int
    )

    data class FTLDeliveryTimeViewTheme(
        @ColorRes val textColor: Int,
        @ColorRes val bgColor: Int,
        @ColorRes val iconColor: Int? = null
    )

    data class FTLCircleProgressIndicatorTheme(
        @ColorRes val bgColor: Int
    )

    data class FTLProgressDialogTheme(
        @ColorRes val textColor: Int,
        @ColorRes val bgColor: Int
    )

    data class FTLRadioButtonTheme(
        @ColorRes val textColor: Int,
        @ColorRes val uncheckedStateColor: Int
    )

    data class FTLSwitchTheme(
        @ColorRes val textColor: Int,
        @ColorRes val trackColor: Int,
        @ColorRes val thumbColor: Int,
        @ColorRes val highlightColor: Int
    )

    data class FTLEmptyListImageViewTheme(
        @DrawableRes val imgSrc: Int
    )

    data class FTLCardViewTheme(
        @ColorRes val bgColor: Int
    )

    data class FTLShimmerViewTheme(
        @ColorRes val bgColor: Int
    )

    data class FTLShimmerFrameLayoutTheme(
        @ColorRes val baseColor: Int,
        @ColorRes val highlightingColor: Int
    )

    data class FTLFloatingActionButtonTheme(
        @ColorRes val bgColor: Int
    )

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
        @DrawableRes val replayLarge: Int
    )

    data class FTLBottomSheetTheme(
        @ColorRes val messageColor: Int,
        @ColorRes val bgColor: Int
    )

    data class FTLEditTextDefaultTheme(
        @ColorRes val textColor: Int,
        @ColorRes val hintColor: Int,
        @ColorRes val bgColor: Int,
        @ColorRes val defaultControlColor: Int,
        @ColorRes val errorControlColor: Int,
        @ColorRes val activeControlColor: Int
    )

    data class FTLButtonPrimaryTheme(
        @ColorRes val textColor: Int,
        @ColorRes val dotColor: Int,
        @ColorRes val bounceDotColor: Int,
        @DrawableRes val bgDrawable: Int
    )

    data class FTLButtonSecondaryTheme(
        @ColorRes val textColor: Int,
        @ColorRes val dotColor: Int,
        @ColorRes val bounceDotColor: Int,
        @DrawableRes val bgDrawable: Int
    )

    data class FTLButtonCancelTheme(
        @ColorRes val textColor: Int,
        @ColorRes val dotColor: Int,
        @ColorRes val bounceDotColor: Int,
        @DrawableRes val bgDrawable: Int
    )

    data class FTLButtonAdditionalTheme(
        @ColorRes val textColor: Int,
        @ColorRes val dotColor: Int,
        @ColorRes val bounceDotColor: Int
    )

    interface ThemeChangedListener {
        fun onThemeChanged(theme: Theme)
    }
}