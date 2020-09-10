package com.foodtechlab.ftlandroiduikitsample.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.main.MainActivity
import com.foodtechlab.ftlandroiduikitsample.playground.PlaygroundFragment
import com.foodtechlab.ftlandroiduikitsample.showroom.ShowroomFragment
import com.foodtechlab.ftlandroiduikitsample.utils.RevealAnimationSetting
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnGoToPlayground.setOnClickListener {
            (activity as MainActivity).openFragment(
                PlaygroundFragment.newInstance(
                    RevealAnimationSetting(
                        (btnGoToShowroom.x + btnGoToShowroom.width / 2).toInt(),
                        (btnGoToShowroom.y + btnGoToShowroom.height / 2).toInt(),
                        view.width, view.height
                    )
                )
            )
        }
        btnGoToShowroom.setOnClickListener {
            (activity as MainActivity).openFragment(
                ShowroomFragment.newInstance(
                    RevealAnimationSetting(
                        (btnGoToShowroom.x + btnGoToShowroom.width / 2).toInt(),
                        (btnGoToShowroom.y + btnGoToShowroom.height / 2).toInt(),
                        view.width, view.height
                    )
                )
            )
        }
    }
}