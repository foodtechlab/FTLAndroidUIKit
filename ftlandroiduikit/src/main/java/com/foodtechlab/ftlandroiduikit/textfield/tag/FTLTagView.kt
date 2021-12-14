package com.foodtechlab.ftlandroiduikit.textfield.tag

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.updatePadding
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.*
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class FTLTagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLTagViewTheme> = FTLTagViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var shouldVisibleBackground = true
        set(value) {
            field = value
            updateBackgroundColorTheme(tagBackgroundColor)
            background = shapeDrawable
        }

    var shouldVisibleBorder = false
        set(value) {
            field = value
            updateBorderColorTheme(tagBorderColor)
            background = shapeDrawable
        }

    var tag: String? = null
        set(value) {
            field = value
            text = field
        }

    @ColorRes
    private var tagBackgroundColor = -1

    @ColorRes
    private var tagBorderColor = -1

    @ColorRes
    private var tagTextColor = -1

    private val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, context.dpToPx(CORNERS))
        .build()

    private val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLTagView) {
            tag = getString(R.styleable.FTLTagView_android_text)
            shouldVisibleBackground =
                getBoolean(R.styleable.FTLTagView_shouldVisibleBackground, true)
            shouldVisibleBorder = getBoolean(R.styleable.FTLTagView_shouldVisibleBorder, false)
        }
        ellipsize = TextUtils.TruncateAt.END
        isSingleLine = true
        setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE)
        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)

        val verticalPaddings = context.dpToPxInt(VERTICAL_PADDING)
        val horizontalPaddings = context.dpToPxInt(HORIZONTAL_PADDING)
        updatePadding(
            horizontalPaddings,
            verticalPaddings,
            horizontalPaddings,
            verticalPaddings
        )

        updateTextColorTheme(tagTextColor)
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

    fun onThemeChanged() {
        updateTextColorTheme(tagTextColor)
        updateBackgroundColorTheme(tagBackgroundColor)
        updateBorderColorTheme(tagBorderColor)
        background = shapeDrawable
    }

    fun updateTextColorTheme(@ColorRes colorRes: Int) {
        tagTextColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                setTextColor(
                    ContextCompat.getColorStateList(
                        context,
                        if (tagBorderColor != -1) tagTextColor
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
        tagBackgroundColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                if (shouldVisibleBackground) {
                    shapeDrawable.fillColor = ContextCompat.getColorStateList(
                        context,
                        if (tagBackgroundColor != -1) tagBackgroundColor
                        else theme.backgroundColor
                    )
                } else {
                    shapeDrawable.fillColor = ContextCompat.getColorStateList(
                        context,
                        android.R.color.transparent
                    )
                    try {
                        this.cancel()
                    } catch (e: Exception) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }
        }
    }

    fun updateBorderColorTheme(@ColorRes colorRes: Int) {
        tagBorderColor = colorRes
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                if (shouldVisibleBorder) {
                    shapeDrawable.setStroke(
                        context.dpToPx(STROKE_WIDTH),
                        ContextCompat.getColor(
                            context,
                            if (tagBorderColor != -1) tagBorderColor
                            else theme.borderColor
                        )
                    )
                } else {
                    shapeDrawable.strokeWidth = 0F
                }
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    companion object {
        private const val TAG = "FTLTagView"
        private const val TEXT_SIZE = 14F
        private const val HORIZONTAL_PADDING = 8F
        private const val VERTICAL_PADDING = 6F
        private const val CORNERS = 16F
        private const val STROKE_WIDTH = 1F
    }
}

data class FTLTagViewTheme(
    @ColorRes val textColor: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val borderColor: Int
) : ViewTheme()
