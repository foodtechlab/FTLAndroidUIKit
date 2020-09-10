package com.foodtechlab.ftlandroiduikitsample.showroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.utils.*
import kotlinx.android.synthetic.main.fragment_showroom.*

class ShowroomFragment : Fragment(), Dismissible {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_showroom, container, false)
        arguments?.let {
            view.registerCircularRevealAnimation(it.getParcelable(ARG_REVEAL_SETTINGS)!!,
                ContextCompat.getColor(requireContext(), R.color.PrimaryDangerPressed),
                ContextCompat.getColor(requireContext(), R.color.BackgroundPrimary))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val fragmentAdapter = PagerAdapter(childFragmentManager)
        vpShowroomContent.adapter = fragmentAdapter
        tblShowroomCategories.setupWithViewPager(vpShowroomContent)
    }

    override fun dismiss(listener: Dismissible.OnDismissedListener) {
        arguments?.let {
            view!!.startCircularExitAnimation(
                it.getParcelable(ARG_REVEAL_SETTINGS)!!,
                ContextCompat.getColor(requireContext(), R.color.BackgroundPrimary),
                ContextCompat.getColor(requireContext(), R.color.PrimaryDangerPressed),
                object : Dismissible.OnDismissedListener {
                    override fun onDismissed() {
                        view!!.visibility = View.GONE
                        listener.onDismissed()
                    }
                })
        }
    }

    companion object {
        const val ARG_REVEAL_SETTINGS = "arg_reveal_settings"
        fun newInstance(revealSettings: RevealAnimationSetting) = ShowroomFragment().withArgs { it.putParcelable(ARG_REVEAL_SETTINGS, revealSettings) }
    }
}