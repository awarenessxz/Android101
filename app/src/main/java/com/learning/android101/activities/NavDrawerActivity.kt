package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.learning.android101.R
import kotlinx.android.synthetic.main.activity_nav_drawer.*
import kotlinx.android.synthetic.main.nav_drawer_content_main.*

class NavDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)

        setSupportActionBar(navDrawer_toolbar)

        val toggle = ActionBarDrawerToggle (this, navDrawerLayout, navDrawer_toolbar, 0, 0)
        navDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nv_navDrawer.setNavigationItemSelectedListener(this)
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
