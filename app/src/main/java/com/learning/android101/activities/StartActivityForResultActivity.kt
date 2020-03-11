package com.learning.android101.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.android101.Constants
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_start_for_result.*

class StartActivityForResultActivity : AppCompatActivity() {

    private val EDIT_ACTIVITY_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_for_result)

        btn_startActivity.setOnClickListener {
            val intent = Intent(this, StartActivityForResult2Activity::class.java)
            startActivityForResult(intent, EDIT_ACTIVITY_REQUEST_CODE)
        }
    }

    // Function for startActivityForResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val returnedObj = data!!.getStringExtra(Constants.RESULT_OBJ)

                tv_results.text = returnedObj
            }
        }
    }
}
