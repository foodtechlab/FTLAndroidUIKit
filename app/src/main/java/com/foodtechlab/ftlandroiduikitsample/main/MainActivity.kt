package com.foodtechlab.ftlandroiduikitsample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.menu.MenuFragment
import com.foodtechlab.ftlandroiduikitsample.utils.Dismissible
import com.foodtechlab.ftlandroiduikitsample.utils.Utils


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) openFragment(MenuFragment())
    }

    fun openFragment(fragmentInstance: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.flMainContainer, fragmentInstance)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.flMainContainer)
        if (fragment is Dismissible) {
            fragment.dismiss(object : Dismissible.OnDismissedListener {
                override fun onDismissed() {
                    supportFragmentManager.beginTransaction().remove(fragment).commit()
                }
            })
        } else {
            finish()
        }
    }
}