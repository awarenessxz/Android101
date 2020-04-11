package com.learning.android101.adapters

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.learning.android101.Constants
import com.learning.android101.fragments.CalMonthFragment

class CalMonthViewPagerAdapter(fm: FragmentManager, monthsArray: List<String>) : FragmentStatePagerAdapter(fm) {

    private var monthsArray = monthsArray                               // months to display

    override fun getItem(position: Int): Fragment {
        val fragment = CalMonthFragment()
        val bundle = Bundle()
        bundle.putString(Constants.ARG_DATE, "${monthsArray[position]}")
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return monthsArray.size
    }

    fun updateMonthsArray(updatedMonthsArray: List<String>) {
        monthsArray = updatedMonthsArray
    }
}