package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.sheet.DialogState
import com.foodtechlab.ftlandroiduikit.sheet.FTLBottomSheet
import com.foodtechlab.ftlandroiduikit.sheet.Type
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikitsample.R
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
        rg.check(
            when (ThemeManager.theme) {
                ThemeManager.Theme.DARK -> R.id.rbDarkTheme
                else -> R.id.rbLightTheme
            }
        )

        rg.setOnCheckedChangeListener { _, i ->
            ThemeManager.setTheme(
                when (i) {
                    R.id.rbDarkTheme -> ThemeManager.Theme.DARK
                    else -> ThemeManager.Theme.LIGHT
                },
                screenImageView, llContainer, centerX = 100, centerY = 100, animate = true
            )
            prefs.edit()
                .putInt("key_theme", ThemeManager.theme.ordinal)
                .apply()
        }

        FTLBottomSheet.newInstance(DialogState("title", "message", Type.SAD, listOf()))
            .show(supportFragmentManager, FTLBottomSheet.TAG)
    }
}
