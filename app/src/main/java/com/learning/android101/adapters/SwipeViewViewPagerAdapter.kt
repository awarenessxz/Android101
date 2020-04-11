package com.learning.android101.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.learning.android101.fragments.SwipeViewWithViewPagerFragment

class SwipeViewViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment = SwipeViewWithViewPagerFragment()
        val bundle = Bundle()
        bundle.putString("message", "Hello from Postion ${position+1}")
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return 100
    }
}