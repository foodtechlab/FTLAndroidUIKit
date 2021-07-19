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
        /*tg_test.tex = "VLG.0.1VLG.0.1"*/
       // tg_test.mText = "VLG.0.1VLG.0.1"
        tg_test.tagBorderColor =  ContextCompat.getColorStateList(tg_test.context, android.R.color.transparent)
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}