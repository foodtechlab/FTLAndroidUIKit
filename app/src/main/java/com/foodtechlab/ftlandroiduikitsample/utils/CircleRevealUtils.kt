package com.foodtechlab.ftlandroiduikitsample.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.ViewAnimationUtils
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.parcel.Parcelize

fun View.registerCircularRevealAnimation(
    revealSettings: RevealAnimationSetting,
    startColor: Int,
    endColor: Int
) {
    this.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            v.removeOnLayoutChangeListener(this)
            val cx = revealSettings.centerX
            val cy = revealSettings.centerY
            val width = revealSettings.width
            val height = revealSettings.height
            val duration = context.resources.getInteger(android.R.integer.config_mediumAnimTime)

            //Simply use the diagonal of the view
            val finalRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat()
            val anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, finalRadius)
                .setDuration(duration.toLong())
            anim.interpolator = FastOutSlowInInterpolator()
            anim.start()
            startColorAnimation(startColor, endColor, duration)
        }
    })
}

fun View.startCircularExitAnimation(
    revealSettings: RevealAnimationSetting,
    startColor: Int,
    endColor: Int,
    listener: Dismissible.OnDismissedListener
) {
    val cx = revealSettings.centerX
    val cy = revealSettings.centerY
    val width = revealSettings.width
    val height = revealSettings.height
    val duration = context.resources.getInteger(android.R.integer.config_mediumAnimTime)

    val initRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, initRadius, 0f)
    anim.duration = duration.toLong()
    anim.interpolator = FastOutSlowInInterpolator()
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            listener.onDismissed()
        }
    })
    anim.start()
    startColorAnimation(startColor, endColor, duration)
}

fun View.startColorAnimation(startColor: Int, endColor: Int, duration: Int) {
    val anim = ValueAnimator()
    anim.setIntValues(startColor, endColor)
    anim.setEvaluator(ArgbEvaluator())
    anim.addUpdateListener { valueAnimator -> this.setBackgroundColor(valueAnimator.animatedValue as Int) }
    anim.duration = duration.toLong()
    anim.start()
}

interface Dismissible {
    interface OnDismissedListener {
        fun onDismissed()
    }

    fun dismiss(listener: OnDismissedListener)
}

@Parcelize
data class RevealAnimationSetting(
    val centerX: Int,
    val centerY: Int,
    val width: Int,
    val height: Int
) :
    Parcelable


fun <T : Fragment> T.withArgs(fillArgs: (Bundle) -> Unit): T {
    return this.apply { arguments = Bundle().apply(fillArgs) }
}
