package com.foodtechlab.ftlandroiduikit.bar.toolbar

import DeliveryStatus
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.NetworkConnectivityState
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.toolbar.FTLToolbarDotsProgress
import com.foodtechlab.ftlandroiduikit.progress.circle.scale.FTLCircleScaleView
import com.foodtechlab.ftlandroiduikit.textfield.title.FTLTitle
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.textfield.time.FTLDeliveryTimeView
import com.foodtechlab.ftlandroiduikit.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 28.05.2020
 */
class FTLToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CoroutineScope {
    var isShadowVisible: Boolean
        get() = vShadow.isVisible
        set(value) {
            vShadow.isVisible = value
        }

    var titleColor: Int
        get() = ftlTitle.titleColor
        set(value) {
            ftlTitle.titleColor = value
        }

    var actionTextColor: Int
        get() = tvAction.currentTextColor
        set(value) {
            tvAction.setTextColor(value)
            actionDrawable?.mutate()?.changeColor(value)
        }

    var subtitleColor: Int
        get() = ftlTitle.subtitleColor
        set(value) {
            ftlTitle.subtitleColor = value
        }

    var currentProgress: Int = 0
        set(value) {
            field = value
            cpiProgress.currentProgress = field
        }

    var maxProgress: Int = 100
        set(value) {
            field = value
            cpiProgress.maxProgress = field
        }

    var title: String?
        get() = ftlTitle.title
        set(value) {
            ftlTitle.isTitleVisible = !value.isNullOrEmpty()
            ftlTitle.title = value
        }

    var subTitle: String?
        get() = ftlTitle.subTitle.toString()
        set(value) {
            ftlTitle.isSubtitleVisible = !value.isNullOrEmpty()
            ftlTitle.subTitle = value
        }

    var actionText: CharSequence?
        get() = tvAction.text
        set(value) {
            tvAction.text = value
        }

    var startDrawable: Drawable?
        get() = ibStart.drawable
        set(value) {
            launch {
                viewThemeManager.mapToViewData().collect { theme ->
                    value?.mutate()?.changeColor(
                        ContextCompat.getColor(
                            context,
                            theme.startIconColor
                        )
                    )
                }
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
            ibStart.setImageDrawable(value)
            ibStart.isVisible = value != null
        }

    var endDrawable: Drawable?
        get() = ibEnd.drawable
        set(value) {
            launch {
                viewThemeManager.mapToViewData().collect { theme ->
                    value?.mutate()?.changeColor(
                        ContextCompat.getColor(context, theme.endIconColor)
                    )
                    try {
                        this.cancel()
                    } catch (e: Exception) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }
            ibEnd.setImageDrawable(value)
        }

    var actionDrawable: Drawable?
        get() = tvAction.compoundDrawablesRelative.filterNotNull().firstOrNull()
        private set(value) {
            tvAction.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, value, null)
        }

    var logoIcon: Drawable?
        get() = ivLogo.drawable
        set(value) {
            ivLogo.setImageDrawable(value)
        }

    var socketConnectivityState = SocketConnectivityState.CONNECTED
        set(value) {
            field = value
            launch {
                viewThemeManager.mapToViewData().collect { theme ->
                    if (value == SocketConnectivityState.CONNECTED) {
                        vIndicator.background.changeColor(
                            ContextCompat.getColor(
                                context,
                                theme.socketConnected
                            )
                        )
                    } else {
                        vIndicator.background.changeColor(
                            ContextCompat.getColor(
                                context,
                                theme.socketDisconnected
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
        }

    var networkState = NetworkConnectivityState.CONNECTED
        set(value) {
            field = value
            tvConnectivity.apply {
                launch {
                    viewThemeManager.mapToViewData().collect { theme ->
                        setBackgroundColor(ContextCompat.getColor(context, theme.networkConnected))
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

    private val hideNetworkConnectivityBar = Runnable { tvConnectivity.isGone = true }

    var onUpdateEndContent: OnUpdateEndContent? = null

    var onToolbarClickListener: OnToolbarClickListener? = null

    var onIndicatorClickListener: OnClickListener? = null

    var onActionClickListener: OnClickListener? = null

    private val viewThemeManager: ViewThemeManager<FTLToolbarTheme> = FTLToolbarThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val progress: FTLToolbarDotsProgress
    private val ibStart: ImageButton
    private val ibEnd: ImageButton
    private val ivLogo: ImageView
    private val vIndicator: View
    private val flIndicator: View
    private val cpiProgress: FTLCircleScaleView
    private val ftlTitle: FTLTitle
    private val tvConnectivity: TextView
    private val tvTime: FTLDeliveryTimeView
    private val tvAction: TextView

    private val vShadow: View
    private val flEndContainer: FrameLayout

    private val rlContainer: RelativeLayout

    init {
        View.inflate(context, R.layout.layout_ftl_toolbar, this)

        orientation = VERTICAL

        tvAction = findViewById(R.id.tv_ftl_toolbar_action)
        progress = findViewById(R.id.dot_progress)
        ftlTitle = findViewById(R.id.ftl_title)
        ibStart = findViewById(R.id.ib_ftl_toolbar_start)
        ibEnd = findViewById(R.id.ib_ftl_toolbar_end)
        ivLogo = findViewById(R.id.iv_ftl_toolbar_logo)
        vIndicator = findViewById(R.id.v_ftl_toolbar_indicator)
        flIndicator = findViewById(R.id.fl_ftl_toolbar_indicator)
        cpiProgress = findViewById(R.id.cpi_ftl_toolbar_progress)
        flEndContainer = findViewById(R.id.fl_ftl_toolbar_end_container)
        vShadow = findViewById(R.id.v_ftl_toolbar_shadow)
        rlContainer = findViewById(R.id.rl_ftl_toolbar_container)
        tvConnectivity = findViewById(R.id.tv_ftl_toolbar_connectivity)
        tvTime = findViewById(R.id.tv_ftl_toolbar_time)

        ftlTitle.autoHandleColors = false

        ibStart.setOnClickListener { onToolbarClickListener?.onToolbarClick(it) }

        ibEnd.setOnClickListener { onToolbarClickListener?.onToolbarClick(it) }

        flIndicator.setOnClickListener { onIndicatorClickListener?.onClick(it) }

        tvAction.setOnClickListener { onActionClickListener?.onClick(it) }

        if (background == null) rlContainer.setBackgroundColor(Color.WHITE)

        context.withStyledAttributes(attrs, R.styleable.FTLToolbar) {
            if (hasValue(R.styleable.FTLToolbar_ftlToolbar_start_icon)) {
                startDrawable = getDrawable(R.styleable.FTLToolbar_ftlToolbar_start_icon)
            }
            if (hasValue(R.styleable.FTLToolbar_ftlToolbar_end_icon)) {
                endDrawable = getDrawable(R.styleable.FTLToolbar_ftlToolbar_end_icon)
            }
            actionDrawable = when {
                hasValue(R.styleable.FTLToolbar_ftlToolbar_action_drawable) ->
                    getDrawable(R.styleable.FTLToolbar_ftlToolbar_action_drawable)
                else -> null
            }
            isShadowVisible = getBoolean(R.styleable.FTLToolbar_ftlToolbar_shadow_visible, false)

            setupLogoIcon()

            setupTitleColor()

            setupSubtitleColor()

            setupActionColor()

            title = getString(R.styleable.FTLToolbar_ftlToolbar_title)
            actionText = getString(R.styleable.FTLToolbar_ftlToolbar_action_text)
            subTitle = getString(R.styleable.FTLToolbar_ftlToolbar_subtitle)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun onThemeChanged(theme: FTLToolbarTheme) {
        rlContainer.setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.bgColor
            )
        )
        endDrawable?.mutate()?.changeColor(
            ContextCompat.getColor(
                context,
                theme.endIconColor
            )
        )
        startDrawable?.mutate()?.changeColor(
            ContextCompat.getColor(
                context,
                theme.startIconColor
            )
        )
        if (theme.logoIcon != -1) {
            logoIcon = ContextCompat.getDrawable(context, theme.logoIcon)
        }
        titleColor = ContextCompat.getColor(context, theme.titleColor)
        subtitleColor = ContextCompat.getColor(context, theme.subtitleColor)
        actionTextColor = ContextCompat.getColor(context, theme.actionColor)

        socketConnectivityState = socketConnectivityState

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

    fun showProgress() {
        TransitionManager.beginDelayedTransition(rlContainer, Fade().apply { duration = 100 })
        ftlTitle.isGone = true
        progress.startAnimation()
        progress.isVisible = true
    }

    fun hideProgress() {
        TransitionManager.beginDelayedTransition(rlContainer, Fade().apply { duration = 100 })
        progress.stopAnimation()
        progress.isGone = true
        ftlTitle.isVisible = true
    }

    fun showConnectionIndicator() {
        showOnlyOneChildInEndContainer(flIndicator.id)
    }

    fun showCircleProgressIndicator(
        imageType: ImageType = ImageType.CHECKLIST,
        @ColorRes trackColorRes: Int = -1,
        @ColorRes backgroundTrackColorRes: Int = -1,
        @ColorRes imageColorRes: Int = -1
    ) {
        with(cpiProgress) {
            this.imageType = imageType
            updateTrackColorTheme(trackColorRes)
            updateBackgroundTrackColorTheme(backgroundTrackColorRes)
            updateImageColorTheme(imageColorRes)
        }
        showOnlyOneChildInEndContainer(cpiProgress.id)
    }

    fun showTime(timeZone: String?, deliveryTime: Long, status: DeliveryStatus) {
        with(tvTime) {
            visibility = View.VISIBLE
            timeZoneId = timeZone
            deliveryTimeMillis = deliveryTime
            deliveryStatus = status
        }
    }

    fun showEndButton() {
        showOnlyOneChildInEndContainer(ibEnd.id)
    }

    fun showLogo() {
        showOnlyOneChildInEndContainer(ivLogo.id)
    }

    fun showActionText() {
        showOnlyOneChildInEndContainer(tvAction.id)
    }

    private fun showOnlyOneChildInEndContainer(id: Int) {
        TransitionManager.beginDelayedTransition(rlContainer, Fade().apply { duration = 100 })

        val endContainerSize = if (id == tvAction.id) {
            RelativeLayout.LayoutParams.WRAP_CONTENT
        } else {
            context.dpToPx(32f).toInt()
        }

        val lParams = flEndContainer.layoutParams as RelativeLayout.LayoutParams
        lParams.width = endContainerSize
        lParams.height = endContainerSize
        flEndContainer.layoutParams = lParams

        for (i in 0 until flEndContainer.childCount) {
            val child = flEndContainer.getChildAt(i)
            child.isVisible = child.id == id
        }
        onUpdateEndContent?.onUpdate(false)
    }


    fun hideAllChildInEndContainer() {
        TransitionManager.beginDelayedTransition(rlContainer, Fade().apply { duration = 100 })
        for (i in 0 until flEndContainer.childCount) {
            flEndContainer.getChildAt(i).isGone = true
        }
        onUpdateEndContent?.onUpdate(true)
    }

    fun updateBackgroundColor(@ColorRes lightColor: Int, @ColorRes darkColor: Int) {
        viewThemeManager.lightTheme.bgColor = lightColor
        viewThemeManager.darkTheme?.bgColor = darkColor
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                rlContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        theme.bgColor
                    )
                )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    private fun TypedArray.setupLogoIcon() {
        viewThemeManager.lightTheme.logoIcon = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_logo_icon_light,
            viewThemeManager.lightTheme.logoIcon
        )
        viewThemeManager.darkTheme?.let {
            it.logoIcon = getResourceId(
                R.styleable.FTLToolbar_ftlToolbar_logo_icon_dark,
                it.logoIcon
            )
        }
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                if (theme.logoIcon != -1) {
                    logoIcon = ContextCompat.getDrawable(
                        context,
                        theme.logoIcon
                    )
                }
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    private fun TypedArray.setupTitleColor() {
        viewThemeManager.lightTheme.titleColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_title_color_light,
            viewThemeManager.lightTheme.titleColor
        )
        viewThemeManager.darkTheme?.let {
            it.titleColor = getResourceId(
                R.styleable.FTLToolbar_ftlToolbar_title_color_dark,
                it.titleColor
            )
        }
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                titleColor = ContextCompat.getColor(
                    context,
                    theme.titleColor
                )
                try {
                    this.cancel()
                } catch (e: Exception){
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    private fun TypedArray.setupSubtitleColor() {
        viewThemeManager.lightTheme.subtitleColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_subtitle_color_light,
            viewThemeManager.lightTheme.subtitleColor
        )

        viewThemeManager.darkTheme?.let {
            it.subtitleColor = getResourceId(
                R.styleable.FTLToolbar_ftlToolbar_subtitle_color_dark,
                it.subtitleColor
            )
        }
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                ContextCompat.getColor(context, theme.subtitleColor)
                try {
                    this.cancel()
                } catch (e: Exception){
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    private fun TypedArray.setupActionColor() {
        viewThemeManager.lightTheme.actionColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_action_color_light,
            viewThemeManager.lightTheme.actionColor
        )
        viewThemeManager.darkTheme?.let {
            it.actionColor = getResourceId(
                R.styleable.FTLToolbar_ftlToolbar_action_color_dark,
                it.actionColor
            )
        }
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                actionTextColor =
                    ContextCompat.getColor(
                        context,
                        theme.actionColor
                    )
                try {
                    this.cancel()
                } catch (e : Exception){
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    companion object {
        private const val TAG = "FTLToolbar"
    }
}

data class FTLToolbarTheme(
    @ColorRes var bgColor: Int,
    @ColorRes var titleColor: Int,
    @ColorRes var subtitleColor: Int,
    @ColorRes var actionColor: Int,
    @ColorRes var startIconColor: Int,
    @ColorRes var endIconColor: Int,
    @ColorRes var socketConnected: Int,
    @ColorRes var socketDisconnected: Int,
    @ColorRes var networkConnected: Int,
    @ColorRes var networkDisconnected: Int,
    @DrawableRes var logoIcon: Int = -1
) : ViewTheme()
