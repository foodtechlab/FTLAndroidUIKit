package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.snackbar.top.FTLSnackbar
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikitsample.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ObsoleteCoroutinesApi

class MainActivity : AppCompatActivity() {

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_settings", MODE_PRIVATE)

        ThemeManager.theme =
            ThemeManager.Theme.values()[prefs.getInt("key_theme", ThemeManager.Theme.LIGHT.ordinal)]

        setContentView(R.layout.activity_main)

        toolbar.startDrawable = ContextCompat.getDrawable(this, R.drawable.ic_close_24)
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
        with(btnAction) {
            updateBackgroundDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.selector_ftl_button_green_light,
                ),
                ContextCompat.getDrawable(
                    context,
                    R.drawable.selector_ftl_button_green_dark
                )
            )
            setOnClickListener {
                FTLSnackbar.make(
                    root,
                    "Пожалуйста, измените номер телефона и повторите попытку",
                    Snackbar.LENGTH_LONG
                )?.show()
            }
        }
    }
}
