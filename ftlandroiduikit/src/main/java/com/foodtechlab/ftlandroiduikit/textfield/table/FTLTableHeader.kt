package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.divider.FTLDivider
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLTableHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), CoroutineScope {
    private val viewThemeManager = FTLTableHeaderViewTheme()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var tvTitle: TextView
    private var tvSubtitle: TextView
    private var ivImageSlot: ImageView
    private var ivSwitch: ImageView
    private var vTopDivider: FTLDivider
    private var vBottomDivider: FTLDivider
    private var rlContainer: RelativeLayout

    var headerTitle: String = ""
        set(value) {
            field = value
            tvTitle.text = field
        }

    var headerSubtitle: String = ""
        set(value) {
            field = value
            tvSubtitle.text = field
        }

    var showSubtitle = false
        set(value) {
            if (field != value) {
                field = value
                initStateHeader()
            }
        }

    var isUnwrapped = false
        set(value) {
            if (field != value) {
                field = value
                changeStateHeader()
            }
        }

    var isDividersEnabled = false
        set(value) {
            if (field != value) {
                field = value
                initStateHeader()
            }
        }

    var imageType: ImageType = ImageType.CUSTOMER
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    var tableHeaderClickListener: OnTableHeaderClickListener? = null

    @ColorInt
    var imageBackgroundColor = ContextCompat.getColor(context, R.color.IconBackgroundBlueLight)
        set(value) {
            field = value
            ivImageSlot.background?.changeColor(value)
        }

    @ColorInt
    var imageColor = ContextCompat.getColor(context, R.color.IconPrimaryLight)
        set(value) {
            field = value
            ivImageSlot.setColorFilter(field)
        }

    init {
        inflate(context, R.layout.layout_ftl_table_header, this)

        orientation = VERTICAL

        tvTitle = findViewById(R.id.tvTitle)
        tvSubtitle = findViewById(R.id.tvSubtitle)
        ivImageSlot = findViewById(R.id.ivImageSlot)
        ivSwitch = findViewById(R.id.ivSwitch)
        vBottomDivider = findViewById(R.id.vBottomDivider)
        vTopDivider = findViewById(R.id.vTopDivider)
        rlContainer = findViewById(R.id.rlContainer)

        super.setOnClickListener {
            isUnwrapped = !isUnwrapped
            tableHeaderClickListener?.onSwitchClick(isUnwrapped)
        }

        context.withStyledAttributes(attrs, R.styleable.FTLTableHeader) {
            imageType = ImageType.values()[getInt(R.styleable.FTLTableHeader_imageType, 7)]
            headerTitle = getString(R.styleable.FTLTableHeader_headerTitle) ?: ""
            headerSubtitle = getString(R.styleable.FTLTableHeader_headerSubtitle) ?: ""
            isUnwrapped = getBoolean(R.styleable.FTLTableHeader_isUnwrapped, false)
            showSubtitle = getBoolean(R.styleable.FTLTableHeader_showSubtitle, false)
            isDividersEnabled = getBoolean(R.styleable.FTLTableHeader_isDividersEnabled, false)
            imageBackgroundColor = getColor(
                R.styleable.FTLTableHeader_imageBackgroundColor,
                ContextCompat.getColor(context, R.color.IconBackgroundBlueLight)
            )
            imageColor = getColor(
                R.styleable.FTLTableHeader_imageColor,
                ContextCompat.getColor(context, R.color.IconPrimaryLight)
            )
            initStateHeader()
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

    private fun onThemeChanged(theme: FTLTableHeaderTheme) {
        ivSwitch.drawable?.mutate()?.changeColor(
            ContextCompat.getColor(
                context,
                theme.switchIconColor
            )
        )
        vTopDivider.setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.dividerColor
            )
        )
        vBottomDivider.setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.dividerColor
            )
        )
        tvTitle.setTextColor(ContextCompat.getColor(context, theme.titleColor))
        tvSubtitle.setTextColor(
            ContextCompat.getColor(
                context,
                theme.subtitleColor
            )
        )
    }

    private fun initStateHeader() {
        val titleLayoutParams = tvTitle.layoutParams as RelativeLayout.LayoutParams
        val switchLayoutParams = ivSwitch.layoutParams as RelativeLayout.LayoutParams
        ivSwitch.animate().rotation(if (isUnwrapped) 180f else 0f).start()
        if (showSubtitle) {
            tvSubtitle.visibility = View.VISIBLE
            titleLayoutParams.removeRule(RelativeLayout.CENTER_VERTICAL)
            switchLayoutParams.removeRule(RelativeLayout.CENTER_VERTICAL)
        } else {
            tvSubtitle.visibility = View.GONE
            titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            switchLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        }
        if (isDividersEnabled) {
            vTopDivider.visibility = if (showSubtitle) View.GONE else View.VISIBLE
            vBottomDivider.visibility = if (showSubtitle) {
                View.GONE
            } else {
                if (isUnwrapped) View.INVISIBLE else View.VISIBLE
            }
        } else {
            vTopDivider.visibility = View.GONE
            vBottomDivider.visibility = View.GONE
        }
    }

    fun setRippleBackground(forButton: Boolean) {
        val typedValue = TypedValue()
        if (forButton) {
            context.theme
                .resolveAttribute(
                    android.R.attr.selectableItemBackgroundBorderless,
                    typedValue,
                    true
                )
            ivSwitch.setBackgroundResource(typedValue.resourceId)
        } else {
            context.theme
                .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
            rlContainer.setBackgroundResource(typedValue.resourceId)
        }
    }

    fun updatePaddingForContent(start: Float, top: Float, end: Float, bottom: Float) {
        rlContainer.setPadding(
            context.dpToPx(start).toInt(),
            context.dpToPx(top).toInt(),
            context.dpToPx(end).toInt(),
            context.dpToPx(bottom).toInt()
        )
    }

    private fun changeStateHeader() {
        ivSwitch.animate().rotation(if (!isUnwrapped) 0f else 180f).start()
        if (!showSubtitle && isDividersEnabled) {
            vBottomDivider.visibility = if (!isUnwrapped) View.VISIBLE else View.INVISIBLE
        }
    }
}

data class FTLTableHeaderTheme(
    @ColorRes val dividerColor: Int,
    @ColorRes val switchIconColor: Int,
    @ColorRes val titleColor: Int,
    @ColorRes val subtitleColor: Int
) : ViewTheme()
