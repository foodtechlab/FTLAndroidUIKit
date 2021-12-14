package com.foodtechlab.ftlandroiduikit.imageview.mediapreview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.circular.FTLCircularCheckBox
import com.foodtechlab.ftlandroiduikit.progress.circle.indicator.FTLCircleProgressIndicator
import com.foodtechlab.ftlandroiduikit.textfield.tag.FTLTagView
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

class FTLMediaPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), CoroutineScope {
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

    private val viewThemeManager: ViewThemeManager<FTLMediaPreviewViewTheme> =
        FTLMediaPreviewViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    fun onThemeChanged(theme: FTLMediaPreviewViewTheme) {
        ivRepeat.drawable?.mutate()?.changeColor(
            ContextCompat.getColor(context, theme.accentColor)
        )
        tgVideoDuration.updateTextColorTheme(theme.textColor)
        val backgroundDrawable = background
        backgroundDrawable.mutate().changeColor(
            ContextCompat.getColor(
                context,
                theme.backgroundColor
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

data class FTLMediaPreviewViewTheme(
    @ColorRes val accentColor: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColor: Int
) : ViewTheme()