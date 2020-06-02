package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_simple.setOnClickListener {
            Picasso.get()
                .load(R.drawable.image_test)
                .into(ftlpiv_pic)
        }
    }
}
