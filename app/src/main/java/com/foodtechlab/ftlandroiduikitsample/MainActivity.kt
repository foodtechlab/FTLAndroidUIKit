package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_simple.setOnClickListener {
            ftl_btn_additional_small.isSelected = !ftl_btn_additional_small.isSelected
            ftl_btn_additional_medium.isSelected = !ftl_btn_additional_medium.isSelected
            ftl_btn_additional_large.isSelected = !ftl_btn_additional_large.isSelected
        }
    }
}
