package com.learning.android101.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.viewpager.widget.ViewPager
import com.learning.android101.Constants
import com.learning.android101.R
import com.learning.android101.activities.CalendarActivity
import com.learning.android101.adapters.CalMonthViewPagerAdapter
import com.learning.android101.helpers.DateFormatter
import kotlin.collections.ArrayList
import org.joda.time.DateTime

// Calendar Month that implements CalFragmentHolder
class CalMonthFragmentHolder : CalFragmentHolder() {

    private val noOfMonths = 73                     // number of months to preload (should be odd number)
    private var currentViewPage = 0                 // tracks the position of the viewpager
    private var updateCalendarView = false          // update CalendarView when reach limit

    private lateinit var currentDateStr: String     // basically the current Date that is displaying in fragment
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_month_holder, container, false)
        viewPager = rootView.findViewById<ViewPager>(R.id.vp_monthFragment)
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
        val dialog = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.custom_date_picker, null)
        val datePicker = view.findViewById<DatePicker>(R.id.dp_calendar)
        val daySpinner = datePicker.findViewById<View>(Resources.getSystem().getIdentifier("day", "id", "android"))
        daySpinner?.let {
            daySpinner.visibility = View.GONE
        }

        // initialize date picker with current selected date time
        val datetime = DateFormatter.getDateTimeObjFromDateStr(currentDateStr)
        val year = datetime.year
        val month = datetime.monthOfYear - 1  // Note: DatePicker (0 - 11), monthOfYear (1-12)
        datePicker.init(year, month, 1, null)

        dialog.setView(view)
        dialog.setNegativeButton("Cancel", null)
        dialog.setPositiveButton("Ok") { _ : DialogInterface, _ : Int ->
            currentDateStr = DateFormatter.getDateStrFromYearMonthDay(datePicker.year, datePicker.month + 1, 1)
            setupFragment()
        }
        dialog.show()
    }

    override fun updateActionTitle(displayDate: String) {
        val displayMonthYear = DateFormatter.getMonthTextAndYearFromDateStr(displayDate)
        (activity as CalendarActivity).updateActionBarTitle(displayMonthYear)
    }

    /************************************************************************************
     * Functions for fragments inside ViewPager
     ************************************************************************************/

    // function to set up fragments inside viewPager (Each ViewPage is a Month Fragment)
    private fun setupFragment() {
        // Initialize months
        var currentDateTime = DateFormatter.getDateTimeObjFromDateStr(currentDateStr)
        var months = getMonthsRange(currentDateTime)
        currentViewPage = months.size / 2                           // reason: current Date is stored in middle of array

        // Initialize View Pager
        val monthViewPagerAdapter = CalMonthViewPagerAdapter(activity!!.supportFragmentManager, months)
        viewPager.adapter = monthViewPagerAdapter
        viewPager.currentItem = currentViewPage
        viewPager.clearOnPageChangeListeners()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Refresh the viewPager when the user swiped till the edge
                if (state == ViewPager.SCROLL_STATE_IDLE && updateCalendarView) {
                    // update variables
                    currentDateTime = DateFormatter.getDateTimeObjFromDateStr(months[currentViewPage])      // get current month in view
                    months = getMonthsRange(currentDateTime)                                                // get month range with current month in middle
                    currentViewPage = months.size / 2
                    updateCalendarView = false

                    // Update adapter
                    monthViewPagerAdapter.updateMonthsArray(months)

                    // Update Pager
                    viewPager.setCurrentItem(currentViewPage, false)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) {
                currentViewPage = position
                currentDateStr = months[position]
                if (position <= 0 || position >= noOfMonths) {
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
    private fun getMonthsRange(datetime: DateTime, limit: Int = noOfMonths) : List<String> {
        val months = ArrayList<String>(limit)
        for (i in (-limit / 2)..(limit / 2)) {
            // add dd-MM-yyyy into array
            val dateStr = DateFormatter.getDateStringFromDateTimeObj(datetime.plusMonths(i))
            months.add(dateStr)
        }
        return months
    }
}
