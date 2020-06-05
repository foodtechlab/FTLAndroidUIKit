package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryStatus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.showLogo()

        ftl_delivery_time.deliveryTimeMillis = 1590582735098

        var i = 0

        btn_simple.setOnClickListener {
            ftl_delivery_time.deliveryStatus = if (i++ % 2 == 0) {
                DeliveryStatus.CANCELED
            } else {
                DeliveryStatus.DELIVERED_LATE
            }
        }
    }
}
