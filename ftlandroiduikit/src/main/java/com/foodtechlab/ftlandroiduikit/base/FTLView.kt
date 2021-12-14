package com.foodtechlab.ftlandroiduikit.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 07.10.2020
 */
abstract class FTLView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }
}
