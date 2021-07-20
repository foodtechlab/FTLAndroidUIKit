package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.foodtechlab.ftlandroiduikit.bar.toolbar.FTLToolbar
import com.foodtechlab.ftlandroiduikit.tab.FTLTabLayout
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikitsample.R
import com.google.android.material.tabs.TabLayoutMediator

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
        val tblContainer = view.findViewById<FTLTabLayout>(R.id.tbl_playground)
        val vpChecklists = view.findViewById<ViewPager2>(R.id.vp_playground)
        val toolbar = view.findViewById<FTLToolbar>(R.id.toolbar)

        val vpAdapter = VPAdapter(childFragmentManager, lifecycle)
        vpChecklists.adapter = vpAdapter

        TabLayoutMediator(tblContainer.tabs, vpChecklists) { tab, position ->
            tab.text = vpAdapter.getPageTitle(position)
        }.attach()

        tblContainer.changeCaseForItems(false)

        toolbar.showCircleProgressIndicator(ImageType.BONUSES)
        toolbar.maxProgress = 15
        toolbar.currentProgress = 10
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}