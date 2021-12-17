package com.foodtechlab.ftlandroiduikit.tab

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.NetworkConnectivityState
import com.foodtechlab.ftlandroiduikit.switch.FTLSwitchTheme
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

class FTLTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLTabLayoutTheme> = FTLTabLayoutThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    var isShadowVisible: Boolean
        get() = vShadow.isVisible
        set(value) {
            vShadow.isVisible = value
        }

    var shouldDrawIndicatorFullWidth = true
        set(value) {
            field = value
            tabs.isTabIndicatorFullWidth = field
        }

    var networkState = NetworkConnectivityState.CONNECTED
        set(value) {
            field = value
            tvConnectivity.apply {
                launch {
                    viewThemeManager.mapToViewData().collect { theme ->
                        if (value == NetworkConnectivityState.CONNECTED) {
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    theme.networkConnected
                                )
                            )
                        } else {
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    theme.networkDisconnected
                                )
                            )
                        }
                        try {
                            this.cancel()
                        } catch (e: Exception) {
                            Log.e(TAG, e.message.toString())
                        }
                    }
                }
                setText(value.message)
                isVisible = true
            }
            if (value == NetworkConnectivityState.CONNECTED) {
                postDelayed(hideNetworkConnectivityBar, 2500L)
            } else {
                removeCallbacks(hideNetworkConnectivityBar)
            }
        }

    @TabLayout.Mode
    var displayMode = TabLayout.MODE_FIXED
        set(value) {
            field = value
            tabs.tabMode = field
        }

    @TabGravity
    var gravityMode = GRAVITY_FILL
        set(value) {
            field = value
            tabs.tabGravity = if (field == GRAVITY_END) {
                tabs.layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.END
                }
                GRAVITY_START
            } else {
                tabs.layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.NO_GRAVITY
                }
                field
            }
        }

    val tabs: TabLayout

    private val hideNetworkConnectivityBar = Runnable { tvConnectivity.isGone = true }
    private val vShadow: View
    private val tvConnectivity: TextView

    init {
        View.inflate(context, R.layout.layout_ftl_tab_layout, this)

        orientation = VERTICAL

        vShadow = findViewById(R.id.v_ftl_tab_layout_shadow)
        tabs = findViewById(R.id.tbl_ftl_tab_layout_container)
        tvConnectivity = findViewById(R.id.tv_ftl_tab_layout_connectivity)

        context.withStyledAttributes(attrs, R.styleable.FTLTabLayout) {
            isShadowVisible = getBoolean(R.styleable.FTLTabLayout_shouldShadowVisible, false)
            shouldDrawIndicatorFullWidth = getBoolean(
                R.styleable.FTLTabLayout_shouldDrawIndicatorFullWidth,
                true
            )
            displayMode = getInt(R.styleable.FTLTabLayout_displayMode, TabLayout.MODE_FIXED)
            gravityMode = getInt(R.styleable.FTLTabLayout_gravityMode, TabLayout.GRAVITY_FILL)
        }

        onThemeChanged()
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun changeCaseForItems(isAllCaps: Boolean) {
        for (i in 0 until tabs.tabCount) {
            tabs.getTabAt(i)?.let { tab ->
                tab.view.children.forEach {
                    if (it is AppCompatTextView) {
                        it.isAllCaps = isAllCaps
                    }
                }
            }
        }
    }

    fun onThemeChanged() {
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                with(tabs) {
                    setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            context,
                            theme.selectedTabIndicatorColor
                        )
                    )
                    setTabTextColors(
                        ContextCompat.getColor(context, theme.tabTextNormalColor),
                        ContextCompat.getColor(context, theme.tabTextSelectedColor)
                    )
                    setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            theme.backgroundColor
                        )
                    )
                }
                if (networkState == NetworkConnectivityState.CONNECTED) {
                    tvConnectivity.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            theme.networkConnected
                        )
                    )
                } else {
                    tvConnectivity.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            theme.networkDisconnected
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "FTLTabLayout"
        const val GRAVITY_FILL = 0
        const val GRAVITY_CENTER = 1
        const val GRAVITY_START = 2
        const val GRAVITY_END = 3

        @IntDef(
            flag = true,
            value = [GRAVITY_FILL, GRAVITY_CENTER, GRAVITY_START, GRAVITY_END]
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class TabGravity
    }
}

data class FTLTabLayoutTheme(
    @ColorRes val backgroundColor: Int,
    @ColorRes val selectedTabIndicatorColor: Int,
    @ColorRes val tabTextNormalColor: Int,
    @ColorRes val tabTextSelectedColor: Int,
    @ColorRes var networkConnected: Int,
    @ColorRes var networkDisconnected: Int
) : ViewTheme()
