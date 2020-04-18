package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.learning.android101.R
import com.learning.android101.views.HSVColorPickerView
import kotlinx.android.synthetic.main.activity_color_hsv_picker.*
import kotlinx.android.synthetic.main.dialog_color_hsv_picker.view.*
import java.util.*

class ColorHSVPickerActivity : AppCompatActivity(), HSVColorPickerView.OnColorSelectedListener {

    private lateinit var tv_hexColor : TextView
    private lateinit var iv_hexIcon : ImageView
    private lateinit var color_picker : HSVColorPickerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_hsv_picker)

        btn_openColorPickerDialog.setOnClickListener {
            val dialog = AlertDialog.Builder(this, R.style.MyDialogTheme)
            val view = layoutInflater.inflate(R.layout.dialog_color_hsv_picker, null)
            view.color_picker_view.mlistener = this

            tv_hexColor = view.findViewById(R.id.tv_hexColor)
            color_picker = view.findViewById(R.id.color_picker_view)
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
