package com.learning.android101.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.android101.Constants
import com.learning.android101.R
import com.learning.android101.models.ParcelItem
import com.learning.android101.models.ParcelObj
import kotlinx.android.synthetic.main.activity_pass_parcelable_object_intent1.*

class PassParcelableObjectIntent1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_parcelable_object_intent1)

        var list = arrayListOf<ParcelItem>(ParcelItem("ABC", 1), ParcelItem("DEF", 2))
        val parcel = ParcelObj("object_1", list)

        btn_parcelable.setOnClickListener {
            val intent = Intent(this, PassParcelableObjectIntent2Activity::class.java)
            intent.putExtra(Constants.PARCEL_OBJ, parcel)
            startActivity(intent)
        }
    }
}
