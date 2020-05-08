package com.foodtechlab.ftlandroiduikit.snackbar.top

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.foodtechlab.ftlandroiduikit.R
import com.google.android.material.snackbar.ContentViewCallback
import kotlinx.android.synthetic.main.view_snackbar.view.*

/**
 * Created by Umalt on 08.05.2020
 */
class SnackbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), ContentViewCallback {

    init {
        View.inflate(context, R.layout.view_snackbar, this)
    }

    override fun animateContentIn(delay: Int, duration: Int) {}

    override fun animateContentOut(delay: Int, duration: Int) {}

    fun setMessage(message: String) {
        tv_snackbar_message.text = message
    }
}