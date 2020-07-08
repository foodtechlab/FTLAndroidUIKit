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

        toolbar.showConnectionIndicator()

        val zoneId = "Europe/Samara"

        with(ftlTimeView) {
            timeZoneId = zoneId
            deliveryTime = "23:12"
        }

        with(ftlTimerButton) {
            timeZoneId = zoneId
            estimateDuration = 20 * 60 * 1000
            estimateSuccessAt = "2020-07-06T15:50:00.001"
            autoAnimateProgress = false

            var i = 0
            setOnClickListener {
                ftlTimerButton.updateState(State.values()[i++])
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
