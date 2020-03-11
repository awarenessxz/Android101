package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_labelled_fab_with_overlay.*
import kotlinx.android.synthetic.main.custom_fab_menu.*

class LabelledFabWithOverlayActivity : AppCompatActivity() {

    var noOfTimes = 0
    var isFABMenuOpen = false
    var interpolator = OvershootInterpolator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labelled_fab_with_overlay)

        setSupportActionBar(toolbar)

        /******************** Initialize OnClickListener ********************/

        fab_plus.setOnClickListener {
            toggleFABMenu()
        }

        fab_addNew1.setOnClickListener {
            toggleFABMenu()
            Toast.makeText(this, "Button 1 was clicked", Toast.LENGTH_SHORT).show()
        }

        fab_addNew2.setOnClickListener {
            toggleFABMenu()
            Toast.makeText(this, "Button 2 was clicked", Toast.LENGTH_SHORT).show()
        }

        fab_addNew3.setOnClickListener {
            toggleFABMenu()
            Toast.makeText(this, "Button 3 was clicked", Toast.LENGTH_SHORT).show()
        }

        fab_overlay.setOnClickListener {
            if (isFABMenuOpen) {
                Toast.makeText(this, "BG was clicked", Toast.LENGTH_SHORT).show()
                toggleFABMenu()
            }
        }

        btn_noClick.setOnClickListener {
            noOfTimes++
            Toast.makeText(this, "I GOT CLICKED! ${noOfTimes}", Toast.LENGTH_SHORT).show()
        }
    }

    // function to toggle fab menu
    private fun toggleFABMenu() {
        if (isFABMenuOpen) {
            // close Menu
            fab_overlay.visibility = View.GONE
            fab_plus.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start()
            ll_layer_addNewfab1.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
            ll_layer_addNewfab2.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
            ll_layer_addNewfab3.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        } else {
            // open Menu
            fab_overlay.visibility = View.VISIBLE
            fab_plus.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start()
            ll_layer_addNewfab1.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
            ll_layer_addNewfab2.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
            ll_layer_addNewfab3.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        }
        // update Menu Status
        isFABMenuOpen = !isFABMenuOpen
    }
}
