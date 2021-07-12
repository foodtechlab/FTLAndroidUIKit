package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt
import com.foodtechlab.ftlandroiduikitsample.R
import kotlinx.android.synthetic.main.fragment_playground.*

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
        val cats = arrayListOf("Мурзик", "Рыжик", "Барсик", "Борис Бритва", "Мурзилка", "Мурка")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.layout_ftl_autocomplete_drop_down_item,
            cats
        )

        et_test_autocomplete.setHintsAdapter(adapter)

        with(et_test_autocomplete) {
            maxDropDownHeightForFolding =  requireContext().dpToPxInt(100f)
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

                override fun afterTextChanged(s: Editable?) {
                    super.afterTextChanged(s)
                    foldingDropDownDialog(cats, s, 2)
                }
            })
        }
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}