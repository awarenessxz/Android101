package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_text_spinner.*

class TextSpinnerActivity : AppCompatActivity() {

    var options : ArrayList<String> = arrayListOf("A", "B")
    var adapter : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_spinner)
        initDefaultSpinner()

        btn_addSpinnerItem.setOnClickListener {
            options.add("Newly Added")
            adapter!!.notifyDataSetChanged()
        }
    }

    // initialize default spinner
    private fun initDefaultSpinner() {

        // Set Adapter
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , options)
        sp_option.adapter = adapter

        // Set selected listener
        sp_option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tv_result.text = "Please select an option"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tv_result.text = options[position]
            }
        }
    }
}
