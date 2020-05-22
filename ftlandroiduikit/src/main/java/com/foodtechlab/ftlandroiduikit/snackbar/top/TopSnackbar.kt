package com.foodtechlab.ftlandroiduikit.snackbar.top

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.dpToPx
import com.foodtechlab.ftlandroiduikit.common.findSuitableParent
import com.google.android.material.snackbar.BaseTransientBottomBar

/**
 * Created by Umalt on 08.05.2020
 */
class TopSnackbar(
    @NonNull parent: ViewGroup,
    @NonNull customView: SnackbarView
) : BaseTransientBottomBar<TopSnackbar>(parent, customView, customView) {

    init {
        animationMode = ANIMATION_MODE_FADE

        when (val lParams = view.layoutParams) {
            is FrameLayout.LayoutParams -> lParams.gravity = Gravity.TOP
            is CoordinatorLayout.LayoutParams -> lParams.gravity = Gravity.TOP
        }

        view.background = ContextCompat.getDrawable(context, R.drawable.layer_list_snackbar)

        ViewCompat.setElevation(view, context.dpToPx(ELEVATION))
    }

    companion object {
        private const val ELEVATION = 6f

        fun make(
            @NonNull view: View,
            @NonNull text: String,
            @Duration duration: Int
        ): TopSnackbar? {
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            return try {
                val customView = LayoutInflater.from(view.context).inflate(
                    R.layout.layout_snackbar,
                    parent,
                    false
                ) as SnackbarView

                customView.setMessage(text)

                TopSnackbar(parent, customView).setDuration(duration)
            } catch (e: Exception) {
                Log.v("exception ", e.message ?: "")
                null
            }
        }
    }
}