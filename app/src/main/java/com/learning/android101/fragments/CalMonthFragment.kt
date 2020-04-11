package com.learning.android101.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.learning.android101.Constants
import com.learning.android101.MyPreferences
import com.learning.android101.R
import com.learning.android101.activities.CalendarActivity
import com.learning.android101.helpers.DateFormatter
import com.learning.android101.models.CalendarDate
import com.learning.android101.views.CalMonthView
import kotlin.collections.ArrayList
import org.joda.time.DateTime

/**
 * Calendar Month Fragment
 */
class CalMonthFragment : Fragment() {

    private val numOfDays = 42                                      // 7 days a week * 6 rows
    private var isSundayFirst = MyPreferences.getIsSundayFirst()    // is Sunday or Monday First (GET FROM PREFERNECES NEXT TIME)
    private var dateStr = ""
    private lateinit var rootLayout : RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_month, container, false)
        rootLayout = rootView.findViewById<RelativeLayout>(R.id.month_view_holder)

        // extract value from bundle
        dateStr = arguments?.getString(Constants.ARG_DATE)!!

        // initialize components
        initTopNavigation()
        initMonthViewWrapper()

        return rootView
    }

    // initialize the navigation bar
    private fun initTopNavigation() {
        // initialize left button
        val leftArrow = rootLayout.findViewById<ImageView>(R.id.top_left_arrow)
        leftArrow.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager>(R.id.vp_monthFragment)
            viewPager?.let {
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }

        // initialize right button
        val rightArrow = rootLayout.findViewById<ImageView>(R.id.top_right_arrow)
        rightArrow.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager>(R.id.vp_monthFragment)
            viewPager?.let {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }

        // initialize value
        val navValue = rootLayout.findViewById<TextView>(R.id.top_value)
        navValue.text = DateFormatter.getMonthYearFromDateStr(dateStr)
        navValue.setOnClickListener {
            (activity as CalendarActivity).showGoToDateDialog()
        }
    }

    // initialize Month View Wrapper (inside rootLayout)
    private fun initMonthViewWrapper() {
        val monthViewWrapper = rootLayout.findViewById<CalMonthView>(R.id.month_view_wrapper)

        // get days in month
        val daysToDisplay = getDaysInMonth()

        // pass variables to month view
        monthViewWrapper.updateMonthView(daysToDisplay, isSundayFirst)
    }

    // get all the days in the month
    private fun getDaysInMonth() : ArrayList<CalendarDate> {
        val datetime = DateFormatter.getDateTimeObjFromDateStr(dateStr)
        val daysToDisplay = ArrayList<CalendarDate>(numOfDays)
        val numOfDaysInCurMonth = datetime.dayOfMonth().maximumValue            // get number of days in current month
        val today = datetime.dayOfMonth                                         // get DD from DD-MM-YYYY
        val firstDayOfCurMonth = datetime.withDayOfMonth(1)                // get first day of month (datetime)
        val weekIndex = firstDayOfCurMonth.dayOfWeek                            // get week index (Monday - Sunday: 1 - 7)
        val startIndexOfCurMonth = if (isSundayFirst) { weekIndex % 7 } else { weekIndex - 1 }
        val endIndexOfCurMonth = startIndexOfCurMonth + numOfDaysInCurMonth - 1
        val startDateToDisplay = datetime.plusDays(-(today-1+startIndexOfCurMonth))

        for (i in 0..numOfDays) {
            val displayDate = startDateToDisplay.plusDays(i)
            val isToDay = DateFormatter.isDateTimeToday(displayDate)
            val isSunday = DateFormatter.isDateTimeSunday(displayDate)
            var isCurrentMonth = true
            if (i < startIndexOfCurMonth || i > endIndexOfCurMonth) {
                isCurrentMonth = false
            }
            val display = DateFormatter.getDateStringFromDateTimeObj(displayDate)

            val calDateObj = CalendarDate(display, displayDate, isCurrentMonth, isSunday, isToDay)
            daysToDisplay.add(calDateObj)
        }

        return daysToDisplay
    }
}
