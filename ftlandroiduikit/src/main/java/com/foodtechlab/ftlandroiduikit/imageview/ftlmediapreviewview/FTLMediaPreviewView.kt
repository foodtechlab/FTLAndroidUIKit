package com.foodtechlab.ftlandroiduikit.imageview.ftlmediapreviewview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.FTLCircularCheckBox
import com.foodtechlab.ftlandroiduikit.progress.FTLCircleProgressIndicator
import com.foodtechlab.ftlandroiduikit.textfield.FTLTagView
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class FTLMediaPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
    var isWaitForReplay = false
        set(value) {
            field = value
            ivRepeat.isVisible = field
        }

    var imageResource: Drawable? = null
        get() = ivContent.drawable
        set(value) {
            field = value
            ivContent.setImageDrawable(field)
        }

    var videoDuration: Long = 0L
        set(value) {
            field = value

            val hours = TimeUnit.MILLISECONDS.toHours(value)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(value) % 60 + hours * 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(value - minutes * 60 * 1000) % 3600
            val format = R.string.format_mm_ss

            tgVideoDuration.tag = String.format(
                context.getString(format),
                abs(minutes),
                abs(seconds)
            )
        }

    var previewContentType: PreviewContentType = PreviewContentType.IMAGE
        set(value) {
            field = value
            tgVideoDuration.isVisible = field == PreviewContentType.VIDEO
        }

    val ivContent: ImageView

    private var cbSelect: FTLCircularCheckBox
    private var tgVideoDuration: FTLTagView
    private var ivRepeat: ImageView
    private var cpiProgress: FTLCircleProgressIndicator

    init {
        inflate(context, R.layout.layout_ftl_media_preview_view, this)
        cbSelect = findViewById(R.id.cb_ftl_media_preview_select)
        tgVideoDuration = findViewById(R.id.tg_ftl_media_preview_video_duration)
        ivContent = findViewById(R.id.iv_ftl_media_preview_content)
        ivRepeat = findViewById(R.id.iv_ftl_media_preview_repeat)
        cpiProgress = findViewById(R.id.cpi_ftl_media_preview_progress)

        context.withStyledAttributes(attrs, R.styleable.FTLMediaPreviewView) {
            if (hasValue(R.styleable.FTLMediaPreviewView_imageResource)) {
                imageResource = getDrawable(R.styleable.FTLMediaPreviewView_imageResource)
            }
            videoDuration = getInt(R.styleable.FTLMediaPreviewView_videoDuration, 0).toLong()
            previewContentType = PreviewContentType.values()[getInt(
                R.styleable.FTLMediaPreviewView_previewContentType,
                0
            )]
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
        ivRepeat.drawable?.mutate()?.changeColor(
            ContextCompat.getColor(context, theme.ftlMediaPreviewViewTheme.accentColor)
        )
        tgVideoDuration.updateTextColorTheme(theme.ftlMediaPreviewViewTheme.textColor)
        val backgroundDrawable = background
        backgroundDrawable.mutate().changeColor(
            ContextCompat.getColor(
                context,
                theme.ftlMediaPreviewViewTheme.backgroundColor
            )
        )
        background = backgroundDrawable
    }

    fun showProgress() {
        ivContent.isVisible = false
        ivRepeat.isVisible = false
        tgVideoDuration.isVisible = false
        cpiProgress.isVisible = true
    }

    fun hideProgress() {
        ivContent.isVisible = true
        tgVideoDuration.isVisible = previewContentType == PreviewContentType.VIDEO
        cpiProgress.isVisible = false
    }
}