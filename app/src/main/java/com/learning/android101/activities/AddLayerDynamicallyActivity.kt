package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_add_layer_dynamically.*

class AddLayerDynamicallyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_layer_dynamically)
        val parentLayer = findViewById<LinearLayout>(R.id.layer_parent)

        btn_addLayer.setOnClickListener {
            val layer = layoutInflater.inflate(R.layout.custom_add_layer_dynamically, null)
            parentLayer.addView(layer, parentLayer.childCount -1 )
        }
    }

    fun onDelete(view: View) {
        val parentLayer = findViewById<LinearLayout>(R.id.layer_parent)
        parentLayer.removeView(view.parent as View)
    }
}
