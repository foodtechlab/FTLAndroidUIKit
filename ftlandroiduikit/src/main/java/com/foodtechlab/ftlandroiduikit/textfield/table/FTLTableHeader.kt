package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType


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
            ivImageSlot.setImageResource(imageType.imgRes)
            headerTitle = getString(R.styleable.FTLTableHeader_headerTitle) ?: ""
            headerSubtitle = getString(R.styleable.FTLTableHeader_headerSubtitle) ?: ""
            isUnwrapped = getBoolean(R.styleable.FTLTableHeader_isUnwrapped, false)
            showSubtitle = getBoolean(R.styleable.FTLTableHeader_showSubtitle, false)
            initStateHeader()
        }
    }

    private fun initStateHeader() {
        ivSwitch.animate().rotation(if (isUnwrapped) 180f else 0f).start()

        val titleLayoutParams = tvTitle.layoutParams as RelativeLayout.LayoutParams
        if (showSubtitle) {
            tvSubtitle.visibility = View.VISIBLE
            vTopDivider.visibility = View.GONE
            vBottomDivider.visibility = View.GONE
            titleLayoutParams.removeRule(RelativeLayout.CENTER_VERTICAL)
            tvTitle.layoutParams = titleLayoutParams
        } else {
            tvSubtitle.visibility = View.GONE
            vTopDivider.visibility = View.VISIBLE
            vBottomDivider.visibility = View.INVISIBLE
            titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            tvTitle.layoutParams = titleLayoutParams
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