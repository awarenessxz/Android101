package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_edit_text.*
import org.w3c.dom.Text

class EditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)

        // This works for single Edit Text
        et_editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_editText.text = s    // s == et_editText.text
            }
        })

        et_user.addTextChangedListener(loginTextWatcher)
        et_pass.addTextChangedListener(loginTextWatcher)

        btn_login.setOnClickListener {
            Toast.makeText(this, "Login Button is clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null && !s.toString().equals("")) {
                // check which editText did this text changed occured from
                if (et_user.editableText == s) {
                    Log.i("testing", "after user")
                } else if (et_pass.editableText == s) {
                    Log.i("testing", "after pass")
                } else {
                    Log.i("testing", "after no where")
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (et_user.text.hashCode() == s.hashCode()) {
                Log.i("testing", "on user")
            } else if (et_pass.text.hashCode() == s.hashCode()) {
                Log.i("testing", "on pass")
            } else {
                Log.i("testing", "on no where")
            }

            // enable button
            if (et_user.text.isNotEmpty() && et_pass.text.isNotEmpty()) {
                btn_login.isEnabled = true
                btn_login.isClickable = true
            } else {
                btn_login.isEnabled = false
                btn_login.isClickable = false
            }
        }
    }
}
