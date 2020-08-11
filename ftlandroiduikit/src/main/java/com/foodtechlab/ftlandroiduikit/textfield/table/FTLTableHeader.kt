package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
    private var llMainContainer: LinearLayout
    private var llContentContainer: LinearLayout

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
            field = value
            initStateHeader()
        }

    var isUnwrapped = false
        set(value) {
            field = value
            initStateHeader()
        }

    var imageType: ImageType = ImageType.CUSTOMER
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    var tableHeaderClickListener: OnTableHeaderClickListener? = null

    init {
        inflate(context, R.layout.layout_ftl_table_header, this)
        tvTitle = findViewById(R.id.tv_title)
        tvSubtitle = findViewById(R.id.tv_subtitle)
        ivImageSlot = findViewById(R.id.iv_image_slot)
        ivSwitch = findViewById(R.id.iv_switch)
        vBottomDivider = findViewById(R.id.v_bottom_divider)
        vTopDivider = findViewById(R.id.v_top_divider)
        llMainContainer = findViewById(R.id.ll_container)
        llContentContainer = findViewById(R.id.ll_content_container)
        llMainContainer.setOnClickListener {
            changeStateHeader()
            tableHeaderClickListener?.onSwitchClick(isUnwrapped)
        }
        context.withStyledAttributes(attrs, R.styleable.FTLTableHeader) {
            imageType = ImageType.values()[getInt(R.styleable.FTLTableHeader_imageType, 7)]
            ivImageSlot.setImageResource(imageType.imgRes)
            headerTitle = getString(R.styleable.FTLTableHeader_headerTitle) ?: ""
            headerSubtitle = getString(R.styleable.FTLTableHeader_headerSubtitle) ?: ""
            isUnwrapped = getBoolean(R.styleable.FTLTableHeader_isUnwrapped, false)
            showSubtitle = getBoolean(R.styleable.FTLTableHeader_showSubtitle, false)
            initStateHeader()
        }
    }

    private fun initStateHeader() {
        if (isUnwrapped) {
            ivSwitch.animate().rotation(180f).start()
            if (showSubtitle) {
                tvSubtitle.visibility = View.VISIBLE
                vTopDivider.visibility = View.GONE
                vBottomDivider.visibility = View.GONE
                llContentContainer.gravity = Gravity.NO_GRAVITY
            } else {
                tvSubtitle.visibility = View.GONE
                vTopDivider.visibility = View.VISIBLE
                vBottomDivider.visibility = View.INVISIBLE
                llContentContainer.gravity = Gravity.CENTER_VERTICAL

            }
        } else {
            ivSwitch.animate().rotation(0f).start()

            if (showSubtitle) {
                tvSubtitle.visibility = View.VISIBLE
                vTopDivider.visibility = View.GONE
                vBottomDivider.visibility = View.GONE
                llContentContainer.gravity = Gravity.NO_GRAVITY
            } else {
                tvSubtitle.visibility = View.GONE
                vTopDivider.visibility = View.VISIBLE
                vBottomDivider.visibility = View.VISIBLE
                llContentContainer.gravity = Gravity.CENTER_VERTICAL
            }
        }
    }

    fun updatePaddingForContent(start: Float, top: Float, end: Float, bottom: Float) {
        llContentContainer.setPadding(
            context.dpToPx(start).toInt(),
            context.dpToPx(top).toInt(),
            context.dpToPx(end).toInt(),
            context.dpToPx(bottom).toInt()
        )
    }

    fun setRippleBackground(forButton: Boolean) {
        val typedValue = TypedValue()
        if (forButton) {
            context.theme
                .resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true)
            ivSwitch.setBackgroundResource(typedValue.resourceId)
        } else {
            context.theme
                .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
            llContentContainer.setBackgroundResource(typedValue.resourceId)
        }
    }

    private fun changeStateHeader() {
        if (isUnwrapped) {
            ivSwitch.animate().rotation(0f).start()
            if (!showSubtitle) vBottomDivider.visibility = View.VISIBLE
        } else {
            ivSwitch.animate().rotation(180f).start()
            if (!showSubtitle) vBottomDivider.visibility = View.INVISIBLE
        }
        isUnwrapped = !isUnwrapped
    }
}