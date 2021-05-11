package com.foodtechlab.ftlandroiduikit.banner

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.*
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.ButtonType
import com.foodtechlab.ftlandroiduikit.button.FTLButton
import com.foodtechlab.ftlandroiduikit.common.DialogButton
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.card.MaterialCardView

class FTLBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle), ThemeManager.ThemeChangedListener,
    View.OnClickListener {

    /**
     * Данный параметр позволяет установить заголовок в компоненте.
     * В случае если заголовок = null, то на верстке заголовок будет скрыт.
     */
    var title: String? = null
        set(value) {
            field = value
            with(tvTitle) {
                field?.let { text = it }
                visibility = if (field == null) View.GONE else View.VISIBLE
            }
        }

    /**
     * Данный параметр позволяет установить описание в компоненте.
     * Описание не может быть null и всегда отображается в разметке.
     */
    var description: String = ""
        set(value) {
            field = value
            tvDescription.text = field
        }

    private val tvTitle: TextView
    private val tvDescription: TextView
    private val llContainer: LinearLayout
    private var onClickBannerListener: OnClickListener? = null

    init {
        inflate(context, R.layout.layout_ftl_banner, this)
        tvTitle = findViewById(R.id.tv_ftl_banner_title)
        tvDescription = findViewById(R.id.tv_ftl_banner_description)
        llContainer = findViewById(R.id.ll_ftl_banner_container)
        radius = context.dpToPx(CARD_RADIUS_DEFAULT)
        cardElevation = context.dpToPx(CARD_ELEVATION_DEFAULT)
        context.withStyledAttributes(attrs, R.styleable.FTLBanner) {
            title = getString(R.styleable.FTLBanner_title)
            description = getString(R.styleable.FTLBanner_description) ?: ""
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

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        tvTitle.setTextColor(ContextCompat.getColor(context, theme.ftlBannerTheme.titleColor))
        tvDescription.setTextColor(
            ContextCompat.getColor(context, theme.ftlBannerTheme.descriptionColor)
        )
        setCardBackgroundColor(ContextCompat.getColor(context, theme.ftlBannerTheme.bgColor))
    }

    override fun onClick(v: View) {
        onClickBannerListener?.onClick(v)
    }

    /**
     * Данный метод позволяет добавить кнопки на компонент.
     * @param buttons Список [DialogButton] для размещения в разметке.
     * В данном компоненте используется только один тип кнопок [ButtonType.ADDITIONAL].
     */
    fun addButtons(buttons: List<DialogButton>) {
        buttons.forEachIndexed { index, btn ->
            val v = FTLButton(context).apply {
                id = btn.id
                text = btn.title
                isWrapContentOnHeight = true
                setButtonType(ButtonType.ADDITIONAL)
                setOnClickListener(this@FTLBanner)
            }
            val params = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.END
            }
            llContainer.addView(v, params)
            if (index != 0) v.setMarginTop()
        }
    }

    /**
     * Данный метод позволяет установить слушатель клика по кнопкам в компоненте.
     * @param clickListener Экземпляр [View.OnClickListener].
     * Для избежания утечек памяти можно передать в параметр null.
     */
    fun setOnClickBannerListener(clickListener: OnClickListener?) {
        onClickBannerListener = clickListener
    }

    private fun View.setMarginTop() {
        val lParams = layoutParams as LinearLayout.LayoutParams
        lParams.updateMargins(
            marginLeft,
            context?.dpToPx(BUTTON_MARGIN_TOP)?.toInt() ?: marginTop,
            marginEnd,
            marginBottom
        )
    }

    companion object {
        private const val CARD_RADIUS_DEFAULT = 8f
        private const val CARD_ELEVATION_DEFAULT = 0F
        private const val BUTTON_MARGIN_TOP = 4f
    }
}