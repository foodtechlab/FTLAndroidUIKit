package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.circular.FTLCircularCheckBox
import com.foodtechlab.ftlandroiduikit.textfield.bannered.FTLBanneredTextViewThemeManager
import com.foodtechlab.ftlandroiduikit.textfield.helper.OnFTLTextFieldCheckedChangeListener
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class FTLBanneredTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLBanneredTextViewTheme> =
        FTLBanneredTextViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var isVisibleCheckbox: Boolean = false
        get() = cbCheckedSlot.isVisible
        private set(value) {
            field = value
            cbCheckedSlot.isVisible = field
        }

    var isCheckedState: Boolean = false
        get() = cbCheckedSlot.isChecked
        private set(value) {
            field = value
            cbCheckedSlot.isChecked = field
        }

    var isBoldStyle: Boolean = false
        set(value) {
            field = value
            tvTextSlot.typeface = if (field) ResourcesCompat.getFont(
                context,
                R.font.roboto_bold
            ) else ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

    var textForSlot: String = ""
        set(value) {
            field = value
            tvTextSlot.text = field
        }

    var onCheckedChangeListener: OnFTLTextFieldCheckedChangeListener? = null

    @ColorRes
    private var textBackgroundColor = -1

    @ColorRes
    private var textColor = -1

    private val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, context.dpToPx(CORNERS))
        .build()

    private val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

    private val tvTextSlot: AppCompatTextView
    private val cbCheckedSlot: FTLCircularCheckBox
    private val glVerticalStop: Guideline

    init {
        View.inflate(context, R.layout.layout_ftl_bunnered_text_view, this)

        cbCheckedSlot = findViewById(R.id.cb_ftl_bannered_text_view)
        glVerticalStop = findViewById(R.id.gl_ftl_bannered_text_view)
        tvTextSlot = findViewById(R.id.tv_ftl_bannered_text_view)

        context.withStyledAttributes(attrs, R.styleable.FTLBanneredTextView) {
            textForSlot = getString(R.styleable.FTLBanneredTextView_textForSlot) ?: ""
            isBoldStyle = getBoolean(R.styleable.FTLBanneredTextView_isBoldStyle, false)
            isCheckedState = getBoolean(R.styleable.FTLBanneredTextView_isCheckedState, false)
        }

        cbCheckedSlot.setOnCheckedChangeListener { view, isChecked ->
            onCheckedChangeListener?.onFTLCheckedChanged(view, isChecked)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged()
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    private fun onThemeChanged() {
        updateTextColorTheme(textColor)
        updateBackgroundColorTheme(textBackgroundColor)
        tvTextSlot.background = shapeDrawable
    }

    fun updateTextColorTheme(@ColorRes colorRes: Int) {
        textColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                tvTextSlot.setTextColor(
                    ContextCompat.getColorStateList(
                        context,
                        if (textColor != -1) textColor
                        else theme.textColor
                    )
                )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    fun updateBackgroundColorTheme(@ColorRes colorRes: Int) {
        textBackgroundColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                shapeDrawable.fillColor = ContextCompat.getColorStateList(
                    context,
                    if (textBackgroundColor != -1) textBackgroundColor
                    else theme.backgroundColor
                )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    fun updateSolidCheckedColorTheme(@ColorRes colorRes: Int) {
        cbCheckedSlot.colorSolidChecked = ContextCompat.getColor(context, colorRes)
    }

    fun updateStrokeUncheckedColorTheme(@ColorRes colorRes: Int) {
        cbCheckedSlot.colorStrokeUnchecked = ContextCompat.getColor(context, colorRes)
    }

    fun setVisibilityOfCheckbox(shouldVisible: Boolean, showAnimation: Boolean) {
        // Важный момент! В случае если чекбокс нужно отобразить, необходимо это сделать уже
        // после перепривязки. В противоположном случае, необходимо сначала убрать чекбокс
        // и только после этого делать перепривязку
        val set = ConstraintSet()
        set.clone(this)

        if (shouldVisible) {
            set.connect(tvTextSlot.id, ConstraintSet.START, glVerticalStop.id, ConstraintSet.END)
        } else {
            isVisibleCheckbox = false
            set.connect(tvTextSlot.id, ConstraintSet.START, this.id, ConstraintSet.START)
        }

        if (showAnimation) {
            TransitionManager.beginDelayedTransition(this)
        }

        set.applyTo(this)

        if (shouldVisible) {
            isVisibleCheckbox = true
        }
    }

    companion object {
        private const val CORNERS = 8F
        private const val TAG = "FTLBanneredTextView"
    }
}

data class FTLBanneredTextViewTheme(
    @ColorRes val textColor: Int,
    @ColorRes val backgroundColor: Int
) : ViewTheme()
