package com.foodtechlab.ftlandroiduikitsample.menu

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.main.MainActivity
import com.foodtechlab.ftlandroiduikitsample.main.OnNavigateListener
import com.foodtechlab.ftlandroiduikitsample.playground.PlaygroundFragment
import com.foodtechlab.ftlandroiduikitsample.showroom.ShowroomFragment
import com.foodtechlab.ftlandroiduikitsample.utils.Utils.updateBarColors
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private var onNavigateListener: OnNavigateListener? = null

    private val prefs: SharedPreferences? by lazy {
        activity?.getSharedPreferences(
            "app_settings",
            MODE_PRIVATE
        )
    }

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
        with(scTheme) {
            isChecked = ThemeManager.theme == ThemeManager.Theme.DARK
            setOnCheckedChangeListener { _, isChecked ->
                ThemeManager.setTheme(
                    if (isChecked) ThemeManager.Theme.DARK else ThemeManager.Theme.LIGHT,
                    ivDecorScreen, clMenuRoot, centerX = 100, centerY = 100, animate = true
                )
                prefs?.edit()?.putInt("key_theme", ThemeManager.theme.ordinal)?.apply()
                activity?.window?.updateBarColors()
            }
        }
    }

    companion object {
        const val TAG = "MenuFragment"
    }
}