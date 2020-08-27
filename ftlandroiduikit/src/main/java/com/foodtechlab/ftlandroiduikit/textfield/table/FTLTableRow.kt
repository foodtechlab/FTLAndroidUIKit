package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R

class FTLTableRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var tvStartColumn: TextView
    private var tvCenterColumn: TextView
    private var tvEndColumn: TextView
    private var vDivider: View

    var textForStartColumn: String = ""
        set(value) {
            field = value
            tvStartColumn.text = field
        }

    var textForCenterColumn: String = ""
        set(value) {
            field = value
            tvCenterColumn.text = field
        }

    var textForEndColumn: String = ""
        set(value) {
            field = value
            tvEndColumn.text = field
        }

    var isLastRow = false
        set(value) {
            field = value
            vDivider.visibility = if (value) View.GONE else View.VISIBLE
        }

    init {
        inflate(context, R.layout.layout_ftl_table_row, this)
        tvStartColumn = findViewById(R.id.tv_start_column)
        tvCenterColumn = findViewById(R.id.tv_center_column)
        tvEndColumn = findViewById(R.id.tv_end_column)
        vDivider = findViewById(R.id.v_bottom_divider)

        context.withStyledAttributes(attrs, R.styleable.FTLTableRow) {
            textForStartColumn = getString(R.styleable.FTLTableRow_textForStartColumn) ?: ""
            textForCenterColumn = getString(R.styleable.FTLTableRow_textForCenterColumn) ?: ""
            textForEndColumn = getString(R.styleable.FTLTableRow_textForEndColumn) ?: ""
            isLastRow = getBoolean(R.styleable.FTLTableRow_isLastRow, false)
        }
    }
}