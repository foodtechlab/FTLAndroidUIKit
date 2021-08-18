package com.foodtechlab.ftlandroiduikitsample.playground

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.bar.toolbar.FTLToolbar
import com.foodtechlab.ftlandroiduikit.sheet.FTLAudioRecorderBottomSheet
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.fragment_playground.*
import java.io.File

class PlaygroundFragment : Fragment(), View.OnClickListener {
    private var filePath: String? = null
    private var bottomSheetDialog: FTLAudioRecorderBottomSheet? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_playground, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<FTLToolbar>(R.id.toolbar)
        with(toolbar) {
            showCircleProgressIndicator(ImageType.BONUSES)
            maxProgress = 15
            currentProgress = 10
        }
        btnStartRecord.setOnClickListener {
            getPermissionToRecordAudio()
        }
        var state = false
        pvMusicPlayer.setOnLongClickListener {
            state = !state
            pvMusicPlayer.setVisibilityOfCheckbox(
                state,
                true
            )
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (
                grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                showAudioRecorderBottomSheet()
            } else {
                Log.i(TAG, "Permissions are denied")
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_ftl_audio_recorder_bottom_sheet_delete -> {
                bottomSheetDialog?.showMessage("Запись удалена. Чтобы начать сначала нажмите на красную кнопку")
            }
            R.id.btn_ftl_audio_recorder_bottom_sheet_action -> {
                bottomSheetDialog?.dismiss()
                bottomSheetDialog = null
                pvMusicPlayer.dataSource = filePath
            }
        }
    }

    private fun showAudioRecorderBottomSheet() {
        val root = android.os.Environment.getExternalStorageDirectory()
        val file = File(root.absolutePath + "/FTLUIKit/voice/")
        if (!file.exists()) {
            file.mkdirs()
        }

        filePath = root.absolutePath + "/FTLUIKit/voice/voice" + (System.currentTimeMillis()
            .toString() + ".mp3")
        filePath?.let {
            bottomSheetDialog = FTLAudioRecorderBottomSheet(
                "Прикрепить",
                it
            )
            bottomSheetDialog?.show(childFragmentManager, FTLAudioRecorderBottomSheet.TAG)
        }
    }

    private fun getPermissionToRecordAudio() {
        context?.let {
            val isRecordPermissionDenied = ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED

            val isReadExternalPermissionDenied = ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED

            val isWriteExternalPermissionDenied = ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED

            if (isRecordPermissionDenied || isReadExternalPermissionDenied || isWriteExternalPermissionDenied) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), RECORD_AUDIO_REQUEST_CODE
                )
            } else {
                showAudioRecorderBottomSheet()
            }
        }
    }

    companion object {
        const val TAG = "PlaygroundFragment"

        private const val RECORD_AUDIO_REQUEST_CODE = 101
    }
}