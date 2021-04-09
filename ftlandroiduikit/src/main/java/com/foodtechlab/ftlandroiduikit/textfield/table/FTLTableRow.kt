package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.FTLDivider
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

class FTLTableRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {

    var isLastRow = false
        set(value) {
            field = value
            vDivider.visibility = if (value) View.GONE else View.VISIBLE
        }

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

    private var vDivider: FTLDivider
    private var tvEndColumn: TextView
    private var tvStartColumn: TextView
    private var tvCenterColumn: TextView

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

        onThemeChanged(ThemeManager.theme)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    /**
     * Метод для обновления цветовой гаммы в соответствии с темой
     * @param theme Тип темы приложения
     */
    override fun onThemeChanged(theme: ThemeManager.Theme) {
        tvStartColumn.setTextColor(
            ContextCompat.getColor(context, theme.ftlTableRowTheme.startTextColor)
        )
        tvCenterColumn.setTextColor(
            ContextCompat.getColor(context, theme.ftlTableRowTheme.centerTextColor)
        )
        tvEndColumn.setTextColor(
            ContextCompat.getColor(context, theme.ftlTableRowTheme.endTextColor)
        )
        vDivider.onThemeChanged(theme)
    }

    /**
     * Метод для конкатенации (объединения) столбцов в строке
     * @param type Тип соединения строк
     * START_AND_CENTER_COLUMNS - используется для объединения первого и второго столбца
     * END_AND_CENTER_COLUMNS - используется для объединения третьего и второго столбца
     * При объединении необходимо вносить данные в крайние столбцы.
     */
    fun concatenateColumns(type: ConcatenationType) {
        tvCenterColumn.layoutParams =
            TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0f)
        when (type) {
            ConcatenationType.START_AND_CENTER_COLUMNS -> tvStartColumn.layoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f)
            else -> tvEndColumn.layoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f)
        }
    }
}