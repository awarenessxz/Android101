package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.learning.android101.R
import com.learning.android101.fragments.BotNav1Fragment
import com.learning.android101.fragments.BotNav2Fragment
import com.learning.android101.fragments.BotNav3Fragment
import kotlinx.android.synthetic.main.activity_nav_drawer.*
import kotlinx.android.synthetic.main.nav_drawer_content_main.*

class NavDrawerWithFragActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var btmNavFrag1 : BotNav1Fragment
    lateinit var btmNavFrag2 : BotNav2Fragment
    lateinit var btmNavFrag3 : BotNav3Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer_with_frag)

        initFragments()

        setSupportActionBar(navDrawer_toolbar)

        val toggle = ActionBarDrawerToggle (this, navDrawerLayout, navDrawer_toolbar, 0, 0)
        navDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nv_navDrawer.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_first_fragment -> {
                selectNavFragment(btmNavFrag1)
            }
            R.id.nav_second_fragment -> {
                selectNavFragment(btmNavFrag2)
            }
            R.id.nav_third_fragment -> {
                selectNavFragment(btmNavFrag3)
            }
        }
        navDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    // function to initialize fragments
    private fun initFragments() {
        // Initialize fragments for bottom navigation bar
        btmNavFrag1 = BotNav1Fragment()
        btmNavFrag2 = BotNav2Fragment()
        btmNavFrag3 = BotNav3Fragment()

        // Set default fragment to appear first
        selectNavFragment(btmNavFrag1)
    }

    // function that switches the fragments when tab is clicked in the bottom navigation bar
    private fun selectNavFragment(fragment: Fragment) : Boolean {
        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.navDrawerFrag_FrameLayout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit() != 0
    }
}
