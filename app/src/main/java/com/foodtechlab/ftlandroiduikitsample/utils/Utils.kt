package com.foodtechlab.ftlandroiduikitsample.utils

import android.app.Application
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikitsample.R

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

    /**
     * Синтаксический сахар для уменьшения количества кода в основных классах
     * Метод возращает необходимый цвет согласно типу темы
     * @param darkThemeColor - цвет для темной темы
     * @param lightThemeColor - цвет для темной темы
     */
    fun getColorForTheme(@ColorRes darkThemeColor: Int, @ColorRes lightThemeColor: Int): Int {
        return application?.let {
            when (ThemeManager.theme) {
                ThemeManager.Theme.LIGHT -> ContextCompat.getColor(it, lightThemeColor)
                else -> ContextCompat.getColor(it, darkThemeColor)
            }
        }
            ?: throw IllegalStateException("Application context in Utils not initialized. Please call method init in your Application instance")
    }

    /**
     * Данный метод помогает красить навигационный бар в соответствии с цветом темы с
     * android 8 и выше, в противном случае бар будет черным
     */
    @Suppress("DEPRECATION")
    fun Window.updateColorNavigationBar() {
        apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insetsController?.setSystemBarsAppearance(
                    if (ThemeManager.theme == ThemeManager.Theme.LIGHT) {
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    } else {
                        0
                    },
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            } else {
                val flags = decorView.systemUiVisibility
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    decorView.systemUiVisibility =
                        if (ThemeManager.theme == ThemeManager.Theme.LIGHT) {
                            flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        } else {
                            flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
                        }
                }
            }
            navigationBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (ThemeManager.theme == ThemeManager.Theme.LIGHT) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        ContextCompat.getColor(context, R.color.BackgroundDefaultLight)
                    else
                        ContextCompat.getColor(context, R.color.BackgroundSecondaryLight)
                } else {
                    ContextCompat.getColor(context, R.color.BackgroundSecondaryDark)
                }
            } else {
                ContextCompat.getColor(context, R.color.BackgroundSecondaryDark)
            }
        }
    }

    /**
     * Данный метод помогает красить статус бар в соответствии с цветом темы (сероватый для
     * android 5.1 т.к. не возможно управлять цветом текста в статус баре)
     */
    @Suppress("DEPRECATION")
    fun Window.updateStatusBarColor() {
        apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setDecorFitsSystemWindows(true)
                insetsController?.setSystemBarsAppearance(
                    if (ThemeManager.theme == ThemeManager.Theme.LIGHT) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                decorView.systemUiVisibility = when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
                        View.SYSTEM_UI_FLAG_VISIBLE or if (ThemeManager.theme == ThemeManager.Theme.LIGHT)
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0

                    else -> View.SYSTEM_UI_FLAG_VISIBLE
                }
            }
            statusBarColor = when (ThemeManager.theme) {
                ThemeManager.Theme.LIGHT -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ContextCompat.getColor(context, R.color.SurfaceSecondLight)
                    } else {
                        ContextCompat.getColor(context, R.color.BackgroundStatusBar)
                    }
                }
                else -> ContextCompat.getColor(context, R.color.SurfaceSecondDark)
            }
        }
    }

    /**
     * Данный метод помогает красить статус бар и навигационный
     * бар в соответствии с цветом темы
     */
    fun Window.updateBarColors() {
        updateStatusBarColor()
        updateColorNavigationBar()
    }

}