package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dtsv_time.deliveryTimeMillis = 3_600_000L
        dtlv_time.deliveryTimeMillis = 3_600_000L

        var mode = DeliveryMode.USUAL

        btn_simple.setOnClickListener {
            mode = if (mode == DeliveryMode.USUAL) {
                DeliveryMode.URGENT
            } else {
                DeliveryMode.USUAL
            }
            dtsv_time.deliveryMode = mode
            dtlv_time.deliveryMode = mode
        }
    }
}
