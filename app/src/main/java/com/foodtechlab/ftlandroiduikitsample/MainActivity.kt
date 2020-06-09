package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.showLogo()

        ftl_delivery_time.deliveryTimeMillis = 1590582735098

        ftl_delivery_timer.setRemainedTime(10_000L)

        btn_simple.setOnClickListener {}
    }
}
