package com.foodtechlab.ftlandroiduikitsample.playground

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.foodtechlab.ftlandroiduikit.common.NetworkConnectivityState
import com.foodtechlab.ftlandroiduikit.tab.FTLTabLayout
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikitsample.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_playground.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PlaygroundFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_playground, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tg_test2.updateTrackColorTheme(
            when(ThemeManager.theme) {
                ThemeManager.Theme.LIGHT -> R.color.TimerNegativeLight
                else -> R.color.TimerNegativeDark
            }
        )
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}