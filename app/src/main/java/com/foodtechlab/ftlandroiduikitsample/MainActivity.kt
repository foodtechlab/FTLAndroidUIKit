package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.snackbar.top.FTLSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.showLogo()

        time.date = "2020-06-11T16:37:00.001"

        timer.estimateDuration = 60 * 60 * 1000
        timer.estimateSuccessAt = "2020-06-11T16:37:00.001"

        btn_simple.setOnClickListener {
            FTLSnackbar.make(root, "This is just simple text", Snackbar.LENGTH_SHORT)?.show()
        }
    }
}
