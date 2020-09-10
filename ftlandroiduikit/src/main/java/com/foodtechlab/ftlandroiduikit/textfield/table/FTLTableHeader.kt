package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLTableHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var tvTitle: TextView
    private var tvSubtitle: TextView
    private var ivImageSlot: ImageView
    private var ivSwitch: ImageView
    private var vTopDivider: View
    private var vBottomDivider: View
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

    var isUnwrapped = false

    var isDividersEnabled = false

    var imageType: ImageType = ImageType.CUSTOMER
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    var tableHeaderClickListener: OnTableHeaderClickListener? = null

    @ColorRes
    var backgroundColorRes = -1
        set(value) {
            field = value
            ivImageSlot.backgroundTintList = ColorStateList.valueOf(field)
        }

    @ColorRes
    var imageColorRes = -1
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
            changeStateHeader()
            tableHeaderClickListener?.onSwitchClick(isUnwrapped)
        }

        context.withStyledAttributes(attrs, R.styleable.FTLTableHeader) {
            imageType = ImageType.values()[getInt(R.styleable.FTLTableHeader_imageType, 7)]
            headerTitle = getString(R.styleable.FTLTableHeader_headerTitle) ?: ""
            headerSubtitle = getString(R.styleable.FTLTableHeader_headerSubtitle) ?: ""
            isUnwrapped = getBoolean(R.styleable.FTLTableHeader_isUnwrapped, false)
            showSubtitle = getBoolean(R.styleable.FTLTableHeader_showSubtitle, false)
            isDividersEnabled = getBoolean(R.styleable.FTLTableHeader_isDividersEnabled, false)
            backgroundColorRes = getColor(
                R.styleable.FTLTableHeader_backgroundColorRes,
                ContextCompat.getColor(context, R.color.AdditionalDarkBlue)
            )
            imageColorRes = getColor(
                R.styleable.FTLTableHeader_imageColorRes,
                ContextCompat.getColor(context, R.color.BackgroundPrimary)
            )
            initStateHeader()
        }
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
        ivSwitch.animate().rotation(if (isUnwrapped) 0f else 180f).start()
        vBottomDivider.visibility
        if (!showSubtitle && isDividersEnabled) {
            vBottomDivider.visibility = if (isUnwrapped) View.VISIBLE else View.INVISIBLE
        }

        isUnwrapped = !isUnwrapped
    }
}