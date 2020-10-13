package com.foodtechlab.ftlandroiduikit.bar.toolbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.FTLToolbarDotsProgress
import com.foodtechlab.ftlandroiduikit.textfield.FTLTitle
import com.foodtechlab.ftlandroiduikit.textfield.time.FTLDeliveryTimeView
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryStatus
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.foodtechlab.ftlandroiduikit.util.dpToPx

/**
 * Created by Umalt on 28.05.2020
 */
class FTLToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

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
            actionDrawable?.changeColor(value)
        }

    var subtitleColor: Int
        get() = ftlTitle.subtitleColor
        set(value) {
            ftlTitle.subtitleColor = value
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
            ibStart.setImageDrawable(value)
            ibStart.isVisible = value != null
        }

    var endDrawable: Drawable?
        get() = ibEnd.drawable
        set(value) {
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
            vIndicator.background.changeColor(ContextCompat.getColor(context, value.color))
        }

    var networkState = NetworkConnectivityState.CONNECTED

    private val hideNetworkConnectivityBar = Runnable { tvConnectivity.isGone = true }

    var onUpdateEndContent: OnUpdateEndContent? = null

    var onToolbarClickListener: OnToolbarClickListener? = null

    var onIndicatorClickListener: OnClickListener? = null

    var onActionClickListener: OnClickListener? = null

    private val progress: FTLToolbarDotsProgress
    private val ibStart: ImageButton
    private val ibEnd: ImageButton
    private val ivLogo: ImageView
    private val vIndicator: View
    private val flIndicator: View
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

        onThemeChanged(ThemeManager.theme)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        rlContainer.setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.ftlToolbarTheme.bgColor
            )
        )
        endDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                theme.ftlToolbarTheme.endIconColor
            )
        )
        startDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                theme.ftlToolbarTheme.startIconColor
            )
        )
        if (theme.ftlToolbarTheme.logoIcon != -1) {
            logoIcon = ContextCompat.getDrawable(context, theme.ftlToolbarTheme.logoIcon)
        }
        titleColor = ContextCompat.getColor(context, theme.ftlToolbarTheme.titleColor)
        subtitleColor = ContextCompat.getColor(context, theme.ftlToolbarTheme.subtitleColor)
        actionTextColor = ContextCompat.getColor(context, theme.ftlToolbarTheme.actionColor)

        SocketConnectivityState.CONNECTED.color = theme.ftlToolbarTheme.socketConnected
        SocketConnectivityState.DISCONNECTED.color = theme.ftlToolbarTheme.socketDisconnected
        socketConnectivityState = socketConnectivityState

        NetworkConnectivityState.CONNECTED.color = theme.ftlToolbarTheme.networkConnected
        NetworkConnectivityState.DISCONNECTED.color = theme.ftlToolbarTheme.networkDisconnected
        tvConnectivity.setBackgroundColor(ContextCompat.getColor(context, networkState.color))
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

    fun setNetworkConnectivityState(state: NetworkConnectivityState) {
        networkState = state

        tvConnectivity.apply {
            setBackgroundColor(ContextCompat.getColor(context, state.color))
            setText(state.message)
            isVisible = true
        }

        if (state == NetworkConnectivityState.CONNECTED) {
            postDelayed(hideNetworkConnectivityBar, 2500L)
        } else {
            removeCallbacks(hideNetworkConnectivityBar)
        }
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

    private fun TypedArray.setupLogoIcon() {
        ThemeManager.Theme.LIGHT.ftlToolbarTheme.logoIcon = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_logo_icon_light,
            ThemeManager.Theme.LIGHT.ftlToolbarTheme.logoIcon
        )
        ThemeManager.Theme.DARK.ftlToolbarTheme.logoIcon = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_logo_icon_dark,
            ThemeManager.Theme.DARK.ftlToolbarTheme.logoIcon
        )
        if (ThemeManager.theme.ftlToolbarTheme.logoIcon != -1) {
            logoIcon = ContextCompat.getDrawable(
                context,
                ThemeManager.theme.ftlToolbarTheme.logoIcon
            )
        }
    }

    private fun TypedArray.setupTitleColor() {
        ThemeManager.Theme.LIGHT.ftlToolbarTheme.titleColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_title_color_light,
            ThemeManager.Theme.LIGHT.ftlToolbarTheme.titleColor
        )
        ThemeManager.Theme.DARK.ftlToolbarTheme.titleColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_title_color_dark,
            ThemeManager.Theme.DARK.ftlToolbarTheme.titleColor
        )
        titleColor = ContextCompat.getColor(context, ThemeManager.theme.ftlToolbarTheme.titleColor)
    }

    private fun TypedArray.setupSubtitleColor() {
        ThemeManager.Theme.LIGHT.ftlToolbarTheme.subtitleColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_subtitle_color_light,
            ThemeManager.Theme.LIGHT.ftlToolbarTheme.subtitleColor
        )
        ThemeManager.Theme.DARK.ftlToolbarTheme.subtitleColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_subtitle_color_dark,
            ThemeManager.Theme.DARK.ftlToolbarTheme.subtitleColor
        )
        subtitleColor =
            ContextCompat.getColor(context, ThemeManager.theme.ftlToolbarTheme.subtitleColor)
    }

    private fun TypedArray.setupActionColor() {
        ThemeManager.Theme.LIGHT.ftlToolbarTheme.actionColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_action_color_light,
            ThemeManager.Theme.LIGHT.ftlToolbarTheme.actionColor
        )
        ThemeManager.Theme.DARK.ftlToolbarTheme.actionColor = getResourceId(
            R.styleable.FTLToolbar_ftlToolbar_action_color_dark,
            ThemeManager.Theme.DARK.ftlToolbarTheme.actionColor
        )
        actionTextColor =
            ContextCompat.getColor(context, ThemeManager.theme.ftlToolbarTheme.actionColor)
    }
}
