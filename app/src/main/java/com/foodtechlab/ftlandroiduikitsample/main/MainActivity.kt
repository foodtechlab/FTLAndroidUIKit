package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.menu.MenuFragment
import com.foodtechlab.ftlandroiduikitsample.utils.attachFragment
import com.foodtechlab.ftlandroiduikitsample.utils.replaceFragment

class MainActivity : AppCompatActivity(), OnNavigateListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) onNavigateExclusive(MenuFragment(), MenuFragment.TAG)
    }

    /**
     * Метод для навигации по фрагментам без чистки popBackStack,
     * в случае если фрагмент будет найден по тегу к нему применится [FragmentTransaction.attach],
     * в противном случае применится [FragmentTransaction.add]
     */
    override fun onNavigate(fragmentInstance: Fragment, tag: String?, isAddToBackStack: Boolean) {
        supportFragmentManager.attachFragment(
            R.id.flMainContainer,
            fragmentInstance,
            tag,
            isAddToBackStack
        )
    }

    /**
     * Метод для навигации по фрагментам с чисткой popBackStack
     * и удалением предыдущих экземпляров фрагментов
     */
    override fun onNavigateExclusive(fragmentInstance: Fragment, tag: String?) {
        supportFragmentManager.replaceFragment(R.id.flMainContainer, fragmentInstance, tag)
    }
}