package com.learning.android101.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.learning.android101.Constants
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Explicit Intent: Receiving Data from previous activity
        val bundle: Bundle? = intent.extras
        val msg = bundle!!.getString(Constants.USER_MSG_KEY)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        //Display on the screen
        tvUserMessage.text = msg
    }
}