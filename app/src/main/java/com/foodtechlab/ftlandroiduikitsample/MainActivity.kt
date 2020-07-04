package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.bar.FTLBottomNavigationView
import com.foodtechlab.ftlandroiduikit.button.timer.State
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.showLogo()

        val zoneId = "Europe/Samara"

        with(ftlTimeView) {
            timeZoneId = zoneId
            deliveryTime = "23:12"
        }

        ftlButton.setOnClickListener {
            ftlButton.setProgressVisibility(!ftlButton.inProgress)
        }

        with(ftlTimerButton) {
            state = State.ORDER_MAKE
            timeZoneId = zoneId
            estimateDuration = 60 * 60 * 1000
            estimateSuccessAt = "2020-06-30T16:22:00.001"

            var i = 0
            setOnClickListener {
                ftlTimerButton.state = State.values()[i++]
                if (i == 3) i = 0
            }
        }


        var j = 0

        ftlImageButton.setOnClickListener {
            ftlTimeView.deliveryTime = if (j++ % 2 == 0) "233333333:12" else "23:12"
            ftlTimerButton.updateDotProgressVisibility(!ftlTimerButton.inProgress)
        }

        ftlBottomNavigationView.addMenuItems(
            listOf(
                FTLBottomNavigationView.MenuItem.HISTORY,
                FTLBottomNavigationView.MenuItem.MAPS,
                FTLBottomNavigationView.MenuItem.MORE,
                FTLBottomNavigationView.MenuItem.ORDERS
            )
        )
    }
}
