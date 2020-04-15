package com.learning.android101.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.learning.android101.R
import com.learning.android101.views.ColorPickerSlider
import kotlinx.android.synthetic.main.activity_color_picker.*
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.dialog_color_picker.view.*
import kotlinx.android.synthetic.main.dialog_color_picker.view.tv_hexColor
import java.util.*

class ColorPickerActivity : AppCompatActivity(), ColorPickerSlider.OnColorSelectedListener {

    private lateinit var color_slider : ColorPickerSlider
    private lateinit var tv_hexColor : TextView
    private lateinit var iv_hexIcon : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)

        btn_openColorPickerDialog.setOnClickListener {
            val dialog = AlertDialog.Builder(this, R.style.MyDialogTheme)
            val view = layoutInflater.inflate(R.layout.dialog_color_picker, null)
            view.color_picker_slider.mlistener = this               // set OnColorSelectedListener

            tv_hexColor = view.findViewById(R.id.tv_hexColor)
            color_slider = view.findViewById(R.id.color_picker_slider)
            iv_hexIcon = view.findViewById(R.id.iv_hexColorIcon)

            dialog.setTitle("Select Color")
            dialog.setView(view)
            dialog.show()
        }
    }

    override fun onColorChanged(color: Int) {
        val hexColor = Integer.toHexString(color).substring(2).toUpperCase(Locale.ROOT)
        val display =  " Selected Color: #$hexColor"
        tv_hexColor.text = display
        iv_hexIcon.setColorFilter(color)
    }
}
