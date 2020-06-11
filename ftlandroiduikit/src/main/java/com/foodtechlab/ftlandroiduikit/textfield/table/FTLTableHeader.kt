package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
    private var ivImageSlot: ImageView
    private var ivSwitch: ImageView
    private var vDivider: View
    private var llContainer: LinearLayout

    var titleForHeader: String = ""
        set(value) {
            field = value
            tvTitle.text = field

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
        ivImageSlot = findViewById(R.id.iv_image_slot)
        ivSwitch = findViewById(R.id.iv_switch)
        vDivider = findViewById(R.id.v_bottom_divider)
        llContainer = findViewById(R.id.ll_container)
        llContainer.setOnClickListener {
            changeStateHeader()
            tableHeaderClickListener?.onSwitchClick(isUnwrapped)
        }
        context.withStyledAttributes(attrs, R.styleable.FTLTableHeader) {
            imageType = ImageType.values()[getInt(R.styleable.FTLTableHeader_imageType, 7)]
            ivImageSlot.setImageResource(imageType.imgRes)
            titleForHeader = getString(R.styleable.FTLTableHeader_titleForHeader) ?: ""
            isUnwrapped = getBoolean(R.styleable.FTLTableHeader_isUnwrapped, false)
            tvTitle.text = titleForHeader
            initStateHeader()
        }
    }

    private fun initStateHeader(){
        if (isUnwrapped) {
            ivSwitch.animate().rotation(180f).start()
            vDivider.visibility = View.INVISIBLE
        } else {
            ivSwitch.animate().rotation(0f).start()
            vDivider.visibility = View.VISIBLE
        }
    }

    private fun changeStateHeader() {
        if (isUnwrapped) {
            ivSwitch.animate().rotation(0f).start()
            vDivider.visibility = View.VISIBLE
        } else {
            ivSwitch.animate().rotation(180f).start()
            vDivider.visibility = View.INVISIBLE
        }
        isUnwrapped = !isUnwrapped
    }
}