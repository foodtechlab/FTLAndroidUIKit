package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R

class FTLTextAddresses @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var tvAddressFrom: TextView
    private var tvAddressTo: TextView

    var textAddressFrom: String = ""
        set(value) {
            field = value
            tvAddressFrom.text = field
        }

    var textAddressTo: String = ""
        set(value) {
            field = value
            tvAddressTo.text = field
        }

    init {
        inflate(context, R.layout.layout_ftl_text_addresses, this)
        tvAddressFrom = findViewById(R.id.tv_address_from)
        tvAddressTo = findViewById(R.id.tv_address_to)

        context.withStyledAttributes(attrs, R.styleable.FTLTextAddresses) {
            textAddressFrom = getString(R.styleable.FTLTextAddresses_textAddressFrom) ?: ""
            textAddressTo = getString(R.styleable.FTLTextAddresses_textAddressTo) ?: ""
        }
    }
}



