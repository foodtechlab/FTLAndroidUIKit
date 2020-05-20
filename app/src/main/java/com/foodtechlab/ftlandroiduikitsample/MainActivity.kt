package com.foodtechlab.ftlandroiduikitsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodtechlab.ftlandroiduikit.sheets.SadBottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_primary_click.setOnClickListener(this)
        btn_secondary_click.setOnClickListener(this)
        btn_additional_click.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_dialog_sad_neutral -> {
                Toast.makeText(this, "Neutral", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_dialog_sad_positive -> {
                Toast.makeText(this, "Positive", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_dialog_sad_negative -> {
                Toast.makeText(this, "Negative", Toast.LENGTH_SHORT).show()
            }
            else -> {
                SadBottomSheetDialog.newInstance(
                    R.string.dialog_sad_title,
                    R.string.dialog_sad_message,
                    positiveBtnVisibility = View.VISIBLE,
                    negativeBtnVisibility = View.VISIBLE
                ).show(supportFragmentManager, SadBottomSheetDialog.TAG)
            }
        }
    }
}
