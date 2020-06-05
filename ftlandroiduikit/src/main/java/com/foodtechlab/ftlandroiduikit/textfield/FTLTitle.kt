package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R

class FTLTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val displayDensity = resources.displayMetrics.density

    var isTitleVisible: Boolean
        get() = tvTitle.isVisible
        set(value) {
            tvTitle.isVisible = value
        }

    var isSubtitleVisible: Boolean
        get() = tvTitle.isVisible
        set(value) {
            tvSubtitle.isVisible = value
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

    private val tvTitle: TextView
    private val tvSubtitle: TextView

    init {
        View.inflate(context, R.layout.layout_ftl_title, this)

        orientation = VERTICAL

        setPadding(
            (20f * displayDensity).toInt(),
            (8f * displayDensity).toInt(),
            (20f * displayDensity).toInt(),
            (8f * displayDensity).toInt()
        )

        tvTitle = findViewById(R.id.tv_ftl_title)
        tvSubtitle = findViewById(R.id.tv_ftl_subtitle)

        context.withStyledAttributes(attrs, R.styleable.FTLTitle) {
            titleColor = getColor(
                R.styleable.FTLTitle_title_color,
                ContextCompat.getColor(context, R.color.OnBackgroundPrimary)
            )
            subtitleColor = getColor(
                R.styleable.FTLTitle_subtitle_color,
                ContextCompat.getColor(context, R.color.AdditionalGreen)
            )
            title = getString(R.styleable.FTLTitle_title_text)
            subTitle = getString(R.styleable.FTLTitle_subtitle_text)
        }
    }
}