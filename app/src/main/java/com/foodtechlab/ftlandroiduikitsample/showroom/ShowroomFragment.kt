package com.foodtechlab.ftlandroiduikitsample.showroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.fragment_showroom.*

class ShowroomFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_showroom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = ShowroomPagerAdapter(childFragmentManager)
        vpShowroomContent.adapter = fragmentAdapter
        vpShowroomContent.offscreenPageLimit = 6
        tblShowroomCategories.setupWithViewPager(vpShowroomContent)
    }

    companion object {
        const val TAG = "ShowroomFragment"
    }
}