package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val rvAdapter by lazy { TimersRVAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvTimers.adapter = rvAdapter

        rvAdapter.update(
            listOf(
                TimerItem(1),
                TimerItem(2),
                TimerItem(3),
                TimerItem(4),
                TimerItem(5),
                TimerItem(6),
                TimerItem(7),
                TimerItem(8),
                TimerItem(9),
                TimerItem(10),
                TimerItem(11),
                TimerItem(12),
                TimerItem(13),
                TimerItem(14),
                TimerItem(15),
                TimerItem(16),
                TimerItem(17),
                TimerItem(18),
                TimerItem(19),
                TimerItem(20),
                TimerItem(21),
                TimerItem(22),
                TimerItem(23),
                TimerItem(24),
                TimerItem(25),
                TimerItem(26),
                TimerItem(27),
                TimerItem(28),
                TimerItem(29),
                TimerItem(30),
                TimerItem(31),
                TimerItem(32),
                TimerItem(33),
                TimerItem(34),
                TimerItem(35),
                TimerItem(36),
                TimerItem(37),
                TimerItem(38),
                TimerItem(39),
                TimerItem(40)
            )
        )
    }
}
