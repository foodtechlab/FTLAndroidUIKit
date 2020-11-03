package com.foodtechlab.ftlandroiduikit.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 07.10.2020
 */
abstract class FTLView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }
}