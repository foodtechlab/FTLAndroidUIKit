package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.fragment_playground.*

class PlaygroundFragment : Fragment() {
    var isVisibleBorder = false
    var isVisibleBackground = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_playground, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_border.setOnClickListener {
            isVisibleBorder = !isVisibleBorder
            tg_test.shouldVisibleBorder = isVisibleBorder
        }

        btn_background.setOnClickListener {
            isVisibleBackground = !isVisibleBackground
            tg_test.shouldVisibleBackground = isVisibleBackground
        }
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}