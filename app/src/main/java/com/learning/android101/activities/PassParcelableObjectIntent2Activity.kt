package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.android101.Constants
import com.learning.android101.R
import com.learning.android101.models.ParcelObj
import kotlinx.android.synthetic.main.activity_pass_parcelable_object_intent2.*

class PassParcelableObjectIntent2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_parcelable_object_intent2)

        intent?.let {
            val parcelObj = intent.extras?.getParcelable<ParcelObj>(Constants.PARCEL_OBJ)
            tv_displayParcel.text = parcelObj!!.displayValues()
        }
    }
}
