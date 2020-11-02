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
        val ftlRouteTextViewTheme: FTLRouteTextViewTheme,
        val ftlTableHeaderTheme: FTLTableHeaderTheme,
        val ftlEditTextDefaultTheme: FTLEditTextDefaultTheme,
        val ftlButtonAdditionalTheme: FTLButtonAdditionalTheme,
        val ftlButtonPrimaryTheme: FTLButtonPrimaryTheme,
        val ftlButtonSecondaryTheme: FTLButtonSecondaryTheme,
        val ftlButtonCancelTheme: FTLButtonCancelTheme,
        val ftlMultipleTextViewTheme: FTLMultipleTextViewTheme,
        val ftlTimerButtonTheme: FTLTimerButtonTheme,
        val ftlSectionTextViewTheme: FTLSectionTextViewTheme,
        val ftlDoubleTextViewTheme: FTLDoubleTextViewTheme,
        val ftlCoordinatorLayoutTheme: FTLCoordinatorLayoutTheme,
        val ftlConstraintLayoutTheme: FTLConstraintLayoutTheme,
        val ftlTitleTheme: FTLTitleTheme,
        val ftlBottomNavigationViewTheme: FTLBottomNavigationViewTheme,
        val ftlDividerTheme: FTLDividerTheme,
        val ftlDefaultTextViewTheme: FTLDefaultTextViewTheme,
        val ftlExtendableTextViewTheme: FTLExtendableTextViewTheme,
        val ftlTableRowTheme: FTLTableRowTheme,
        val ftlRestaurantMarkerTheme: FTLRestaurantMarkerTheme,
        val ftlSnackbarTheme: FTLSnackbarTheme
    ) {
        LIGHT(
            FTLLinearLayoutTheme(R.color.BackgroundDefaultLight),
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
                R.color.TextOnColorPrimaryOpacity60Light,
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
            FTLProgressDialogTheme(
                R.color.TextOnColorAdditionalLight,
                R.color.SurfaceFourthLight,
                R.color.ProgressPrimaryLight
            ),
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
            FTLRouteTextViewTheme(
                R.color.IconBackgroundBlueLight,
                R.color.IconPrimaryLight,
                R.color.TextPrimaryLight,
                R.color.TextPrimaryLight
            ),
            FTLTableHeaderTheme(
                R.color.DividerPrimaryLight,
                R.color.IconSecondaryLight,
                R.color.TextPrimaryLight,
                R.color.TextOnColorAdditionalLight
            ),
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
                R.color.TextOnColorPrimaryOpacity60Light,
                R.color.TextOnColorPrimaryLight,
                R.drawable.selector_ftl_button_primary_light
            ),
            FTLButtonSecondaryTheme(
                R.color.selector_ftl_button_primary_or_secondary_text_light,
                R.color.TextOnColorPrimaryOpacity60Light,
                R.color.TextOnColorPrimaryLight,
                R.drawable.selector_ftl_button_secondary_light
            ),
            FTLButtonCancelTheme(
                R.color.selector_ftl_button_cancel_text_light,
                R.color.TextDangerEnabledLightOpacity60,
                R.color.TextDangerEnabledLight,
                R.drawable.selector_ftl_button_cancel_light
            ),
            FTLMultipleTextViewTheme(R.color.TextPrimaryLight),
            FTLTimerButtonTheme(
                R.color.TextPlaceholderLight,
                R.color.ButtonSecondaryPressedLight,
                R.color.ButtonSecondaryEnableLight,
                R.color.TextPlaceholderOpacity60Light,
                R.color.TextPlaceholderLight,
                R.color.TextOnColorPrimaryLight,
                R.color.ButtonDangerPressedLight,
                R.color.ButtonDangerEnableLight,
                R.color.TextOnColorPrimaryOpacity60Light,
                R.color.TextOnColorPrimaryLight,
                R.color.TextOnColorPrimaryLight,
                R.color.ButtonSuccessPressedLight,
                R.color.ButtonSuccessEnableLight,
                R.color.TextOnColorPrimaryOpacity60Light,
                R.color.TextOnColorPrimaryLight
            ),
            FTLSectionTextViewTheme(R.color.TextPrimaryLight, R.color.IconSecondaryLight),
            FTLDoubleTextViewTheme(R.color.TextPrimaryLight),
            FTLCoordinatorLayoutTheme(R.color.BackgroundSecondaryLight),
            FTLConstraintLayoutTheme(R.color.BackgroundDefaultLight),
            FTLTitleTheme(R.color.TextPrimaryLight, R.color.TextSuccessEnabledLight),
            FTLBottomNavigationViewTheme(
                R.color.SurfaceSecondLight,
                R.color.selector_ftl_bnv_item_text_color_light,
                R.drawable.selector_ftl_bnv_orders_light,
                R.drawable.selector_ftl_bnv_maps_light,
                R.drawable.selector_ftl_bnv_history_light,
                R.drawable.selector_ftl_bnv_more_light
            ),
            FTLDividerTheme(R.color.DividerPrimaryLight),
            FTLDefaultTextViewTheme(R.color.TextPrimaryLight),
            FTLExtendableTextViewTheme(
                R.color.TextPrimaryLight,
                R.color.TextOnColorAdditionalLight
            ),
            FTLTableRowTheme(
                R.color.TextPrimaryLight,
                R.color.TextOnColorAdditionalLight,
                R.color.TextPrimaryLight
            ),
            FTLRestaurantMarkerTheme(
                R.drawable.shape_ftl_restaurant_marker_light,
                R.color.TextPrimaryLight
            ),
            FTLSnackbarTheme(R.drawable.layer_list_snackbar_light)
        ),
        DARK(
            FTLLinearLayoutTheme(R.color.BackgroundDefaultDark),
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
                R.color.TextOnColorPrimaryOpacity60Dark,
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
            FTLProgressDialogTheme(
                R.color.TextOnColorAdditionalDark,
                R.color.SurfaceFourthDark,
                R.color.ProgressPrimaryDark
            ),
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
            FTLRouteTextViewTheme(
                R.color.IconBackgroundBlueDark,
                R.color.IconPrimaryDark,
                R.color.TextPrimaryDark,
                R.color.TextPrimaryDark
            ),
            FTLTableHeaderTheme(
                R.color.DividerPrimaryDark,
                R.color.IconSecondaryDark,
                R.color.TextPrimaryDark,
                R.color.TextOnColorAdditionalDark
            ),
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
                R.color.TextOnColorPrimaryOpacity60Dark,
                R.color.TextOnColorPrimaryDark,
                R.drawable.selector_ftl_button_primary_dark
            ),
            FTLButtonSecondaryTheme(
                R.color.selector_ftl_button_primary_or_secondary_text_dark,
                R.color.TextOnColorPrimaryOpacity60Dark,
                R.color.TextOnColorPrimaryDark,
                R.drawable.selector_ftl_button_secondary_dark
            ),
            FTLButtonCancelTheme(
                R.color.selector_ftl_button_cancel_text_dark,
                R.color.TextDangerEnabledLightOpacity60,
                R.color.TextDangerEnabledLight,
                R.drawable.selector_ftl_button_cancel_dark
            ),
            FTLMultipleTextViewTheme(R.color.TextPrimaryDark),
            FTLTimerButtonTheme(
                R.color.TextPlaceholderDark,
                R.color.ButtonSecondaryPressedDark,
                R.color.ButtonSecondaryEnableDark,
                R.color.TextPlaceholderOpacity60Dark,
                R.color.TextPlaceholderDark,
                R.color.TextOnColorPrimaryDark,
                R.color.ButtonDangerPressedDark,
                R.color.ButtonDangerEnableDark,
                R.color.TextOnColorPrimaryOpacity60Dark,
                R.color.TextOnColorPrimaryDark,
                R.color.TextOnColorPrimaryDark,
                R.color.ButtonSuccessPressedDark,
                R.color.ButtonSuccessEnableDark,
                R.color.TextOnColorPrimaryOpacity60Dark,
                R.color.TextOnColorPrimaryDark
            ),
            FTLSectionTextViewTheme(R.color.TextPrimaryDark, R.color.IconSecondaryDark),
            FTLDoubleTextViewTheme(R.color.TextPrimaryDark),
            FTLCoordinatorLayoutTheme(R.color.BackgroundSecondaryDark),
            FTLConstraintLayoutTheme(R.color.BackgroundDefaultDark),
            FTLTitleTheme(R.color.TextPrimaryDark, R.color.TextSuccessEnabledDark),
            FTLBottomNavigationViewTheme(
                R.color.SurfaceSecondDark,
                R.color.selector_ftl_bnv_item_text_color_dark,
                R.drawable.selector_ftl_bnv_orders_dark,
                R.drawable.selector_ftl_bnv_maps_dark,
                R.drawable.selector_ftl_bnv_history_dark,
                R.drawable.selector_ftl_bnv_more_dark
            ),
            FTLDividerTheme(R.color.DividerPrimaryDark),
            FTLDefaultTextViewTheme(R.color.TextPrimaryDark),
            FTLExtendableTextViewTheme(R.color.TextPrimaryDark, R.color.TextOnColorAdditionalLight),
            FTLTableRowTheme(
                R.color.TextPrimaryDark,
                R.color.TextOnColorAdditionalDark,
                R.color.TextPrimaryDark
            ),
            FTLRestaurantMarkerTheme(
                R.drawable.shape_ftl_restaurant_marker_dark,
                R.color.TextPrimaryDark
            ),
            FTLSnackbarTheme(R.drawable.layer_list_snackbar_dark)
        )
    }

    data class FTLToolbarTheme(
        @ColorRes var bgColor: Int,
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
        @ColorRes val bgColor: Int,
        @ColorRes val accentColor: Int
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

    data class FTLRouteTextViewTheme(
        @ColorRes var routeBackgroundColor: Int,
        @ColorRes var routItemsColor: Int,
        @ColorRes var addressToColor: Int,
        @ColorRes var addressFromColor: Int
    )

    data class FTLTableHeaderTheme(
        @ColorRes val dividerColor: Int,
        @ColorRes val switchIconColor: Int,
        @ColorRes val titleColor: Int,
        @ColorRes val subtitleColor: Int
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

    data class FTLMultipleTextViewTheme(
        @ColorRes val topSlotsColor: Int
    )

    data class FTLTimerButtonTheme(
        @ColorRes var textColorNew: Int,
        @ColorRes val progressColorNew: Int,
        @ColorRes val progressBgColorNew: Int,
        @ColorRes var dotColorNew: Int,
        @ColorRes var bounceDotColorNew: Int,

        @ColorRes var textColorReadyForDelivery: Int,
        @ColorRes val progressColorReadyForDelivery: Int,
        @ColorRes val progressBgColorReadyForDelivery: Int,
        @ColorRes var dotColorReadyForDelivery: Int,
        @ColorRes var bounceDotColorReadyForDelivery: Int,

        @ColorRes var textColorInDelivery: Int,
        @ColorRes val progressColorInDelivery: Int,
        @ColorRes val progressBgColorInDelivery: Int,
        @ColorRes var dotColorInDelivery: Int,
        @ColorRes var bounceDotColorInDelivery: Int
    )

    data class FTLSectionTextViewTheme(
        @ColorRes var textColor: Int,
        @ColorRes var arrowColor: Int
    )

    data class FTLDoubleTextViewTheme(
        @ColorRes var textColor: Int
    )

    data class FTLDividerTheme(
        @ColorRes var bgColor: Int
    )

    data class FTLCoordinatorLayoutTheme(
        @ColorRes var bgColor: Int
    )

    data class FTLConstraintLayoutTheme(
        @ColorRes var bgColor: Int
    )

    data class FTLTitleTheme(
        @ColorRes var titleColor: Int,
        @ColorRes var subtitleColor: Int
    )

    data class FTLBottomNavigationViewTheme(
        @ColorRes val bgColor: Int,
        @ColorRes val itemTextColor: Int,
        @DrawableRes val itemOrdersIcon: Int,
        @DrawableRes val itemMapsIcon: Int,
        @DrawableRes val itemHistoryIcon: Int,
        @DrawableRes val itemMoreIcon: Int
    )

    data class FTLDefaultTextViewTheme(
        @ColorRes val textColor: Int
    )

    data class FTLExtendableTextViewTheme(
        @ColorRes val fullTextColor: Int,
        @ColorRes val ellipsizedTextColor: Int
    )

    data class FTLTableRowTheme(
        @ColorRes val startTextColor: Int,
        @ColorRes val centerTextColor: Int,
        @ColorRes val endTextColor: Int
    )

    data class FTLRestaurantMarkerTheme(
        @DrawableRes val background: Int,
        @ColorRes val textColor: Int
    )

    data class FTLSnackbarTheme(
        @DrawableRes val background: Int
    )

    interface ThemeChangedListener {
        fun onThemeChanged(theme: Theme)
    }
}