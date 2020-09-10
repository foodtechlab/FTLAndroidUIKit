package com.foodtechlab.ftlandroiduikitsample.utils

import android.app.Application
import androidx.annotation.StringRes

object Utils {
    private var application: Application? = null

    fun init(application: Application) {
        this.application = application
    }

    @JvmStatic
    fun getString(@StringRes id: Int, vararg parameters: Any): String {
        return application?.getString(id, *parameters)
            ?: throw IllegalStateException("Application context in Utils not initialized. Please call method init in your Application instance")
    }
}