package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher
import com.foodtechlab.ftlandroiduikit.textfield.helper.FTLDropDownAdapter
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.fragment_playground.*

class PlaygroundFragment : Fragment(), FTLDropDownAdapter.FTLMenuCellsChangesListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_playground, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cats = arrayListOf("Мурзик", "Рыжик", "Барсик", "Борис Бритва", "Мурзилка", "Мурка")

        val adapter = FTLDropDownAdapter(
            requireContext(),
            R.layout.layout_ftl_autocomplete_drop_down_item,
            cats
        ).apply {
            menuCellsChangesListener = this@PlaygroundFragment
        }

        et_test_autocomplete.setHintsAdapter(adapter)

        with(et_test_autocomplete) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    // Выводим выпадающий список
                    et_test_autocomplete.showHints()
                }
            }
            addTextChangedListener(object : TextWatcher() {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length ?: 0 >= 6) {
                        et_test_autocomplete.hideHints()
                    }
                    super.onTextChanged(s, start, before, count)
                }
            })
        }
    }

    override fun onQuantityChanges(size: Int) {
        et_test_autocomplete.etInput.dropDownHeight = if (size < 2) {
            ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            requireContext().dpToPxInt(88f)
        }
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}