package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.learning.android101.R
import com.learning.android101.fragments.BotNav1Fragment
import com.learning.android101.fragments.BotNav2Fragment
import com.learning.android101.fragments.BotNav3Fragment
import kotlinx.android.synthetic.main.activity_bot_nav_bar.*

class BotNavBarActivity : AppCompatActivity() {

    lateinit var btmNavFrag1 : BotNav1Fragment
    lateinit var btmNavFrag2 : BotNav2Fragment
    lateinit var btmNavFrag3 : BotNav3Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bot_nav_bar)
        initFragments()
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
                    selectBotNavFragment(btmNavFrag1)
                }
                R.id.btmNavBtn2 -> {
                    selectBotNavFragment(btmNavFrag2)
                }
                R.id.btmNavBtn3 -> {
                    selectBotNavFragment(btmNavFrag3)
                }
            }
            true
        }
    }

    // function that switches the fragments when tab is clicked in the bottom navigation bar
    private fun selectBotNavFragment(fragment: Fragment) : Boolean {
        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.botNav_FrameLayout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit() != 0
    }
}
