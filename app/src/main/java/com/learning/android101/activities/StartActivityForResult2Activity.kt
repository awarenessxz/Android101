package com.learning.android101.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.android101.Constants
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_start_for_result2.*

class StartActivityForResult2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_for_result2)

        btn_closeActivity.setOnClickListener {
            // Put the String to pass back into an Intent and close this activity
            val intent = Intent()
            intent.putExtra(Constants.RESULT_OBJ, "STRING TO PASS BACK")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
