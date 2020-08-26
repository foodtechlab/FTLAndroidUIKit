package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryStatus
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(ftlToolbar) {
            title = "№ Title title title title title title title"
            showTime("Europe/Samara", 1598372100001, DeliveryStatus.URGENT)
            showLogo()
        }
    }
}
