package com.foodtechlab.ftlandroiduikit.media

import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.FTLCircularCheckBox
import com.foodtechlab.ftlandroiduikit.button.image.FTLImageButton
import com.foodtechlab.ftlandroiduikit.button.image.ImageButtonType
import com.foodtechlab.ftlandroiduikit.common.OnFTLCheckedChangeListener
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit


class FTLPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener,
    MediaPlayer.OnCompletionListener {
    var isVisibleCheckbox: Boolean = false
        get() = cbCheckedSlot.isVisible
        private set(value) {
            field = value
            cbCheckedSlot.isVisible = field
            updateControlPanelState(!field)
        }

    var dataSource: String? = null
        set(value) {
            field = value
            mediaPlayer = MediaPlayer().apply {
                setDataSource(field)
                setOnCompletionListener(this@FTLPlayerView)
                prepare()
            }
            val currentPositionMillis = mediaPlayer?.currentPosition ?: 0
            val durationMillis = mediaPlayer?.duration ?: 0
            sbCurrentProgress.max = durationMillis / 1000
            sbCurrentProgress.progress = currentPositionMillis / 1000
            changeTimeline(currentPositionMillis.toLong(), durationMillis.toLong())
        }

    var isCheckedState: Boolean = false
        get() = cbCheckedSlot.isChecked
        private set(value) {
            field = value
            cbCheckedSlot.isChecked = field
        }

    var onCheckedChangeListener: OnFTLCheckedChangeListener? = null

    @ColorRes
    private var seekBarProgressColorRes = -1

    @ColorRes
    private var seekBarSecondaryProgressColorRes = -1

    @ColorRes
    private var seekBarBackgroundColorRes = -1

    @ColorRes
    private var seekBarThumbColorRes = -1

    private var backgroundControlContainerColorRes = -1

    @ColorRes
    private var textTimeColorRes = -1

    private val drawableProgress = ResourcesCompat.getDrawable(
        resources,
        R.drawable.layer_list_ftl_seekbar_progress,
        null
    ) as? LayerDrawable

    private val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, context.dpToPx(CORNERS))
        .build()

    private val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
    private val rlMediaControlContainer: RelativeLayout
    private val cbCheckedSlot: FTLCircularCheckBox
    private val glVerticalStop: Guideline
    private val tvTime: TextView
    private val sbCurrentProgress: SeekBar
    private val btnMediaControl: FTLImageButton
    private var mediaPlayer: MediaPlayer? = null
    private var timer: Timer? = null

    init {
        View.inflate(context, R.layout.layout_ftl_player_view, this)

        cbCheckedSlot = findViewById(R.id.cb_ftl_player_view)
        glVerticalStop = findViewById(R.id.gl_ftl_player_view)
        rlMediaControlContainer = findViewById(R.id.rl_ftl_player_view_container)
        tvTime = findViewById(R.id.tv_ftl_player_view_time)
        sbCurrentProgress = findViewById(R.id.sb_ftl_player_view_progress)
        btnMediaControl = findViewById(R.id.btn_ftl_player_view_media_control)

        isVisibleCheckbox = false
        isCheckedState = false

        cbCheckedSlot.setOnCheckedChangeListener { view, isChecked ->
            onCheckedChangeListener?.onFTLCheckedChanged(view, isChecked)
        }

        btnMediaControl.setOnClickListener {
            if (!btnMediaControl.isSelected) {
                if (btnMediaControl.type == ImageButtonType.PLAY_MEDIUM) {
                    btnMediaControl.type = ImageButtonType.PAUSE_MEDIUM
                    startPlaying()
                } else {
                    btnMediaControl.type = ImageButtonType.PLAY_MEDIUM
                    pausePlaying()
                }
            }
        }

        onThemeChanged(ThemeManager.theme)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        preparePlayingIfNeed()
        ThemeManager.removeListener(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        updateSeekBarProgressColors(
            seekBarProgressColorRes,
            seekBarSecondaryProgressColorRes,
            seekBarBackgroundColorRes
        )
        updateTextTimeColor(textTimeColorRes)
        updateSeekBarThumbColor(seekBarThumbColorRes)
        updateBackgroundColorTheme(backgroundControlContainerColorRes)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        timer?.cancel()
        btnMediaControl.type = ImageButtonType.PLAY_MEDIUM
        resetProgress()
    }

    fun updateSeekBarProgressColors(
        @ColorRes progressColorRes: Int = -1,
        @ColorRes secondaryProgressColorRes: Int = -1,
        @ColorRes backgroundColorRes: Int = -1
    ) {
        seekBarProgressColorRes = progressColorRes
        seekBarSecondaryProgressColorRes = secondaryProgressColorRes
        seekBarBackgroundColorRes = backgroundColorRes

        val progressDrawable = drawableProgress?.findDrawableByLayerId(
            android.R.id.progress
        ) as? ClipDrawable

        val secondaryProgressDrawable = drawableProgress?.findDrawableByLayerId(
            android.R.id.secondaryProgress
        ) as? ClipDrawable

        val backgroundDrawable = drawableProgress?.findDrawableByLayerId(
            android.R.id.background
        ) as? GradientDrawable

        progressDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                if (seekBarProgressColorRes != -1) seekBarProgressColorRes
                else ThemeManager.theme.ftlPlayerViewTheme.progressSeekBarColor
            )
        )

        secondaryProgressDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                if (seekBarSecondaryProgressColorRes != -1) seekBarSecondaryProgressColorRes
                else ThemeManager.theme.ftlPlayerViewTheme.secondaryProgressSeekBarColor
            )
        )

        backgroundDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                if (backgroundColorRes != -1) backgroundColorRes
                else ThemeManager.theme.ftlPlayerViewTheme.bgSeekBarColor
            )
        )

        sbCurrentProgress.progressDrawable = drawableProgress
    }

    fun updateSeekBarThumbColor(@ColorRes colorRes: Int = -1) {
        seekBarThumbColorRes = colorRes
        sbCurrentProgress.thumbTintList = ContextCompat.getColorStateList(
            context,
            if (seekBarThumbColorRes != -1) seekBarThumbColorRes
            else ThemeManager.theme.ftlPlayerViewTheme.thumbColor
        )
    }

    fun updateTextTimeColor(@ColorRes colorRes: Int = -1) {
        textTimeColorRes = colorRes
        tvTime.setTextColor(
            ContextCompat.getColor(
                context,
                if (textTimeColorRes != -1) textTimeColorRes
                else ThemeManager.theme.ftlPlayerViewTheme.textColor
            )
        )
    }

    fun updateControlPanelState(isEnabled: Boolean) {
        btnMediaControl.isSelected = !isEnabled
        sbCurrentProgress.isEnabled = isEnabled
        if (isEnabled) {
            updateSeekBarProgressColors(
                seekBarProgressColorRes,
                seekBarSecondaryProgressColorRes,
                seekBarBackgroundColorRes
            )
            updateSeekBarThumbColor(seekBarThumbColorRes)
        } else {
            preparePlayingIfNeed()
            setDisabledSeekBarColors()
        }
    }

    fun updateBackgroundColorTheme(@ColorRes colorRes: Int) {
        backgroundControlContainerColorRes = colorRes
        shapeDrawable.fillColor = ContextCompat.getColorStateList(
            context,
            if (backgroundControlContainerColorRes != -1) backgroundControlContainerColorRes
            else ThemeManager.theme.ftlPlayerViewTheme.bgControlColor
        )
        rlMediaControlContainer.background = shapeDrawable
    }

    fun setVisibilityOfCheckbox(shouldVisible: Boolean, showAnimation: Boolean) {
        // Важный момент! В случае если чекбокс нужно отобразить, необходимо это сделать уже
        // после перепривязки. В противоположном случае, необходимо сначала убрать чекбокс
        // и только после этого делать перепривязку
        val set = ConstraintSet()
        set.clone(this)

        if (shouldVisible) {
            set.connect(
                rlMediaControlContainer.id,
                ConstraintSet.START,
                glVerticalStop.id,
                ConstraintSet.END
            )
        } else {
            isVisibleCheckbox = false
            set.connect(
                rlMediaControlContainer.id,
                ConstraintSet.START,
                this.id,
                ConstraintSet.START
            )
        }

        if (showAnimation) {
            TransitionManager.beginDelayedTransition(this)
        }

        set.applyTo(this)

        isVisibleCheckbox = shouldVisible
    }

    private fun setDisabledSeekBarColors() {
        val progressDrawable = drawableProgress?.findDrawableByLayerId(
            android.R.id.progress
        ) as? ClipDrawable

        val secondaryProgressDrawable = drawableProgress?.findDrawableByLayerId(
            android.R.id.secondaryProgress
        ) as? ClipDrawable

        val backgroundDrawable = drawableProgress?.findDrawableByLayerId(
            android.R.id.background
        ) as? GradientDrawable

        progressDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlPlayerViewTheme.progressSeekBarDisabledColor
            )
        )

        secondaryProgressDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlPlayerViewTheme.secondaryProgressSeekBarDisabledColor
            )
        )

        backgroundDrawable?.changeColor(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlPlayerViewTheme.bgSeekBarColor
            )
        )

        sbCurrentProgress.progressDrawable = drawableProgress

        sbCurrentProgress.thumbTintList = ContextCompat.getColorStateList(
            context,
            ThemeManager.theme.ftlPlayerViewTheme.thumbDisabledColor
        )
    }

    private fun preparePlayingIfNeed() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                timer?.cancel()
                btnMediaControl.type = ImageButtonType.PLAY_MEDIUM
                resetProgress()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
        }
    }

    private fun startPlaying() {
        try {
            timer?.cancel()
            timer = Timer()
            timer?.schedule(RemainedDurationTask(), 0L, 1000L)
            mediaPlayer?.start()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
        }
    }

    private fun pausePlaying() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
        }
    }

    private fun resetProgress() {
        val duration: Int = mediaPlayer?.duration ?: 0
        sbCurrentProgress.progress = 0
        changeTimeline(0L, duration.toLong())
    }

    private fun changeTimeline(currentPositionMillis: Long, durationMillis: Long) {
        /*val currentValue = String.format(
            "%02d:%02d:%02d",
            currentPositionSecs / 3600,
            (currentPositionSecs % 3600) / 60,
            currentPositionSecs % 60
        )

        val minutes =
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
*/
        val progressValue = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(currentPositionMillis) % 60,
            TimeUnit.MILLISECONDS.toSeconds(currentPositionMillis) % 60,
        )

        val durationValue = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60,
            TimeUnit.MILLISECONDS.toSeconds(durationMillis) % 60,
        )

        tvTime.text = resources.getString(R.string.format_timeline, progressValue, durationValue)
    }

    companion object {
        private const val CORNERS = 8F
        private const val TAG = "FTLPlayerView"
    }

    inner class ProgressTaskRunnable : Runnable {
        override fun run() {
            val currentPosition: Int = mediaPlayer?.currentPosition ?: 0
            val duration: Int = mediaPlayer?.duration ?: 0
            sbCurrentProgress.progress = currentPosition / 1000
            changeTimeline(currentPosition.toLong(), duration.toLong())
        }
    }

    inner class RemainedDurationTask : TimerTask() {
        override fun run() {
            val progressHandler = Handler(Looper.getMainLooper())
            val progressRunnable = ProgressTaskRunnable()
            progressHandler.post(progressRunnable)
        }
    }
}