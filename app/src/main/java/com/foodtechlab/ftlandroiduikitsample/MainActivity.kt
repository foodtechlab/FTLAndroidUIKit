package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.bar.FTLBottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(toolbar) {
            showConnectionIndicator()
            onIndicatorClickListener = View.OnClickListener {
                Toast.makeText(context, "dfdfd", Toast.LENGTH_SHORT).show()
            }
        }

        val zoneId = "Europe/Samara"

        with(ftlTimerView) {
            timeZoneId = zoneId
            estimateDuration = 60 * 60 * 1000
            estimateSuccessAt = "2020-07-14T21:40:00.001"
        }

        with(ftlTimeView) {
            timeZoneId = zoneId
            estimateDuration = 20 * 60 * 1000
            deliveryTime = "28:30"
        }

        rgContent.check(R.id.rb2)
        rb2.colorStyle = ContextCompat.getColor(this, R.color.AdditionalOrange)
        with(mtvPayment) {
            colorBottomEndSlot = ContextCompat.getColor(this@MainActivity, R.color.AdditionalDarkBlue)
            colorBottomStartSlot = ContextCompat.getColor(this@MainActivity, R.color.AdditionalPink)
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
