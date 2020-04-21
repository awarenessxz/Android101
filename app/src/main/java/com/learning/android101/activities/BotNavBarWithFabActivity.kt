package com.learning.android101.activities

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.learning.android101.R
import com.learning.android101.fragments.BotNav1Fragment
import com.learning.android101.fragments.BotNav2Fragment
import com.learning.android101.fragments.BotNav3Fragment
import kotlinx.android.synthetic.main.activity_bot_nav_bar.*
import kotlinx.android.synthetic.main.custom_fab_menu_for_botnavbar.*

class BotNavBarWithFabActivity : AppCompatActivity() {

    // FAB
    private var isFABMenuOpen = false
    private var interpolator = OvershootInterpolator()
    // Bottom Navigation bar
    lateinit var btmNavFrag1 : BotNav1Fragment
    lateinit var btmNavFrag2 : BotNav2Fragment
    lateinit var btmNavFrag3 : BotNav3Fragment
    private var currentNav : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bot_nav_bar_with_fab)
        initFragments()
        initFABMenu()
    }

    // function to initialize fragments
    private fun initFragments() {
        // Initialize fragments for bottom navigation bar
        btmNavFrag1 = BotNav1Fragment()
        btmNavFrag2 = BotNav2Fragment()
        btmNavFrag3 = BotNav3Fragment()

        // Set Home to be default fragment on start up
        selectBotNavFragment(btmNavFrag1)
        botNavBar.selectedItemId = R.id.btmNavBtn1

        // Set OnNavigationItemSelectedListener
        botNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btmNavBtn1 -> {
                    currentNav = 1
                    selectBotNavFragment(btmNavFrag1)
                }
                R.id.btmNavBtn2 -> {
                    currentNav = 2
                    selectBotNavFragment(btmNavFrag2)
                }
                R.id.btmNavBtn3 -> {
                    currentNav = 3
                    selectBotNavFragment(btmNavFrag3)
                }
            }
            true
        }
    }

    // function that switches the fragments when tab is clicked in the bottom navigation bar
    private fun selectBotNavFragment(botNavFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.botNav_FrameLayout, botNavFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        // update FAB Menu
        changeFABMenu()
    }

    /***********************************************************************************************
     * Functions related to FAB Menu Buttons
     ***********************************************************************************************/

    // function to initialize fab menu buttons
    private fun initFABMenu() {
        fab_mainbtn.setOnClickListener {
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

        fab_addNew4.setOnClickListener {
            toggleFABMenu()
            Toast.makeText(this, "Button 4 was clicked", Toast.LENGTH_SHORT).show()
        }

        fab_overlay.setOnClickListener {
            if (isFABMenuOpen) {
                Toast.makeText(this, "BG was clicked", Toast.LENGTH_SHORT).show()
                toggleFABMenu()
            }
        }
    }

    // function to update FAB Menu based on Fragment selected
    private fun changeFABMenu() {
        when (currentNav) {
            1 -> {
                fab_menu.visibility = View.VISIBLE
                fab_mainbtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce))
                fab_mainbtn.setImageResource(R.drawable.ic_thumb_up)
                fab_mainbtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorFABLike))
            }
            2 -> {
                fab_menu.visibility = View.GONE
            }
            3 -> {
                fab_menu.visibility = View.VISIBLE
                fab_mainbtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce))
                fab_mainbtn.setImageResource(R.drawable.ic_thumb_down)
                fab_mainbtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorFABDislike))
            }
        }
    }

    // function to toggle fab menu
    private fun toggleFABMenu() {
        if (isFABMenuOpen) {
            // close Menu
            fab_overlay.visibility = View.GONE
            fab_mainbtn.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start()
            if (currentNav == 1) {
                ll_layer_addNewfab1.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
                ll_layer_addNewfab2.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
            } else if (currentNav == 3) {
                ll_layer_addNewfab3.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
                ll_layer_addNewfab4.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start()
            }
        } else {
            // open Menu
            fab_overlay.visibility = View.VISIBLE
            fab_mainbtn.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start()
            if (currentNav == 1) {
                ll_layer_addNewfab1.animate().translationY(-20f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
                ll_layer_addNewfab2.animate().translationY(-20f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
            } else if (currentNav == 3) {
                ll_layer_addNewfab3.animate().translationY(250f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
                ll_layer_addNewfab4.animate().translationY(250f).alpha(1f).setInterpolator(interpolator).setDuration(300).start()
            }
        }
        // update Menu Status
        isFABMenuOpen = !isFABMenuOpen
    }
}
