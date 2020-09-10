package com.foodtechlab.ftlandroiduikitsample.showroom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.showroom.components.ItemFragment
import com.foodtechlab.ftlandroiduikitsample.utils.Utils.getString

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val categories = arrayListOf(
        getString(R.string.common_buttons),
        getString(R.string.common_text_fields),
        getString(R.string.common_edit_fields),
        getString(R.string.common_table_fields),
        getString(R.string.common_cards),
        getString(R.string.common_times)
    )

    override fun getItem(position: Int): Fragment = ItemFragment.newInstance(position)

    override fun getCount(): Int = categories.size

    override fun getPageTitle(position: Int): CharSequence = categories[position]

}