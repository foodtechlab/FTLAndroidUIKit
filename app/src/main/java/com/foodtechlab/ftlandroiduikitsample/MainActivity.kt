package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_primary_click.setOnClickListener(this)
        btn_secondary_click.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show()
        v.isSelected = !v.isSelected
    }
}
