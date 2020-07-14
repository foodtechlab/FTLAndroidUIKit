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

        with(ftlTimerView) {
            timeZoneId = zoneId
            estimateDuration = 60 * 60 * 1000
            estimateSuccessAt = "2020-07-14T09:25:00.001"
        }

        with(ftlTimeView) {
            timeZoneId = zoneId
            estimateDuration = 20 * 60 * 1000
            deliveryTime = "28:30"
        }

        with(ftlTimerButton) {
            timeZoneId = zoneId
            estimateDuration = 60 * 60 * 1000
            estimateSuccessAt = "2231-12-17T23:59:00.001"
            autoAnimateProgress = false

            var i = 0
            setOnClickListener {
                ftlTimerButton.updateState(State.values()[i++])
                if (i == 3) i = 0
            }
        }

        var i = 0
        ftlImageButton.setOnClickListener {
            if (i++ % 2 == 0) {
                toolbar.showProgress()
            } else {
                toolbar.hideProgress()
            }
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
