package com.learning.android101.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.learning.android101.Constants
import com.learning.android101.MyPreferences

import com.learning.android101.R
import com.learning.android101.activities.CalendarActivity
import com.learning.android101.adapters.CalWeekViewPagerAdapter
import com.learning.android101.helpers.DateFormatter
import com.learning.android101.interfaces.SyncScrollListener
import com.learning.android101.views.SyncScrollView
import org.joda.time.DateTime
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

// Calendar Week that implements CalFragmentHolder
class CalWeekFragmentHolder : CalFragmentHolder(), SyncScrollListener {

    private var isSundayFirst = MyPreferences.getIsSundayFirst()  // is Sunday or Monday First (GET FROM PREFERNECES NEXT TIME)
    private val noOfWeeks = 151                                   // number of weeks to preload (should be odd number)
    private var currentViewPage = 0                               // tracks the position of the viewpager
    private var updateCalendarView = false                        // update CalendarView when reach limit
    private var scrollYPos = 0                                    // controls the scoller position

    private lateinit var currentDateStr: String                   // basically the current Date that is displaying in fragment
    private lateinit var viewPager: ViewPager
    private lateinit var outerScrollView: SyncScrollView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_week_holder, container, false)
        viewPager = rootView.findViewById<ViewPager>(R.id.vp_weekFragment)
        outerScrollView = rootView.findViewById<SyncScrollView>(R.id.sv_vertical_outer)
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
        val (startMonth, startYear, startWeekNum) = DateFormatter.getMonthYearWeekFromDateStr(displayDate, isSundayFirst)
        val (endMonth, endYear, endWeekNum) = DateFormatter.getMonthYearWeekFromDateStr(DateFormatter.addXDaysToDateStr(displayDate, 6), isSundayFirst)
        if (startMonth == endMonth) {
            (activity as CalendarActivity).updateActionBarTitle("$startMonth $startYear - Week $startWeekNum")
        } else {
            (activity as CalendarActivity).updateActionBarTitle("$startMonth / $endMonth $endYear - Week $endWeekNum")
        }
    }

    /************************************************************************************
     * Functions for fragments inside ViewPager
     ************************************************************************************/

    private fun setupFragment() {
        // Initialize weeks
        var firstDateOfWeek = DateFormatter.getDateTimeForFirstDayOfWeek(currentDateStr, isSundayFirst)
        var weeks = getWeeksRange(firstDateOfWeek)
        currentViewPage = weeks.size / 2                           // reason: current Date is stored in middle of array

        // Reset View Pager
        viewPager.clearOnPageChangeListeners()

        // Initalize View Pager
        val weekViewPagerAdapter = CalWeekViewPagerAdapter(activity!!.supportFragmentManager, weeks, this)
        viewPager.adapter = weekViewPagerAdapter
        viewPager.currentItem = currentViewPage
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Refresh the viewPager when the user swiped till the edge
                if (state == ViewPager.SCROLL_STATE_IDLE && updateCalendarView) {
                    // update variables
                    firstDateOfWeek = DateFormatter.getDateTimeObjFromDateStr(weeks[currentViewPage])   // get current week in view
                    weeks = getWeeksRange(firstDateOfWeek)                                              // get week range with current month in middle
                    currentViewPage = weeks.size / 2
                    updateCalendarView = false

                    // Update adapter
                    weekViewPagerAdapter.updateWeeksArray(weeks)

                    // Update Pager
                    viewPager.setCurrentItem(currentViewPage, false)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) {
                currentViewPage = position
                currentDateStr = weeks[position]
                if (position <= 0 || position >= noOfWeeks) {
                    updateCalendarView = true
                }
                // Update Action Bar
                updateActionTitle(currentDateStr)
            }
        })

        // initialize Scroll View (Outer -- Hours)
        initHoursScrollBar(weekViewPagerAdapter)

        // Update Action Bar
        updateActionTitle(DateFormatter.getDateStringFromDateTimeObj(firstDateOfWeek))
    }

    // function to get weeks range (prevWeek - currentWeek - nextWeek) based on first day of week
    private fun getWeeksRange(datetime: DateTime, limit: Int = noOfWeeks): List<String> {
        val weeks = ArrayList<String>(noOfWeeks)
        for (i in (-limit / 2)..(limit/2)) {
            val newDate = datetime.plusDays(i*7)
            val newDateStr = DateFormatter.getDateStringFromDateTimeObj(newDate)
            weeks.add(newDateStr)
        }
        return weeks
    }

    /************************************************************************************
     * Sync Scrollbar functions
     ************************************************************************************/

    // function to init Scroll Bar (Hours)
    private fun initHoursScrollBar(weekViewPagerAdapter: CalWeekViewPagerAdapter) {
        // init layout
        val hoursWrapper = outerScrollView.findViewById<LinearLayout>(R.id.week_view_hours_wrapper)
        hoursWrapper.removeAllViews()

        // add hours view into layout
        val datetime = DateFormatter.getCurrentDateTime().withTime(0, 0, 0, 0)
        for (i in 1..23) {
            val hour = datetime.plusHours(i)
            val hourTextView = layoutInflater.inflate(R.layout.custom_hours_view, null, false) as TextView
            hourTextView.text = DateFormatter.getHoursIn12HoursFormat(hour)
            hourTextView.setTextColor(ContextCompat.getColor(context!!, R.color.colorCalendarText))
            hoursWrapper.addView(hourTextView)
        }

        // Add Listener
        outerScrollView.setOnScrollviewListener(object: SyncScrollView.ScrollViewListener {
            override fun onScrollChanged(scrollView: SyncScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                scrollYPos = y
                weekViewPagerAdapter.updateScrollY(currentViewPage, y)
            }

        })
    }

    override fun scrollTo(y: Int) {
        outerScrollView.scrollY = y
        scrollYPos = y
    }

    override fun getScrollYPos(): Int {
        return scrollYPos
    }
}
