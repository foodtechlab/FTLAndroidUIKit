package com.foodtechlab.ftlandroiduikit.textfield.table

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.divider.FTLDivider
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLTableRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), CoroutineScope {

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

    private val viewThemeManager: ViewThemeManager<FTLTableRowTheme> = FTLTableRowThemeManager()
    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    /**
     * Метод для обновления цветовой гаммы в соответствии с темой
     * @param theme Тип темы приложения
     */
    fun onThemeChanged(theme: FTLTableRowTheme) {
        tvStartColumn.setTextColor(
            ContextCompat.getColor(context, theme.startTextColor)
        )
        tvCenterColumn.setTextColor(
            ContextCompat.getColor(context, theme.centerTextColor)
        )
        tvEndColumn.setTextColor(
            ContextCompat.getColor(context, theme.endTextColor)
        )
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

data class FTLTableRowTheme(
    @ColorRes val startTextColor: Int,
    @ColorRes val centerTextColor: Int,
    @ColorRes val endTextColor: Int
) : ViewTheme()
