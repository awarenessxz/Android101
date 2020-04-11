package com.learning.android101.adapters

import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.learning.android101.Constants
import com.learning.android101.fragments.CalWeekFragment
import com.learning.android101.interfaces.SyncScrollListener

class CalWeekViewPagerAdapter(fm: FragmentManager, weeksArray: List<String>, syncScrollListener: SyncScrollListener) : FragmentStatePagerAdapter(fm) {

    private val fragments = SparseArray<CalWeekFragment>()
    private var weeksArray = weeksArray             // first day of the week
    private var syncScrollListener = syncScrollListener

    override fun getItem(position: Int): Fragment {
        val fragment = CalWeekFragment()
        val bundle = Bundle()
        bundle.putString(Constants.ARG_DATE, "${weeksArray[position]}");
        fragment.arguments = bundle
        fragment.outerSyncScrollListener = syncScrollListener
        fragments.put(position, fragment)                               // stores the fragment
        return fragment
    }

    override fun getCount(): Int {
        return weeksArray.size
    }

    fun updateWeeksArray(updatedWeeksArray: List<String>) {
        weeksArray = updatedWeeksArray
    }

    // for synchronizing the outer and inner scrollbar
    fun updateScrollY(pos: Int, y: Int) {
        fragments[pos].updateScrollY(y)
    }
}