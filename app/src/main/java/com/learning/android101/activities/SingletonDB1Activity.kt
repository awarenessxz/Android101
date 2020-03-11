package com.learning.android101.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.android101.R
import com.learning.android101.SingletonDBHandler
import kotlinx.android.synthetic.main.activity_singleton_db1.*

class SingletonDB1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singleton_db1)

        btn_insertValueA.setOnClickListener {
            SingletonDBHandler.addNewValue("FROM A")
        }

        btn_gotoActivityB.setOnClickListener {
            val intent = Intent(this, SingletonDB2Activity::class.java)
            finish()
            startActivity(intent)
        }

        val result = SingletonDBHandler.getResults()
        tv_displayResult.text = result.toString()
    }
}
