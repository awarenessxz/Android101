package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.learning.android101.DBHandler
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_sqlite.*

class SQLiteActivity : AppCompatActivity() {

    lateinit var dbHandler : DBHandler
    var adapter : ArrayAdapter<String>? = null
    var options : ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)
        dbHandler = DBHandler(this)
        initDefaultSpinner()

        btn_addValue.setOnClickListener {
            if(et_newValue.text.isEmpty()) {
                et_newValue.setError("Please enter some values")
            } else {
                val result = dbHandler.addNewValue(et_newValue.text.toString())
                if (result) {
                    Toast.makeText(this,"Add value Success", Toast.LENGTH_SHORT).show()
                    options = dbHandler.getResults() as ArrayList<String>
                    adapter!!.clear()
                    adapter!!.addAll(options)
                    adapter!!.notifyDataSetChanged()
                    et_newValue.text.clear()
                } else {
                    Toast.makeText(this,"Add value Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // initialize default spinner
    private fun initDefaultSpinner() {
        // Get Value from Database
        options = dbHandler.getResults() as ArrayList<String>

        // Set Adapter
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , options)
        sp_results.adapter = adapter
    }
}
