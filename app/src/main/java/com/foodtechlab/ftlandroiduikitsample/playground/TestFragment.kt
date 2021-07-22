package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.utils.argument
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment() : Fragment() {
    private var pos: Int by argument(KEY_POSITION)

    constructor(position: Int) : this() {
        arguments = Bundle().apply {
            putInt(KEY_POSITION, position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_progress_section.currentProgress = 85
        tv_progress_section.imageTypeForProgress = ImageType.CROSS
        val gallery = ContextCompat.getDrawable(
            tv_text_with_icons.context,
            R.drawable.ic_media_content_gallery
        ).apply {
            this?.mutate()?.changeColor(
                ContextCompat.getColor(tv_progress_section.context, R.color.IconGreyLight)
            )
        }
        val message = ContextCompat.getDrawable(
            tv_text_with_icons.context,
            R.drawable.ic_media_content_message
        ).apply {
            this?.mutate()?.changeColor(
                ContextCompat.getColor(tv_progress_section.context, R.color.IconGreyLight)
            )
        }
        val camera = ContextCompat.getDrawable(
            tv_text_with_icons.context,
            R.drawable.ic_media_content_camera
        ).apply {
            this?.mutate()?.changeColor(
                ContextCompat.getColor(tv_progress_section.context, R.color.IconBlueLight)
            )
        }
        val video =
            ContextCompat.getDrawable(tv_text_with_icons.context, R.drawable.ic_media_content_video)
                .apply {
                    this?.mutate()?.changeColor(
                        ContextCompat.getColor(tv_progress_section.context, R.color.IconBlueLight)
                    )
                }
        val voice =
            ContextCompat.getDrawable(tv_text_with_icons.context, R.drawable.ic_media_content_voice)
                .apply {
                    this?.mutate()?.changeColor(
                        ContextCompat.getColor(tv_progress_section.context, R.color.IconDangerLight)
                    )
                }
        with(tv_text_with_icons) {
            addIconsForBottomSlot(listOf(gallery, message, camera, video, voice))
            imageType = ImageType.CROSS
            updateImageColors(R.color.IconPrimaryLight, R.color.IconPrimaryDark)
            updateImageBackgroundColors(R.color.IconDangerLight, R.color.IconDangerDark)
        }

        btn_mixed_full.currentProgress = 90
        btn_mixed1.currentProgress = 90
        btn_mixed1.textForSlot = "Hello world"
        btn_mixed2.currentProgress = 10
        btn_mixed2.setOnClickListener {
            Log.i("MY TAG", "click btn_mixed2")
        }
        btn_mixed2.updateProgressTrackColor(
            when (ThemeManager.theme) {
                ThemeManager.Theme.LIGHT -> R.color.TimerNegativeLight
                else -> R.color.TimerNegativeDark
            }
        )
        btn_mixed4.updateImageBackgroundColorTheme(
            when (ThemeManager.theme) {
                ThemeManager.Theme.LIGHT -> R.color.IconBackgroundOrangeLight
                else -> R.color.IconBackgroundOrangeDark
            }
        )
    }

    companion object {
        const val KEY_POSITION = "KEY_POSITION"
        const val TAG = "TestFragment"
    }
}