package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.foodtechlab.ftlandroiduikit.common.NetworkConnectivityState
import com.foodtechlab.ftlandroiduikit.tab.FTLTabLayout
import com.foodtechlab.ftlandroiduikitsample.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PlaygroundFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_playground, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tblContainer = view.findViewById<FTLTabLayout>(R.id.tbl_playground)
        val vpChecklists = view.findViewById<ViewPager2>(R.id.vp_playground)

        val vpAdapter = VPAdapter(childFragmentManager, lifecycle)
        vpChecklists.adapter = vpAdapter

        TabLayoutMediator(tblContainer.tabs, vpChecklists) { tab, position ->
            tab.text = vpAdapter.getPageTitle(position)
        }.attach()

       /* tblContainer.networkState = NetworkConnectivityState.DISCONNECTED
        launch {
            delay(5000)
            tblContainer.networkState = NetworkConnectivityState.CONNECTED
        }*/
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}