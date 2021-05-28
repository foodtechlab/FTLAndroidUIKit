package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.bar.FTLBottomNavigationView
import com.foodtechlab.ftlandroiduikit.bar.FTLMenuItem
import com.foodtechlab.ftlandroiduikitsample.R

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
        val bnv = view.findViewById<FTLBottomNavigationView>(R.id.bnv_playground)
        with(bnv) {
            addMenuItems(
                listOf(
                    FTLMenuItem(
                        ID_ORDERS,
                        R.string.ftl_bnv_schedule,
                        R.drawable.ic_clock_24
                    ),
                    FTLMenuItem(
                        ID_MAPS,
                        R.string.ftl_bnv_departments,
                        R.drawable.ic_shop_16
                    ),
                    FTLMenuItem(
                        ID_HISTORY,
                        R.string.ftl_bnv_history,
                        R.drawable.ic_history_light_24
                    ),
                    FTLMenuItem(
                        ID_MORE,
                        R.string.ftl_bnv_additional,
                        R.drawable.ic_more_light_24
                    )
                )
            )
        }
    }

    companion object {
        const val ID_ORDERS = 2314
        const val ID_MAPS = 5634
        const val ID_HISTORY = 2378
        const val ID_MORE = 2745

        const val TAG = "PlaygroundFragment"
    }
}