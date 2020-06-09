package com.foodtechlab.ftlandroiduikit.util

import android.animation.Animator

internal fun Animator.doStartAndFinish(
    start: () -> Unit,
    finish: () -> Unit
) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator?) = start()
        override fun onAnimationEnd(animator: Animator?) = finish()
        override fun onAnimationCancel(animator: Animator?) = Unit
        override fun onAnimationRepeat(animator: Animator?) = Unit
    })
}