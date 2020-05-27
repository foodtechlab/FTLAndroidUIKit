package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.sheet.DialogButton
import com.foodtechlab.ftlandroiduikit.sheet.DialogState
import com.foodtechlab.ftlandroiduikit.sheet.FTLBottomSheet
import com.foodtechlab.ftlandroiduikit.sheet.Type
import com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        code.addTextChangedListener(object : TextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
            }
        })

        ftl_open_dialog.setOnClickListener(this)
        ftl_make_code_is_wrong.setOnClickListener(this)
        ftl_make_field_is_wrong.setOnClickListener(this)

        ftl_field.addTextChangedListener(object : TextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(this@MainActivity, s.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        ftl_field.setOnFocusChangeListener { _, _ ->
            Toast.makeText(this@MainActivity, "focus is changed", Toast.LENGTH_SHORT).show()
        }

        ftl_bnv_menu.setOnNavigationItemSelectedListener {
            Toast.makeText(this, "${it.itemId}", Toast.LENGTH_SHORT).show()
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            0 -> {
                Toast.makeText(this, "Accept", Toast.LENGTH_SHORT).show()
            }
            -1 -> {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            R.id.ftl_make_code_is_wrong -> {
                code.isErrorEnabled = true
            }
            R.id.ftl_make_field_is_wrong -> {
                ftl_field.isErrorEnabled = !ftl_field.isErrorEnabled
            }
            R.id.ftl_open_dialog -> {
                FTLBottomSheet.newInstance(
                    DialogState(
                        getString(R.string.dialog_sad_title),
                        getString(R.string.dialog_sad_message),
                        Type.ISSUE,
                        listOf(
                            DialogButton(
                                0,
                                "Accept"
                            ),
                            DialogButton(
                                -1,
                                "Cancel",
                                true
                            )
                        )
                    )
                ).show(supportFragmentManager, FTLBottomSheet.TAG)
            }
        }
    }
}
