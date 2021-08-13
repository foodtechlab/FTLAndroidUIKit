package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.bar.toolbar.FTLToolbar
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
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
        val toolbar = view.findViewById<FTLToolbar>(R.id.toolbar)
        with(toolbar) {
            showCircleProgressIndicator(ImageType.BONUSES)
            maxProgress = 15
            currentProgress = 10
        }
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}