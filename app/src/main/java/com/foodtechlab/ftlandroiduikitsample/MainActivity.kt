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

        ftl_timer_button.estimateDuration = 60 * 60 * 1000
        ftl_timer_button.estimateSuccessAt = "2020-06-17T14:30:00.001"

        var i = 0
        ftl_timer_button.setOnClickListener {
            ftl_timer_button.state = State.values()[i++]
            if (i == 3) i = 0
        }
    }
}
