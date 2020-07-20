package com.foodtechlab.ftlandroiduikit.bar.toolbar

import android.content.Context
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
import com.foodtechlab.ftlandroiduikit.common.DotsProgress
import com.foodtechlab.ftlandroiduikit.textfield.FTLTitle
import com.foodtechlab.ftlandroiduikit.util.changeColor

/**
 * Created by Umalt on 28.05.2020
 */
class FTLToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

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

    var logoIcon: Drawable?
        get() = ivLogo.drawable
        set(value) {
            ivLogo.setImageDrawable(value)
        }

    private val hideNetworkConnectivityBar = Runnable {
        tvConnectivity.isGone = true
    }

    var logoPlaceholder: Drawable? = null

    var onToolbarClickListener: OnToolbarClickListener? = null

    private val progress: DotsProgress
    private val ibStart: ImageButton
    private val ibEnd: ImageButton
    private val ivLogo: ImageView
    private val vIndicator: View
    private val ftlTitle: FTLTitle
    private val tvConnectivity: TextView

    private val vShadow: View
    private val flEndContainer: FrameLayout

    private val rlContainer: RelativeLayout

    init {
        View.inflate(context, R.layout.layout_ftl_toolbar, this)

        orientation = VERTICAL

        progress = findViewById(R.id.dot_progress)
        ftlTitle = findViewById(R.id.ftl_title)
        ibStart = findViewById(R.id.ib_ftl_toolbar_start)
        ibEnd = findViewById(R.id.ib_ftl_toolbar_end)
        ivLogo = findViewById(R.id.iv_ftl_toolbar_logo)
        vIndicator = findViewById(R.id.v_ftl_toolbar_indicator)
        flEndContainer = findViewById(R.id.fl_ftl_toolbar_end_container)
        vShadow = findViewById(R.id.v_ftl_toolbar_shadow)
        rlContainer = findViewById(R.id.rl_ftl_toolbar_container)
        tvConnectivity = findViewById(R.id.tv_ftl_toolbar_connectivity)

        ibStart.setOnClickListener {
            onToolbarClickListener?.onToolbarClick(it)
        }

        ibEnd.setOnClickListener {
            onToolbarClickListener?.onToolbarClick(it)
        }

        if (background == null) {
            rlContainer.setBackgroundColor(Color.WHITE)
        }

        context.withStyledAttributes(attrs, R.styleable.FTLToolbar) {
            if (hasValue(R.styleable.FTLToolbar_ftlToolbar_start_icon)) {
                startDrawable = getDrawable(R.styleable.FTLToolbar_ftlToolbar_start_icon)
            }
            if (hasValue(R.styleable.FTLToolbar_ftlToolbar_end_icon)) {
                endDrawable = getDrawable(R.styleable.FTLToolbar_ftlToolbar_end_icon)
            }
            logoPlaceholder = when {
                hasValue(R.styleable.FTLToolbar_ftlToolbar_logo_placeholder) ->
                    getDrawable(R.styleable.FTLToolbar_ftlToolbar_logo_placeholder)
                        ?: ContextCompat.getDrawable(context, R.drawable.ic_restaurant_placeholder)
                else -> ContextCompat.getDrawable(context, R.drawable.ic_restaurant_placeholder)
            }
            logoIcon = when {
                hasValue(R.styleable.FTLToolbar_ftlToolbar_logo_icon) ->
                    getDrawable(R.styleable.FTLToolbar_ftlToolbar_logo_icon) ?: logoPlaceholder
                else -> logoPlaceholder
            }
            isShadowVisible = getBoolean(R.styleable.FTLToolbar_ftlToolbar_shadow_visible, false)
            titleColor = getColor(
                R.styleable.FTLToolbar_ftlToolbar_title_color,
                ContextCompat.getColor(context, R.color.OnBackgroundPrimary)
            )
            subtitleColor = getColor(
                R.styleable.FTLToolbar_ftlToolbar_subtitle_color,
                ContextCompat.getColor(context, R.color.AdditionalGreen)
            )
            title = getString(R.styleable.FTLToolbar_ftlToolbar_title)
            subTitle = getString(R.styleable.FTLToolbar_ftlToolbar_subtitle)
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
        showOnlyOneChildInEndContainer(vIndicator.id)
    }

    fun showEndButton() {
        showOnlyOneChildInEndContainer(ibEnd.id)
    }

    fun showLogo() {
        showOnlyOneChildInEndContainer(ivLogo.id)
    }

    fun setNetworkConnectivityState(state: NetworkConnectivityState) {
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

    fun setSocketConnectivityState(state: SocketConnectivityState) {
        vIndicator.background.changeColor(ContextCompat.getColor(context, state.color))
    }

    private fun showOnlyOneChildInEndContainer(id: Int) {
        TransitionManager.beginDelayedTransition(rlContainer, Fade().apply { duration = 100 })
        for (i in 0 until flEndContainer.childCount) {
            val child = flEndContainer.getChildAt(i)
            child.isVisible = child.id == id
        }
    }

    private fun hideAllChildInEndContainer() {
        TransitionManager.beginDelayedTransition(rlContainer, Fade().apply { duration = 100 })
        for (i in 0 until flEndContainer.childCount) {
            flEndContainer.getChildAt(i).isGone = true
        }
    }
}