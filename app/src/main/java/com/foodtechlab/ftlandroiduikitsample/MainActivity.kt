package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.button.timer.State
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.showLogo()

        with(ftlTimerButton) {
            state = State.ORDER_MAKE
            estimateDuration = 60 * 60 * 1000
            estimateSuccessAt = "2020-06-17T14:30:00.001"
        }

        ftlButton.setOnClickListener {
            ftlButton.setProgressVisibility(!ftlButton.inProgress)
        }

        var i = 0
        ftlTimerButton.setOnClickListener {
            ftlTimerButton.state = State.values()[i++]
            if (i == 3) i = 0
        }

        ftlImageButton.setOnClickListener {
            ftlTimerButton.updateDotProgressVisibility(!ftlTimerButton.inProgress)
        }
    }
}
