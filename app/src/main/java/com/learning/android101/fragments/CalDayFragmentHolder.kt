package com.learning.android101.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.learning.android101.Constants

import com.learning.android101.R
import com.learning.android101.activities.CalendarActivity
import com.learning.android101.adapters.CalDayViewPagerAdapter
import com.learning.android101.helpers.DateFormatter
import com.learning.android101.interfaces.CalNavigationListener
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList

// Calendar Day that implements CalFragmentHolder
class CalDayFragmentHolder : CalFragmentHolder(), CalNavigationListener {

    private val noOfDays = 55                       // number of days to preload (should be odd number)
    private var currentViewPage = 0                 // tracks the position of the viewpager
    private var updateCalendarView = false          // update CalendarView when reach limit

    private lateinit var currentDateStr: String     // basically the current Date that is displaying in fragment
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_day_holder, container, false)
        viewPager = rootView.findViewById<ViewPager>(R.id.vp_dayFragment)
        currentDateStr = arguments?.getString(Constants.ARG_DATE) ?: ""
        setupFragment()
        return rootView
    }

    /************************************************************************************
     * Abstract functions (implemented by CalFragmentHolder)
     ************************************************************************************/

    override fun goToToday() {
        currentDateStr = DateFormatter.getCurrentDateString()
        setupFragment()
    }

    override fun showGoToDateDialog() {
        val now = Calendar.getInstance()
        now.time = DateFormatter.getDateObjFromDateString(currentDateStr)

        // Date picker
        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            currentDateStr = DateFormatter.getDateStrFromYearMonthDay(year, monthOfYear + 1, dayOfMonth)
            setupFragment()
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
        dpd.show()
    }

    override fun updateActionTitle(displayDate: String) {
        val display = DateFormatter.formatDateStr(displayDate)  // format = dd MMM yy
        (activity as CalendarActivity).updateActionBarTitle(display)
    }

    /************************************************************************************
     * Abstract functions (implemented by CalNavigationListener)
     ************************************************************************************/

    override fun swipeLeft() {
        viewPager.currentItem = viewPager.currentItem - 1
    }

    override fun swipeRight() {
        viewPager.currentItem = viewPager.currentItem + 1
    }

    /************************************************************************************
     * Functions for fragments inside ViewPager
     ************************************************************************************/

    // function to set up fragments inside viewPager (Each ViewPage is a Day Fragment)
    private fun setupFragment() {
        // Initialize months
        var currentDateTime = DateFormatter.getDateTimeObjFromDateStr(currentDateStr)
        var days = getDaysRange(currentDateTime)
        currentViewPage = days.size / 2                           // reason: current Date is stored in middle of array

        // Initialize View Pager
        val dayViewPagerAdapter = CalDayViewPagerAdapter(activity!!.supportFragmentManager, days, this)
        viewPager.adapter = dayViewPagerAdapter
        viewPager.currentItem = currentViewPage
        viewPager.clearOnPageChangeListeners()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Refresh the viewPager when the user swiped till the edge
                if (state == ViewPager.SCROLL_STATE_IDLE && updateCalendarView) {
                    // update variables
                    currentDateTime = DateFormatter.getDateTimeObjFromDateStr(days[currentViewPage])    // get current day in view
                    days = getDaysRange(currentDateTime)                                                // get day range with current day in middle
                    currentViewPage = days.size / 2
                    updateCalendarView = false

                    // Update adapter
                    dayViewPagerAdapter.updateDaysArray(days)

                    // Update Pager
                    viewPager.setCurrentItem(currentViewPage, false)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) {
                currentViewPage = position
                currentDateStr = days[position]
                if (position <= 0 || position >= noOfDays) {
                    updateCalendarView = true
                }
                // Update Action Bar
                updateActionTitle(currentDateStr)
            }
        })

        // Update Action Bar
        updateActionTitle(currentDateStr)
    }

    // function to get months range (prevMonth - currentMonth - nextMonth)
    private fun getDaysRange(datetime: DateTime, limit: Int = noOfDays) : List<String> {
        val days = ArrayList<String>(limit)
        for (i in (-limit / 2)..(limit / 2)) {
            // add dd-MM-yyyy into array
            val dateStr = DateFormatter.getDateStringFromDateTimeObj(datetime.plusDays(i))
            days.add(dateStr)
        }
        return days
    }
}
