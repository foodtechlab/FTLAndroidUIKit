package com.foodtechlab.ftlandroiduikitsample

import android.app.Application
import com.foodtechlab.ftlandroiduikitsample.utils.Utils

@Suppress("unused")
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}