package com.learning.android101.activities

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_fab.*

class FABActivity : AppCompatActivity() {

    var noOfTimes = 0
    var isMenuOpen = false
    var interpolator = OvershootInterpolator()
    var bounceInterpolator = BounceInterpolator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fab)

        /******************** Initialize OnClickListener ********************/

        fab_plus.setOnClickListener {
            toggleMenu()
        }

        fab_1.setOnClickListener {
            Toast.makeText(this, "Button 1 was clicked", Toast.LENGTH_SHORT).show()
        }

        fab_2.setOnClickListener {
            Toast.makeText(this, "Button 2 was clicked", Toast.LENGTH_SHORT).show()
        }

        fab_overlay.setOnClickListener {
            Toast.makeText(this, "BG was clicked", Toast.LENGTH_SHORT).show()
            closeMenuIfOpen()
        }

        btn_changeIcon.setOnClickListener {
            noOfTimes++
            Toast.makeText(this, "I GOT CLICKED! ${noOfTimes}", Toast.LENGTH_SHORT).show()

            // change color & icon of FAB
            if (noOfTimes % 2 == 0) {
                fab_iconChange.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce))
                fab_iconChange.setImageResource(R.drawable.ic_thumb_up)
                fab_iconChange.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorFABLike))
            } else {
                fab_iconChange.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce))
                fab_iconChange.setImageResource(R.drawable.ic_thumb_down)
                //fab_iconChange.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#33691E"))
                fab_iconChange.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorFABDislike))
            }
        }
    }

    // function to control add new item menu (FAB)
    fun toggleMenu() {
        if (isMenuOpen) {
            // close Menu
            fab_overlay.visibility = View.GONE
            btn_changeIcon.isEnabled = true
            fab_plus.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start()
            fab_1.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
            fab_2.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
        } else {
            // open Menu
            fab_overlay.visibility = View.VISIBLE
            btn_changeIcon.isEnabled = false
            fab_plus.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start()
            fab_1.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
            fab_2.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
        }
        // update Menu Status
        isMenuOpen = !isMenuOpen
    }

    fun closeMenuIfOpen() {
        if (isMenuOpen) {
            fab_overlay.visibility = View.GONE
            btn_changeIcon.isEnabled = true
            fab_plus.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start()
            fab_1.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
            fab_2.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
            // update Menu Status
            isMenuOpen = !isMenuOpen
        }
    }
}
