package com.foodtechlab.ftlandroiduikit.sheet

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.button.FTLButton
import com.foodtechlab.ftlandroiduikit.button.image.FTLImageButton
import com.foodtechlab.ftlandroiduikit.button.image.ImageButtonType
import com.foodtechlab.ftlandroiduikit.snackbar.top.FTLSnackbar
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class FTLAudioRecorderBottomSheet() : BottomSheetDialogFragment(), View.OnClickListener,
    MediaPlayer.OnCompletionListener {
    var behavior: BottomSheetBehavior<*>? = null

    private val viewThemeManager: ViewThemeManager<FTLAudioRecorderBottomSheetTheme> =
        FTLAudioRecorderBottomSheetThemeManager()
    private val filePath by lazy { arguments?.getString(KEY_VOICE_FILE_PATH) }
    private val textActionButton by lazy { arguments?.getString(KEY_TEXT_ACTION_BUTTON) }
    private var onClickListener: View.OnClickListener? = null
    private lateinit var btnRecControl: FTLImageButton
    private lateinit var btnMediaControl: FTLImageButton
    private lateinit var btnTrash: FTLImageButton
    private lateinit var btnAction: FTLButton
    private lateinit var cmrTimer: Chronometer
    private lateinit var vTop: View
    private lateinit var clContainer: ConstraintLayout
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    constructor(textActionButton: String, filename: String) : this() {
        arguments = Bundle().apply {
            putString(KEY_TEXT_ACTION_BUTTON, textActionButton)
            putString(KEY_VOICE_FILE_PATH, filename)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickListener =
            context as? View.OnClickListener ?: parentFragment as? View.OnClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FTLBottomSheetDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            behavior = BottomSheetBehavior.from(bottomSheet)
            behavior?.skipCollapsed = true
            behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.layout_ftl_audio_recorder_bottom_sheet,
            container,
            false
        )

        btnRecControl = view.findViewById(R.id.btn_ftl_audio_recorder_bottom_sheet_rec)
        btnMediaControl = view.findViewById(R.id.btn_ftl_audio_recorder_bottom_sheet_media_control)
        btnTrash = view.findViewById(R.id.btn_ftl_audio_recorder_bottom_sheet_delete)
        btnAction = view.findViewById(R.id.btn_ftl_audio_recorder_bottom_sheet_action)
        vTop = view.findViewById(R.id.v_ftl_audio_recorder_bottom_sheet_top)
        clContainer = view.findViewById(R.id.cl_ftl_audio_recorder_bottom_sheet_container)
        cmrTimer = view.findViewById(R.id.cmr_ftl_audio_recorder_bottom_sheet_timer)

        onThemeChanged()

        btnRecControl.setOnClickListener(this)
        btnMediaControl.setOnClickListener(this)
        btnTrash.setOnClickListener(this)
        btnAction.setOnClickListener(this)
        btnAction.text = textActionButton
        updateUI()
        return view
    }

    override fun onDestroyView() {
        onClickListener = null
        stopRecording()
        preparePlayingIfNeed()
        super.onDestroyView()
    }

    override fun onCancel(dialog: DialogInterface) {
        stopRecording()
        preparePlayingIfNeed()
        deleteFileFromStorage()
        super.onCancel(dialog)
    }

    override fun onClick(v: View) {
        when (v.id) {
            btnRecControl.id -> {
                btnRecControl.type = if (btnRecControl.type == ImageButtonType.RECORD_LARGE)
                    ImageButtonType.STOP_LARGE
                else
                    ImageButtonType.RECORD_LARGE
                btnMediaControl.isSelected = btnRecControl.type != ImageButtonType.RECORD_LARGE
                btnTrash.isSelected = btnRecControl.type != ImageButtonType.RECORD_LARGE
                btnAction.isSelected = btnRecControl.type != ImageButtonType.RECORD_LARGE
                cmrTimer.isSelected = false

                if (btnRecControl.type == ImageButtonType.RECORD_LARGE) {
                    stopRecording()
                } else {
                    preparePlayingIfNeed()
                    startRecording()
                }
            }
            btnMediaControl.id -> {
                if (btnMediaControl.type == ImageButtonType.PLAY_LARGE) {
                    btnMediaControl.type = ImageButtonType.PAUSE_LARGE
                    startPlaying()
                } else {
                    btnMediaControl.type = ImageButtonType.PLAY_LARGE
                    pausePlaying()
                }
            }
            btnTrash.id -> {
                preparePlayingIfNeed()
                deleteFileFromStorage()
            }
        }
        onClickListener?.onClick(v)
    }

    fun onThemeChanged() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewThemeManager.mapToViewData().flowWithLifecycle(lifecycle).collect { theme ->
                context?.let { ctx ->
                    clContainer.background.changeColor(
                        ContextCompat.getColor(
                            ctx,
                            theme.bgColor
                        )
                    )
                    vTop.background.changeColor(
                        ContextCompat.getColor(
                            ctx,
                            theme.bgColor
                        )
                    )
                    cmrTimer.setTextColor(
                        ContextCompat.getColorStateList(
                            ctx,
                            theme.timerColor
                        )
                    )
                }
            }
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        cmrTimer.stop()
        mediaPlayer = null
        btnMediaControl.type = ImageButtonType.PLAY_LARGE
    }

    fun showMessage(message: String) {
        FTLSnackbar.make(clContainer, message, Snackbar.LENGTH_LONG)?.show()
    }

    private fun startRecording() {
        try {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFile(filePath)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

                prepare()
                start()
            }

            cmrTimer.base = SystemClock.elapsedRealtime()
            cmrTimer.start()
        } catch (e: Exception) {
            Log.e("AudioRecordBottomSheet", e.message ?: "")
        }
    }

    private fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null

            cmrTimer.stop()
            btnRecControl.isSelected = true
        } catch (e: Exception) {
            Log.e("AudioRecordBottomSheet", e.message ?: "")
        }
    }

    private fun preparePlayingIfNeed() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                mediaPlayer = null
                cmrTimer.stop()
                cmrTimer.base = SystemClock.elapsedRealtime()
                btnMediaControl.type = ImageButtonType.PLAY_LARGE
            }
        } catch (e: Exception) {
            Log.e("AudioRecordBottomSheet", e.message ?: "")
        }
    }

    private fun startPlaying() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(filePath)
                    setOnCompletionListener(this@FTLAudioRecorderBottomSheet)
                    prepare()
                }
            }
            val currentTime = mediaPlayer?.currentPosition ?: 0
            cmrTimer.base = SystemClock.elapsedRealtime() - currentTime
            mediaPlayer?.start()
            cmrTimer.start()
        } catch (e: Exception) {
            Log.e("AudioRecordBottomSheet", e.message ?: "")
        }
    }

    private fun pausePlaying() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                cmrTimer.stop()
            }
        } catch (e: Exception) {
            Log.e("AudioRecordBottomSheet", e.message ?: "")
        }
    }

    private fun deleteFileFromStorage() {
        try {
            filePath?.let {
                val file = File(it)
                if (file.exists()) file.delete()
            }

            cmrTimer.base = SystemClock.elapsedRealtime()
            updateUI()
        } catch (e: Exception) {
            Log.e("AudioRecordBottomSheet", e.message ?: "")
        }
    }

    private fun updateUI() {
        btnMediaControl.isSelected = true
        btnTrash.isSelected = true
        btnAction.isSelected = true
        cmrTimer.isSelected = true
        btnRecControl.isSelected = false
    }

    companion object {
        const val TAG = "FTLAudioRecorderBottomSheet"

        private const val KEY_TEXT_ACTION_BUTTON = "KEY_TEXT_ACTION_BUTTON"
        private const val KEY_VOICE_FILE_PATH = "KEY_VOICE_FILE_PATH"
    }
}

data class FTLAudioRecorderBottomSheetTheme(
    @ColorRes val timerColor: Int,
    @ColorRes val bgColor: Int
) : ViewTheme()