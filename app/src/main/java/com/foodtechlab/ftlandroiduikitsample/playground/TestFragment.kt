package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.utils.argument

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
        val tvContent = view.findViewById<TextView>(R.id.tv_test_content)
        tvContent.text =
            "Параметры: \ndisplayMode=scrollable, \ngravityMode=center, \nshouldDrawIndicatorFullWidth=true"
    }

    companion object {
        const val KEY_POSITION = "KEY_POSITION"
        const val TAG = "TestFragment"
    }
}