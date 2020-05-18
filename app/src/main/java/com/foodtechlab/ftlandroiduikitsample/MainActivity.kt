package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
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

        ftl_et_default_1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // TODO("Not yet implemented")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Toast.makeText(this@MainActivity, s.toString(), Toast.LENGTH_SHORT).show()
                ftl_et_default_1.removeTextChangedListener(this)
            }
        })
    }

    override fun onClick(v: View) {
        TopSnackbar.make(
            ll_root,
            "Пожалуйста, измените номер телефона и повторите попытку",
            Snackbar.LENGTH_LONG
        )?.show()

        when (v.id) {
            R.id.btn_additional_click -> ftl_et_default_1.isActiveStateEnabled = false
            R.id.btn_secondary_click -> ftl_et_default_2.setErrorEnabled(true)
            R.id.btn_primary_click -> ftl_et_default_1.setErrorEnabled(true)
            else -> v.isSelected = !v.isSelected
        }
    }
}
