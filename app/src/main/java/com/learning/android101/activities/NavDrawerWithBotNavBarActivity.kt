package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.learning.android101.R
import com.learning.android101.fragments.BotNav1Fragment
import com.learning.android101.fragments.BotNav2Fragment
import com.learning.android101.fragments.BotNav3Fragment
import kotlinx.android.synthetic.main.activity_bot_nav_bar.*
import kotlinx.android.synthetic.main.activity_nav_drawer.*
import kotlinx.android.synthetic.main.nav_drawer_content_main.*

class NavDrawerWithBotNavBarActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var btmNavFrag1 : BotNav1Fragment
    lateinit var btmNavFrag2 : BotNav2Fragment
    lateinit var btmNavFrag3 : BotNav3Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer_with_bot_nav_bar)

        setSupportActionBar(navDrawer_toolbar)

        val toggle = ActionBarDrawerToggle (this, navDrawerLayout, navDrawer_toolbar, 0, 0)
        navDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nv_navDrawer.setNavigationItemSelectedListener(this)

        btmNavFrag1 = BotNav1Fragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.botNav_FrameLayout, btmNavFrag1)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        botNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btmNavBtn1 -> {
                    btmNavFrag1 = BotNav1Fragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.botNav_FrameLayout, btmNavFrag1)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.btmNavBtn2 -> {
                    btmNavFrag2 = BotNav2Fragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.botNav_FrameLayout, btmNavFrag2)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.btmNavBtn3 -> {
                    btmNavFrag3 = BotNav3Fragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.botNav_FrameLayout, btmNavFrag3)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navDrawer_profile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.navDrawer_messages -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.navDrawer_friends -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.navDrawer_update -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.navDrawer_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        navDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
