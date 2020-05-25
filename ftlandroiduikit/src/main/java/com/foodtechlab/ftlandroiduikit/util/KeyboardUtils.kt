package com.foodtechlab.ftlandroiduikit.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Umalt on 22.05.2020
 */

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(applicationWindowToken, 0)
}

fun View.openKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun addKeyboardVisibilityListener(
    rootLayout: View,
    onKeyboardVisibilityListener: OnKeyboardVisibilityListener
) {
    rootLayout.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        rootLayout.getWindowVisibleDisplayFrame(r)
        val screenHeight: Int = rootLayout.rootView.height

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight: Int = screenHeight - r.bottom
        val isVisible =
            keypadHeight > screenHeight * 0.15 // 0.15 ratio is perhaps enough to determine keypad height.
        onKeyboardVisibilityListener.onVisibilityChange(isVisible)
    }
}

interface OnKeyboardVisibilityListener {
    fun onVisibilityChange(isVisible: Boolean)
}