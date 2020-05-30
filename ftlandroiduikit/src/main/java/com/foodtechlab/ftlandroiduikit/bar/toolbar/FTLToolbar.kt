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
import com.foodtechlab.ftlandroiduikit.R
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
        get() = tvTitle.currentTextColor
        set(value) {
            tvTitle.setTextColor(value)
        }

    var subtitleColor: Int
        get() = tvSubtitle.currentTextColor
        set(value) {
            tvSubtitle.setTextColor(value)
        }

    var title: String?
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.isVisible = !value.isNullOrEmpty()
            tvTitle.text = value
        }

    var subTitle: String?
        get() = tvSubtitle.text.toString()
        set(value) {
            tvSubtitle.isVisible = !value.isNullOrEmpty()
            tvSubtitle.text = value
        }

    var connectionState: ConnectionState = ConnectionState.CONNECTED
        set(value) {
            field = value
            vIndicator.background.changeColor(ContextCompat.getColor(context, value.color))
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

    var logoPlaceholder: Drawable? = null

    var onToolbarClickListener: OnToolbarClickListener? = null

    private val progress: ProgressBar
    private val ibStart: ImageButton
    private val ibEnd: ImageButton
    private val ivLogo: ImageView
    private val vIndicator: View
    private val tvTitle: TextView
    private val tvSubtitle: TextView
    private val vShadow: View
    private val flEndContainer: FrameLayout
    private val llTitleContainer: LinearLayout
    private val rlContainer: RelativeLayout

    init {
        View.inflate(context, R.layout.layout_ftl_toolbar, this)

        orientation = VERTICAL

        progress = findViewById(R.id.pb_loading)
        ibStart = findViewById(R.id.ib_ftl_toolbar_start)
        ibEnd = findViewById(R.id.ib_ftl_toolbar_end)
        ivLogo = findViewById(R.id.iv_ftl_toolbar_logo)
        vIndicator = findViewById(R.id.v_ftl_toolbar_indicator)
        tvTitle = findViewById(R.id.tv_ftl_toolbar_title)
        tvSubtitle = findViewById(R.id.tv_ftl_toolbar_subtitle)
        flEndContainer = findViewById(R.id.fl_ftl_toolbar_end_container)
        llTitleContainer = findViewById(R.id.ll_ftl_toolbar_title_container)
        vShadow = findViewById(R.id.v_ftl_toolbar_shadow)
        rlContainer = findViewById(R.id.rl_ftl_toolbar_container)

        ibStart.setOnClickListener {
            onToolbarClickListener?.onToolbarClick(it)
        }

        ibEnd.setOnClickListener {
            onToolbarClickListener?.onToolbarClick(it)
        }

        if (rlContainer.background == null) {
            rlContainer.setBackgroundColor(Color.WHITE)
        }

        context.withStyledAttributes(attrs, R.styleable.FTLToolbar) {
            if (hasValue(R.styleable.FTLToolbar_startIcon)) {
                startDrawable = getDrawable(R.styleable.FTLToolbar_startIcon)
            }
            if (hasValue(R.styleable.FTLToolbar_endIcon)) {
                endDrawable = getDrawable(R.styleable.FTLToolbar_endIcon)
            }
            logoPlaceholder = when {
                hasValue(R.styleable.FTLToolbar_logoPlaceholder) ->
                    getDrawable(R.styleable.FTLToolbar_logoPlaceholder)
                        ?: ContextCompat.getDrawable(context, R.drawable.ic_restaurant_placeholder)
                else -> ContextCompat.getDrawable(context, R.drawable.ic_restaurant_placeholder)
            }
            logoIcon = when {
                hasValue(R.styleable.FTLToolbar_logoIcon) ->
                    getDrawable(R.styleable.FTLToolbar_logoIcon) ?: logoPlaceholder
                else -> logoPlaceholder
            }
            isShadowVisible = getBoolean(R.styleable.FTLToolbar_isShadowVisible, false)
            titleColor = getColor(
                R.styleable.FTLToolbar_titleColor,
                ContextCompat.getColor(context, R.color.OnBackgroundPrimary)
            )
            subtitleColor = getColor(
                R.styleable.FTLToolbar_subtitleColor,
                ContextCompat.getColor(context, R.color.AdditionalGreen)
            )
            title = getString(R.styleable.FTLToolbar_title)
            subTitle = getString(R.styleable.FTLToolbar_subtitle)
        }
    }

    fun showProgress() {
        llTitleContainer.isGone = true
        progress.isVisible = true
    }

    fun hideProgress() {
        progress.isGone = true
        llTitleContainer.isVisible = true
    }

    fun showConnectionIndicator() {
        showOnlyOneChild(vIndicator.id)
    }

    fun showEndButton() {
        showOnlyOneChild(ibEnd.id)
    }

    fun showLogo() {
        showOnlyOneChild(ivLogo.id)
    }

    private fun showOnlyOneChild(id: Int) {
        for (i in 0 until flEndContainer.childCount) {
            val child = flEndContainer.getChildAt(i)
            child.isVisible = child.id == id
        }
    }
}