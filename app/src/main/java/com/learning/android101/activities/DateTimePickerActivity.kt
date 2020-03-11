package com.learning.android101.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_date_time_picker.*
import java.text.SimpleDateFormat
import java.util.*

class DateTimePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time_picker)

        btn_datetime.setOnClickListener {
            selectDateTime()
        }
    }

    private fun selectDateTime() {
        val now = Calendar.getInstance()
        val selectedDateTime = Calendar.getInstance()
        val formate = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())

        // set already selected value
        val text = tv_datetime.text.toString()
        if (text.equals("Not selected") || TextUtils.isEmpty(text)) {
            // ignore

        } else {
            now.time = formate.parse(text)!!
        }

        // Date Picker
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            selectedDateTime.set(Calendar.YEAR, year)
            selectedDateTime.set(Calendar.MONTH, monthOfYear)
            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Time Picker
            val tp = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedDateTime.set(Calendar.MINUTE, minute)

                // Set Results
                val currentDateTime = Calendar.getInstance()
                if (selectedDateTime.timeInMillis > currentDateTime.timeInMillis) {
                    // valid
                    tv_datetime.error = null
                    tv_datetime.text = formate.format(selectedDateTime.time)
                } else {
                    // error! Date time is before
                    tv_datetime.error = "Invalid deadline selected"
                    tv_datetime.text = "Not selected"
                    tv_datetime.requestFocus()
                }
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false)
            tp.show()

        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }
}
