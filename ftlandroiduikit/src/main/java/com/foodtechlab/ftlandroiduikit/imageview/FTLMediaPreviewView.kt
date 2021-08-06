package com.foodtechlab.ftlandroiduikit.imageview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.FTLCircularCheckBox
import com.foodtechlab.ftlandroiduikit.textfield.FTLTagView
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

class FTLMediaPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
    var imageResource: Drawable? = null
        get() = ivContent.drawable
        set(value) {
            field = value
            ivContent.setImageDrawable(field)
        }

    private var cbSelect: FTLCircularCheckBox
    private var tgVideoDuration: FTLTagView
    private var ivContent: ImageView

    init {
        inflate(context, R.layout.layout_ftl_media_preview_view, this)
        cbSelect = findViewById(R.id.cb_ftl_media_preview_select)
        tgVideoDuration = findViewById(R.id.tg_ftl_media_preview_video_duration)
        ivContent = findViewById(R.id.iv_ftl_media_preview_content)

        context.withStyledAttributes(attrs, R.styleable.FTLMediaPreviewView) {

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
        
    }
}