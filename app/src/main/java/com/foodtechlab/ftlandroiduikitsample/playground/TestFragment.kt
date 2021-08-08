package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.utils.argument
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment() : Fragment() {
    private var pos: Int by argument(KEY_POSITION)

    constructor(position: Int) : this() {
        arguments = Bundle().apply {
            putInt(KEY_POSITION, position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(lpi1) {
            currentProgress = 5
            maxProgress = 20
        }
        with(mpv){
          //  ivContent.load("https://images.unsplash.com/photo-1472851294608-062f824d29cc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80")
        }
    }

    companion object {
        const val KEY_POSITION = "KEY_POSITION"
        const val TAG = "TestFragment"
    }
}