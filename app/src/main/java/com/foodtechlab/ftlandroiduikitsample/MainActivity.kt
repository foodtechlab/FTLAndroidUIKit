package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.bar.FTLBottomNavigationView.MenuItem.*
import com.foodtechlab.ftlandroiduikit.bar.toolbar.OnToolbarClickListener
import com.foodtechlab.ftlandroiduikit.sheet.DialogButton
import com.foodtechlab.ftlandroiduikit.sheet.DialogState
import com.foodtechlab.ftlandroiduikit.sheet.FTLBottomSheet
import com.foodtechlab.ftlandroiduikit.sheet.Type
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, OnToolbarClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        code.addTextChangedListener(object : TextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
            }
        })

        toolbar.apply {
            showEndButton()
            onToolbarClickListener = this@MainActivity
        }

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

        ftl_bnv_menu.apply {
            addMenuItems(
                listOf(
                    ORDERS,
                    MAPS,
                    HISTORY,
                    MORE
                )
            )
            setOnNavigationItemSelectedListener {
                Toast.makeText(this@MainActivity, "${it.itemId}", Toast.LENGTH_SHORT).show()
                return@setOnNavigationItemSelectedListener true
            }
        }

        tv_addresses.textAddressFrom =
            "Волгоград, ул. Садовое товарищество Авангард, д.62 кв./офис 102, под. 1 эт. 24"

        ftl_tv_multiple.apply {
            imageType = ImageType.CASH
            textTopStartSlot = "Наличными"
            textBottomStartSlot = "Без сдачи"
            textTopEndSlot = "1000 р"
            textBottomEndSlot = "Сумма: 1000 р"
        }
        ftl_tv_extendable.apply {
            fullText = "Воу привет! а можно ли мне какой-нибудь, очень вкусный" +
                    " комплимент от шефа! Спасибо, Воу Воу Воу Воу Воу Воу"
            ellipsizedText = "... еще"
        }
        ftl_tv_default.apply {
            textForSlot = "John Wiskas"
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

    override fun onToolbarClick(v: View) {
        when (v.id) {
            R.id.ib_ftl_toolbar_start -> {
                Toast.makeText(this, "Start button click", Toast.LENGTH_SHORT).show()
            }
            R.id.ib_ftl_toolbar_end -> {
                Toast.makeText(this, "End button click", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
