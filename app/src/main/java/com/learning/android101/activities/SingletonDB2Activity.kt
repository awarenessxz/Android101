package com.learning.android101.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.android101.R
import com.learning.android101.SingletonDBHandler
import kotlinx.android.synthetic.main.activity_singleton_db2.*

class SingletonDB2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singleton_db2)

        btn_insertValueB.setOnClickListener {
            SingletonDBHandler.addNewValue("FROM B")
        }

        btn_gotoActivityA.setOnClickListener {
            val intent = Intent(this, SingletonDB1Activity::class.java)
            finish()
            startActivity(intent)
        }
    }
}
