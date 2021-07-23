package com.foodtechlab.ftlandroiduikitsample.playground

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class VPAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    private val checklistsType = arrayListOf(
        "Item 0",
        "Item 1",
        "Item 2",
        "Item Item Item Item Item Item Item 3"
    )

    override fun getItemCount(): Int {
        return checklistsType.size
    }

    override fun createFragment(position: Int): Fragment {
        return TestFragment(position)
    }

    fun getPageTitle(position: Int): CharSequence {
        return checklistsType[position]
    }
}