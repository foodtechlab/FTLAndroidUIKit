package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.textfield.CodeEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, CodeEditText.OnCodeChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ftl_bnv_menu.setOnNavigationItemSelectedListener {
            Toast.makeText(this, "${it.itemId}", Toast.LENGTH_SHORT).show()
            return@setOnNavigationItemSelectedListener true
        }

        cet_code.onCodeChangeListener = this

        btn_primary_click.setOnClickListener(this)
        btn_secondary_click.setOnClickListener(this)
        btn_additional_click.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            0 -> {
                Toast.makeText(this, "Accept", Toast.LENGTH_SHORT).show()
            }
            -1 -> {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            else -> {
                ftl.isErrorEnabled = true

//                FTLBottomSheet.newInstance(
//                    DialogState(
//                        getString(R.string.dialog_sad_title),
//                        getString(R.string.dialog_sad_message),
//                        Type.ISSUE,
//                        listOf(
//                            DialogButton(
//                                0,
//                                "Accept"
//                            ),
//                            DialogButton(
//                                -1,
//                                "Cancel",
//                                true
//                            )
//                        )
//                    )
//                ).show(supportFragmentManager, FTLBottomSheet.TAG)
            }
        }
    }

    override fun onCodeChanged(code: String) {
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show()
    }
}
