package com.foodtechlab.ftlandroiduikitsample.showroom

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.bar.toolbar.OnToolbarClickListener
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.main.MainActivity
import com.foodtechlab.ftlandroiduikitsample.utils.Utils.getColorForTheme
import kotlinx.android.synthetic.main.fragment_showroom.*

class ShowroomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_showroom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = ShowroomPagerAdapter(childFragmentManager)
        vpShowroomContent.adapter = fragmentAdapter
        with(tblShowroomCategories) {
            setupWithViewPager(vpShowroomContent)
            tabTextColors = ColorStateList.valueOf(
                getColorForTheme(
                    R.color.TextPrimaryDark,
                    R.color.TextPrimaryLight
                )
            )
        }
        tbShowroom.onToolbarClickListener = object : OnToolbarClickListener {
            override fun onToolbarClick(v: View) {
                when (v.id) {
                    R.id.ib_ftl_toolbar_start -> (activity as MainActivity).onBackPressed()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tbShowroom.onToolbarClickListener = null
    }

    companion object {
        const val TAG = "ShowroomFragment"
    }
}