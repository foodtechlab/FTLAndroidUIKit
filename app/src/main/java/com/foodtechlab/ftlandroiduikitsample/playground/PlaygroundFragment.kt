package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.button.ButtonType
import com.foodtechlab.ftlandroiduikit.button.FTLButton
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PlaygroundFragment : Fragment(), View.OnClickListener, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    lateinit var bSettings: FTLButton
    lateinit var bNonSettings: FTLButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_playground, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bSettings = view.findViewById(R.id.btn_settings)
        bNonSettings = view.findViewById(R.id.btn_non_settings)
        with(bSettings) {
            setButtonType(ButtonType.ADDITIONAL)
            isWrapContentOnHeight = true
            setOnClickListener(this@PlaygroundFragment)
        }
        bNonSettings.setOnClickListener(this@PlaygroundFragment)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_settings -> {
                launch {
                    bSettings.setProgressVisibility(true)
                    delay(5000)
                    bSettings.setProgressVisibility(false)
                }
            }
            R.id.btn_non_settings -> {
                launch {
                    bNonSettings.setProgressVisibility(true)
                    delay(5000)
                    bNonSettings.setProgressVisibility(false)
                }
            }
        }
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}