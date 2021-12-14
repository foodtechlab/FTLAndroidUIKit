package com.foodtechlab.ftlandroiduikit.button.timer

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Handler
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.ProgressView
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.timer.FTLTimerDotsProgress
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.getMillis
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

/**
 * Created by Umalt on 16.06.2020
 */
class FTLTimerButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), CoroutineScope {

    private val radius = 8 * resources.displayMetrics.density

    private var isTimerRenewableInside = true

    private var onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    private val viewThemeManager: ViewThemeManager<FTLTimerButtonTheme> =
        FTLTimerButtonThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    var inProgress = false
        private set

    var autoAnimateProgress: Boolean
        get() = progressView.autoAnimate
        set(value) {
            progressView.autoAnimate = value
        }

    @ColorRes
    var textColorRes = -1

    @ColorRes
    var dotColorRes = -1

    @ColorRes
    var bounceDotColorRes = -1

    var estimateDuration = 0L

    var accompanyingText: String? = null
        set(value) {
            field = value
            tvLabel.text = value
        }

    private var remainedDuration = 0L
        private set(value) {
            field = value

            val hours = TimeUnit.MILLISECONDS.toHours(value)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(value) % 60 + hours * 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(value - minutes * 60 * 1000) % 3600

            val format = if (seconds < 0 || minutes < 0) {
                R.string.format_negative_mm_ss
            } else {
                R.string.format_mm_ss
            }

            deliveryTime = String.format(context.getString(format), abs(minutes), abs(seconds))
        }

    private var state = State.NEW

    var deliveryTime: String? = if (isInEditMode) "00:00" else null
        set(value) {
            field = value
            tvTime.text = value
        }

    var timeZoneId: String? = null

    var estimateSuccessAt: String? = null
        set(value) {
            field = value
            if (isTimerRenewableInside) {
                launchTimerTask(getMillis(value, timeZoneId) - System.currentTimeMillis())
            }
        }

    var heightButton: Float = 40f
        set(value) {
            field = value
            val params: LayoutParams = rlRoot.layoutParams as LayoutParams
            params.height = context.dpToPx(value).toInt()
            rlRoot.layoutParams = params
        }

    private val fadeTransition = Fade().apply { duration = 200 }

    private var timer: Timer? = null

    private var clickListener: OnClickListener? = null

    private val rlRoot: RelativeLayout
    private val llContainer: LinearLayout
    private val progressView: ProgressView
    private val tvTime: TextView
    private val tvLabel: TextView
    private val dotProgress: FTLTimerDotsProgress

    internal class RemainedDurationTask(button: FTLTimerButton) : TimerTask() {
        private var btnReference: WeakReference<FTLTimerButton>? = null

        init {
            btnReference = WeakReference(button)
        }

        override fun run() {
            btnReference?.get()?.let {
                val timerHandler = Handler(Looper.getMainLooper())
                val timerRunnable = TimerTaskRunnable(it)
                timerHandler.post(timerRunnable)
            }
        }
    }

    internal class TimerTaskRunnable(val button: FTLTimerButton) : Runnable {

        override fun run() {
            button.remainedDuration -= 1000L
            if (button.estimateDuration != 0L) {
                button.progressView.progress =
                    button.remainedDuration * 100f / button.estimateDuration
            }
        }

    }

    init {
        inflate(context, R.layout.layout_ftl_timer_button, this)

        rlRoot = findViewById(R.id.rl_root)
        progressView = findViewById(R.id.progress_view)
        llContainer = findViewById(R.id.ll_container)
        tvTime = findViewById(R.id.tv_time)
        tvLabel = findViewById(R.id.tv_label)
        dotProgress = findViewById(R.id.dot_progress)

        context.withStyledAttributes(attrs, R.styleable.FTLTimerButton) {
            isTimerRenewableInside = getBoolean(R.styleable.FTLTimerButton_isRenewableInside, true)
            accompanyingText = getString(R.styleable.FTLTimerButton_accompanyingText)
            heightButton = getFloat(R.styleable.FTLTimerButton_heightButton, 40f)

            setupColors()

            updateViewState(
                getColor(R.styleable.FTLTimerButton_ftlTimerButton_textColor, -1),
                getColor(R.styleable.FTLTimerButton_ftlTimerButton_dotColor, -1),
                getColor(R.styleable.FTLTimerButton_ftlTimerButton_bounceDotColor, -1),
                getColorStateList(R.styleable.FTLTimerButton_ftlTimerButton_textColor)
            )
        }

        super.setOnClickListener { if (!inProgress) clickListener?.onClick(it) }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        rlRoot.apply {
            onGlobalLayoutListener?.let {
                viewTreeObserver.removeOnGlobalLayoutListener(it)
            }
            onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                if (measuredWidth > 0) {
                    progressView.updateLayoutParams {
                        width = measuredWidth
                        height = measuredHeight
                    }
                    viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
                }
            }
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        }
        updateDotProgressVisibility(inProgress)
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                    onThemeChanged(theme)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable =
        SavedState(super.onSaveInstanceState()).apply {
            inProgress = this@FTLTimerButton.inProgress
            estimateDuration = this@FTLTimerButton.estimateDuration
            remainedDuration = this@FTLTimerButton.remainedDuration
            estimateSuccessAt = this@FTLTimerButton.estimateSuccessAt
            textColorRes = this@FTLTimerButton.textColorRes
            dotColorRes = this@FTLTimerButton.dotColorRes
            bounceDotColorRes = this@FTLTimerButton.bounceDotColorRes
            state = this@FTLTimerButton.state
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            updateDotProgressVisibility(state.inProgress)
            estimateDuration = state.estimateDuration
            remainedDuration = state.remainedDuration
            estimateSuccessAt = state.estimateSuccessAt
            this.state = state.state
            updateTextColor(state.textColorRes)
            updateDotColor(state.dotColorRes)
            updateBounceDotColor(state.bounceDotColorRes)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
        rlRoot.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        onGlobalLayoutListener = null
    }

    fun onThemeChanged(theme: FTLTimerButtonTheme) {
        State.NEW.textColor = theme.textColorNew
        State.NEW.progressColor = theme.progressColorNew
        State.NEW.progressBgColor = theme.progressBgColorNew
        State.NEW.dotColor = theme.dotColorNew
        State.NEW.bounceDotColor = theme.bounceDotColorNew

        State.READY_FOR_DELIVERY.textColor = theme.textColorReadyForDelivery
        State.READY_FOR_DELIVERY.progressColor =
            theme.progressColorReadyForDelivery
        State.READY_FOR_DELIVERY.progressBgColor =
            theme.progressBgColorReadyForDelivery
        State.READY_FOR_DELIVERY.dotColor = theme.dotColorReadyForDelivery
        State.READY_FOR_DELIVERY.bounceDotColor =
            theme.bounceDotColorReadyForDelivery

        State.IN_DELIVERY.textColor = theme.textColorInDelivery
        State.IN_DELIVERY.progressColor = theme.progressColorInDelivery
        State.IN_DELIVERY.progressBgColor = theme.progressBgColorInDelivery
        State.IN_DELIVERY.dotColor = theme.dotColorInDelivery
        State.IN_DELIVERY.bounceDotColor = theme.bounceDotColorInDelivery

        updateViewState()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    private fun launchTimerTask(value: Long) {
        remainedDuration = value
        timer?.cancel()
        timer = Timer()
        timer?.schedule(RemainedDurationTask(this), 0L, 1000L)
    }

    fun updateRemainedDuration(remDuration: Long) {
        remainedDuration = remDuration
        if (estimateDuration != 0L) {
            progressView.progress = remainedDuration * 100f / estimateDuration
        }
    }

    fun updateTextColor(@ColorRes colorRes: Int) {
        textColorRes = colorRes
        if (textColorRes != -1) {
            val colorStateList = ContextCompat.getColorStateList(context, textColorRes)
            if (colorStateList != null) {
                tvTime.setTextColor(colorStateList)
                tvLabel.setTextColor(colorStateList)
            } else {
                tvTime.setTextColor(ContextCompat.getColor(context, textColorRes))
                tvLabel.setTextColor(ContextCompat.getColor(context, textColorRes))
            }
        } else {
            setTextColorFromState()
        }
    }

    fun updateDotColor(@ColorRes colorRes: Int) {
        dotColorRes = colorRes
        state.dotColor?.let { dotColor ->
            dotProgress.dotColor = ContextCompat.getColor(
                context,
                if (dotColorRes != -1) dotColorRes else dotColor
            )
        }
    }

    fun updateBounceDotColor(@ColorRes colorRes: Int) {
        bounceDotColorRes = colorRes
        state.bounceDotColor?.let { bounceDotColor ->
            dotProgress.bounceDotColor = ContextCompat.getColor(
                context,
                if (bounceDotColorRes != -1) bounceDotColorRes else bounceDotColor
            )
        }
    }

    private fun updateViewState(
        @ColorInt textColor: Int = -1,
        @ColorInt dotColor: Int = -1,
        @ColorInt bounceDotColor: Int = -1,
        texColorStateList: ColorStateList? = null
    ) {
        TransitionManager.beginDelayedTransition(rlRoot, fadeTransition)

        with(dotProgress) {
            isVisible = inProgress

            state.dotColor?.let {
                this.dotColor = if (dotColor != -1) {
                    dotColor
                } else {
                    ContextCompat.getColor(context, it)
                }
            }

            state.bounceDotColor?.let {
                this.bounceDotColor = if (bounceDotColor != -1) {
                    bounceDotColor
                } else {
                    ContextCompat.getColor(context, it)
                }
            }
        }

        with(progressView) {
            state.progressBgColor?.let { progressBgColor ->
                colorBackground = ContextCompat.getColor(context, progressBgColor)
            }
            state.progressColor?.let { progressColor ->
                colorProgress = ContextCompat.getColor(context, progressColor)
            }
        }

        when {
            texColorStateList != null -> {
                tvTime.setTextColor(texColorStateList)
                tvLabel.setTextColor(texColorStateList)
            }
            textColor != -1 -> {
                tvTime.setTextColor(textColor)
                tvLabel.setTextColor(textColor)
            }
            else -> setTextColorFromState()
        }

        state.progressColor?.let { progressColor ->
            background = configureSelector(
                ContextCompat.getColor(context, progressColor),
                floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
            )

            llContainer.background = configureSelector(
                ContextCompat.getColor(context, progressColor),
                floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
            )
        }
    }

    private fun configureSelector(@ColorInt color: Int, radii: FloatArray) =
        StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), GradientDrawable().apply {
                setColor(color)
                cornerRadii = radii
            })
            addState(intArrayOf(), GradientDrawable().apply {
                setColor(Color.TRANSPARENT)
                cornerRadii = radii
            })
        }

    fun updateDotProgressVisibility(isVisible: Boolean) {
        TransitionManager.beginDelayedTransition(rlRoot, fadeTransition)

        inProgress = isVisible
        llContainer.isInvisible = isVisible

        with(dotProgress) {
            if (isVisible) startAnimation()
            else stopAnimation()
            this.isVisible = inProgress
        }
    }

    fun updateState(state: State) {
        clearCustomColors()
        this.state = state
        updateViewState()
    }

    fun clearTimer() {
        timer?.cancel()
    }

    private fun clearCustomColors() {
        textColorRes = -1
        dotColorRes = -1
        bounceDotColorRes = -1
    }

    private fun setTextColorFromState() {
        state.textColor?.let { textColor ->
            val color = ContextCompat.getColor(context, textColor)

            if (color < 0) {
                tvTime.setTextColor(ContextCompat.getColorStateList(context, textColor))
                tvLabel.setTextColor(ContextCompat.getColorStateList(context, textColor))
            } else {
                tvTime.setTextColor(color)
                tvLabel.setTextColor(color)
            }
        }
    }

    private fun TypedArray.setupColors() {
        when (state) {
            State.NEW -> {
                viewThemeManager.lightTheme.let {
                    it.textColorNew = getResourceId(
                        R.styleable.FTLTimerButton_ftlTimerButton_textColor,
                        it.textColorNew
                    )
                    it.dotColorNew = getResourceId(
                        R.styleable.FTLTimerButton_ftlTimerButton_dotColor,
                        it.dotColorNew
                    )
                    it.bounceDotColorNew = getResourceId(
                        R.styleable.FTLTimerButton_ftlTimerButton_bounceDotColor,
                        it.bounceDotColorNew
                    )
                }
            }
            State.IN_DELIVERY -> {
                viewThemeManager.lightTheme.let {
                    it.textColorInDelivery = getResourceId(
                        R.styleable.FTLTimerButton_ftlTimerButton_textColor,
                        it.textColorInDelivery
                    )
                    it.dotColorInDelivery = getResourceId(
                        R.styleable.FTLTimerButton_ftlTimerButton_dotColor,
                        it.dotColorInDelivery
                    )
                    it.bounceDotColorInDelivery =
                        getResourceId(
                            R.styleable.FTLTimerButton_ftlTimerButton_bounceDotColor,
                            it.bounceDotColorInDelivery
                        )
                }
            }
            else -> {
                viewThemeManager.lightTheme.let {
                    it.textColorReadyForDelivery =
                        getResourceId(
                            R.styleable.FTLTimerButton_ftlTimerButton_textColor,
                            it.textColorReadyForDelivery
                        )
                    it.dotColorReadyForDelivery =
                        getResourceId(
                            R.styleable.FTLTimerButton_ftlTimerButton_dotColor,
                            it.dotColorReadyForDelivery
                        )
                    it.bounceDotColorReadyForDelivery =
                        getResourceId(
                            R.styleable.FTLTimerButton_ftlTimerButton_bounceDotColor,
                            it.bounceDotColorReadyForDelivery
                        )
                }
            }
        }
    }

    internal class SavedState : BaseSavedState {
        var state = State.NEW
        var inProgress = false
        var estimateDuration: Long = 0L
        var remainedDuration: Long = 0L
        var estimateSuccessAt: String? = null

        @ColorRes
        var textColorRes = -1

        @ColorRes
        var dotColorRes = -1

        @ColorRes
        var bounceDotColorRes = -1

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            state = State.values()[parcel.readInt()]
            inProgress = parcel.readByte() == 1.toByte()
            estimateDuration = parcel.readLong()
            remainedDuration = parcel.readLong()
            estimateSuccessAt = parcel.readString()
            textColorRes = parcel.readInt()
            dotColorRes = parcel.readInt()
            bounceDotColorRes = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeInt(state.ordinal)
            parcel.writeByte(if (inProgress) 1 else 0)
            parcel.writeLong(estimateDuration)
            parcel.writeLong(remainedDuration)
            parcel.writeString(estimateSuccessAt)
            parcel.writeInt(textColorRes)
            parcel.writeInt(dotColorRes)
            parcel.writeInt(bounceDotColorRes)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}

data class FTLTimerButtonTheme(
    @ColorRes var textColorNew: Int,
    @ColorRes val progressColorNew: Int,
    @ColorRes val progressBgColorNew: Int,
    @ColorRes var dotColorNew: Int,
    @ColorRes var bounceDotColorNew: Int,

    @ColorRes var textColorReadyForDelivery: Int,
    @ColorRes val progressColorReadyForDelivery: Int,
    @ColorRes val progressBgColorReadyForDelivery: Int,
    @ColorRes var dotColorReadyForDelivery: Int,
    @ColorRes var bounceDotColorReadyForDelivery: Int,

    @ColorRes var textColorInDelivery: Int,
    @ColorRes val progressColorInDelivery: Int,
    @ColorRes val progressBgColorInDelivery: Int,
    @ColorRes var dotColorInDelivery: Int,
    @ColorRes var bounceDotColorInDelivery: Int
) : ViewTheme()
