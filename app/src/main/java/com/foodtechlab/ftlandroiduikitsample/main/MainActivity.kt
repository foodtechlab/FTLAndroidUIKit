package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.dialog.FTLProgressDialog
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryStatus
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    private val parentJob = Job()

    private var progressDialog: FTLProgressDialog? = null

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_settings", MODE_PRIVATE)

        ThemeManager.theme =
            ThemeManager.Theme.values()[prefs.getInt("key_theme", ThemeManager.Theme.LIGHT.ordinal)]

        setContentView(R.layout.activity_main)

//        toolbar.showTime(
//            "Europe/Samara",
//            60 * 60 * 1000,
//            DeliveryStatus.URGENT
//        )
        toolbar.showLogo()

        btnSwitchTheme.setOnClickListener {
            ThemeManager.setTheme(
                when (ThemeManager.theme) {
                    ThemeManager.Theme.LIGHT -> ThemeManager.Theme.DARK
                    ThemeManager.Theme.DARK -> ThemeManager.Theme.LIGHT
                },
                screenImageView, llContainer, centerX = 100, centerY = 100, animate = true
            )
            prefs.edit()
                .putInt("key_theme", ThemeManager.theme.ordinal)
                .apply()
        }

        cpbIndicator.setOnClickListener {
            showProgressDialog()
            launch {
                delay(5000)
                hideProgressDialog()
            }
        }
    }

    private fun showProgressDialog() {
        if (progressDialog?.isAdded != true) {
            progressDialog = FTLProgressDialog.newInstance("Пожалуйста, подождите...")
            progressDialog?.show(supportFragmentManager, FTLProgressDialog.TAG)
        }
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        ThemeManager.removeListeners()
//    }
}
