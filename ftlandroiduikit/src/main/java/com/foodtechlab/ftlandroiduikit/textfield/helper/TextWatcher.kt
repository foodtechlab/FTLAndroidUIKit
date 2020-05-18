package com.foodtechlab.ftlandroiduikit.textfield.helper

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Umalt on 19.05.2020
 */
abstract class TextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}