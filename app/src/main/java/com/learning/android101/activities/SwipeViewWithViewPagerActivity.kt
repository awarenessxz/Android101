package com.learning.android101.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.learning.android101.R
import com.learning.android101.adapters.SwipeViewViewPagerAdapter

class SwipeViewWithViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_view_with_view_pager)

        // initialize
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val adapter = SwipeViewViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }
}
