package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var i = 0

        btn_simple.setOnClickListener {
            if (i++ % 2 == 0) {
                ftl_toolbar.showProgress()
            } else {
                ftl_toolbar.hideProgress()
            }

            ftl_btn_additional_small.isSelected = !ftl_btn_additional_small.isSelected
            ftl_btn_additional_medium.isSelected = !ftl_btn_additional_medium.isSelected
            ftl_btn_additional_large.isSelected = !ftl_btn_additional_large.isSelected
        }
    }
}
