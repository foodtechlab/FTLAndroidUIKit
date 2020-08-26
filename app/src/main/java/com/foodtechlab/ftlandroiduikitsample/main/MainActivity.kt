package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryStatus
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.getMillis
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    private val parentJob = Job()

    private var timerJob: Job? = null

    private var tickerChannel: ReceiveChannel<Unit>? = null

    private val rvAdapter by lazy { TimersRVAdapter() }

    private val list = arrayListOf<TimerItem>()

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(ftlToolbar) {
            title = "№ Title title title title title title title"
            showTime("Europe/Samara", 1598372100001, DeliveryStatus.URGENT)
            showLogo()
        }

        rvTimers.adapter = rvAdapter

        for (i in 0..40) {
            list.add(
                TimerItem(
                    i,
                    getMillis(
                        "2020-08-26T16:00:00.001",
                        "Europe/Samara"
                    ) - System.currentTimeMillis()
                )
            )
        }

        rvAdapter.update(list)

        initTimer()
    }

    override fun onDestroy() {
        parentJob.cancel()
        tickerChannel?.cancel()
        super.onDestroy()
    }

    @ObsoleteCoroutinesApi
    private fun initTimer() {
        tickerChannel?.cancel()
        tickerChannel =
            ticker(delayMillis = 1000, initialDelayMillis = 0, context = coroutineContext)

        timerJob?.let {
            if (!it.isCancelled) {
                it.cancel()
            }
        }

        tickerChannel?.let { channel ->
            timerJob = launch {
                for (event in channel) {
                    for (i in 0 until list.size) {
                        list[i] = list[i].copy(
                            remainedDuration = getMillis(
                                "2020-08-26T16:00:00.001",
                                "Europe/Samara"
                            ) - System.currentTimeMillis()
                        )
                    }
                    rvAdapter.update(list)
                }
            }
        }
    }
}
