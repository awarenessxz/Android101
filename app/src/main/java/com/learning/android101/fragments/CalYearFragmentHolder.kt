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
import com.learning.android101.R
import com.learning.android101.activities.CalendarActivity
import com.learning.android101.adapters.CalYearViewPagerAdapter
import com.learning.android101.helpers.DateFormatter

// Calendar Year that implements CalFragmentHolder
class CalYearFragmentHolder : CalFragmentHolder() {

    private val noOfYears = 25                          // number of years to preload (should be odd number)
    private var currentViewPage = 0                     // tracks the position of the viewpager
    private var updateCalendarView = false              // update CalendarView when reach limit

    private var currentYear = 0                         // basically the current year displayed in the fragment
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_year_holder, container, false)
        viewPager = rootView.findViewById<ViewPager>(R.id.vp_yearFragment)
        currentYear = DateFormatter.getYearFromDateStr(DateFormatter.getCurrentDateString())
        setupFragment()
        return rootView
    }

    /************************************************************************************
     * Abstract functions (implemented by CalFragmentHolder)
     ************************************************************************************/

    override fun goToToday() {
        val todayDate= DateFormatter.getCurrentDateTime()
        currentYear = todayDate.year
        setupFragment()
    }

    override fun showGoToDateDialog() {
        val dialog = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.custom_date_picker, null)
        val datePicker = view.findViewById<DatePicker>(R.id.dp_calendar)
        val daySpinner = datePicker.findViewById<View>(Resources.getSystem().getIdentifier("day", "id", "android"))
        val monthSpinner = datePicker.findViewById<View>(Resources.getSystem().getIdentifier("month", "id", "android"))
        daySpinner?.visibility = View.GONE
        monthSpinner?.visibility = View.GONE

        // initialize with current year
        datePicker.init(currentYear, 0, 1, null)

        dialog.setView(view)
        dialog.setNegativeButton("Cancel", null)
        dialog.setPositiveButton("Ok") { _ : DialogInterface, _ : Int ->
            currentYear = datePicker.year
            setupFragment()
        }
        dialog.show()
    }

    override fun updateActionTitle(displayDate: String) {
        (activity as CalendarActivity).updateActionBarTitle(displayDate)
    }

    /************************************************************************************
     * Functions for fragments inside ViewPager
     ************************************************************************************/

    // function to set up fragments inside viewPager (Each ViewPage is a Year Fragment)
    private fun setupFragment() {
        // Initialize Year
        var years = getYearsRange(currentYear)
        currentViewPage = years.size / 2                    // reason: current year is stored in middle of array

        // Reset View Pager
        viewPager.clearOnPageChangeListeners()

        // Initalize ViewPager
        val yearViewPagerAdapter = CalYearViewPagerAdapter(activity!!.supportFragmentManager, years)
        viewPager.adapter = yearViewPagerAdapter
        viewPager.currentItem = currentViewPage
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Refresh the viewPager when the user swiped till the edge
                if (state == ViewPager.SCROLL_STATE_IDLE && updateCalendarView) {
                    // update variables
                    currentYear = years[currentViewPage]                // get current year in view
                    years = getYearsRange(currentYear)                  // get year range with current year in middle
                    currentViewPage = years.size / 2
                    updateCalendarView = false

                    // Update adapter
                    yearViewPagerAdapter.updateYearsArray(years)

                    // Update Pager
                    viewPager.setCurrentItem(currentViewPage, false)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) {
                currentViewPage = position
                currentYear = years[position]
                if (position <= 0 || position >= noOfYears) {
                    updateCalendarView = true
                }
                // Update Action Bar
                updateActionTitle(currentYear.toString())
            }
        })

        // Update Action Bar
        updateActionTitle(currentYear.toString())
    }

    // function to get Year Range
    private fun getYearsRange(year: Int, limit: Int = noOfYears) : List<Int> {
        val years = ArrayList<Int>(limit)
        for (i in (-limit / 2)..(limit/2)) {
            val newYear = year + i
            years.add(newYear)
        }
        return years
    }
}
