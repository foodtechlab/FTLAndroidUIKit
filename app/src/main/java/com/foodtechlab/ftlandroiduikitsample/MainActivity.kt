package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.bar.FTLBottomNavigationView
import com.foodtechlab.ftlandroiduikit.button.timer.State
import com.foodtechlab.ftlandroiduikitsample.main.TimerItem
import com.foodtechlab.ftlandroiduikitsample.main.TimersRVAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val rvAdapter by lazy { TimersRVAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvTimers.adapter = rvAdapter

        val list = arrayListOf<TimerItem>()

        for (i in 0..40) {
            list.add(TimerItem(i))
        }

        rvAdapter.update(list)
    }
}
