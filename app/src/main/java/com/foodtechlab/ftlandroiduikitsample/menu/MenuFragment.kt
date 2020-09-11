package com.foodtechlab.ftlandroiduikitsample.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.main.MainActivity
import com.foodtechlab.ftlandroiduikitsample.main.OnNavigateListener
import com.foodtechlab.ftlandroiduikitsample.playground.PlaygroundFragment
import com.foodtechlab.ftlandroiduikitsample.showroom.ShowroomFragment
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private var onNavigateListener: OnNavigateListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNavigateListener = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnGoToPlayground.setOnClickListener {
            onNavigateListener?.onNavigate(PlaygroundFragment(), PlaygroundFragment.TAG, true)
        }
        btnGoToShowroom.setOnClickListener {
            onNavigateListener?.onNavigate(ShowroomFragment(), ShowroomFragment.TAG, true)
        }
    }

    companion object {
        const val TAG = "MenuFragment"
    }
}