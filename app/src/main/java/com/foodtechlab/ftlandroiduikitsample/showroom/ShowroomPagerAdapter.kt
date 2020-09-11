package com.foodtechlab.ftlandroiduikitsample.showroom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.components.ComponentsFragment
import com.foodtechlab.ftlandroiduikitsample.utils.Utils.getString

class ShowroomPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val categories = arrayListOf(
        getString(R.string.common_buttons),
        getString(R.string.common_text_fields),
        getString(R.string.common_edit_fields),
        getString(R.string.common_table_fields),
        getString(R.string.common_cards),
        getString(R.string.common_times)
    )

    override fun getItem(position: Int): Fragment = ComponentsFragment.newInstance(position)

    override fun getCount(): Int = categories.size

    override fun getPageTitle(position: Int): CharSequence = categories[position]

}