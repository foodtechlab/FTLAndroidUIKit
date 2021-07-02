package com.foodtechlab.ftlandroiduikitsample.playground

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.foodtechlab.ftlandroiduikit.bar.FTLBottomNavigationView
import com.foodtechlab.ftlandroiduikit.bar.FTLMenuItem
import com.foodtechlab.ftlandroiduikitsample.R
import com.redmadrobot.inputmask.MaskedTextChangedListener
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
        val cats = arrayOf("+7 (902) 999-99-99", "+7 (999) 626-06-13", "+7 (999) 999-99-99")

        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, cats
        )
        etPhoneEnter.etInput.setAdapter(adapter)

        // Минимальное число символов для показа выпадающего списка
        etPhoneEnter.etInput.threshold = 2

        // Отслеживаем закрытие выпадающего списка
        etPhoneEnter.etInput.setOnDismissListener {
         Log.i("MY TAG", "setOnDismissListener")
        }

        // Если к компоненту перешёл фокус
        etPhoneEnter.etInput.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Выводим выпадающий список
                //etPhoneEnter.etInput.showDropDown()
            }
        }

        val maskedTextChangedListener = MaskedTextChangedListener(
            "+7 ([000]) [000]-[00]-[00]",
            etPhoneEnter.etInput,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    if (extractedValue.isNotEmpty() && extractedValue.first() != '9') {
                        with(etPhoneEnter.etInput) {
                            text?.clear()
                            setText(R.string.fragment_auth_phone_prefix)
                        }
                    } else {
                        Log.i("MY TAG", "phone = $formattedValue")
                    }
                }
            }
        )
        /*with(etPhoneEnter) {
            maskedTextChangedListener?.let {
                addTextChangedListener(it)
            }
            editorActionListener = TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("MY TAG", "performClick")
                    return@OnEditorActionListener true
                }
                return@OnEditorActionListener false
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && !etInput.text.isNullOrEmpty()) {
                    if (etInput.selectionStart <= 4) {
                        etInput.setSelection(etInput.length())
                    }
                }
            }
        }*/
    }

    companion object {
        const val TAG = "PlaygroundFragment"
    }
}