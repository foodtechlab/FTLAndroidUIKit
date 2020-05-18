package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.snackbar.top.TopSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_primary_click.setOnClickListener(this)
        btn_secondary_click.setOnClickListener(this)
        btn_additional_click.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        TopSnackbar.make(
            ll_root,
            "Пожалуйста, измените номер телефона и повторите попытку",
            Snackbar.LENGTH_LONG
        )?.show()

        when (v.id) {
            R.id.btn_primary_click -> cet_code.isErrorEnabled = true
            R.id.btn_secondary_click -> cet_code.isErrorEnabled = false
        }
    }
}
