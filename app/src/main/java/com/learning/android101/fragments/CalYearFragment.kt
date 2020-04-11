package com.learning.android101.fragments


import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.learning.android101.Constants
import com.learning.android101.MyPreferences

import com.learning.android101.R
import com.learning.android101.activities.CalendarActivity
import com.learning.android101.helpers.DateFormatter
import com.learning.android101.models.CalendarDate
import com.learning.android101.views.CalSmallMonthView

/**
 * Calendar Year Fragment
 */
class CalYearFragment : Fragment() {

    private var isSundayFirst = MyPreferences.getIsSundayFirst()    // is Sunday or Monday First (GET FROM PREFERNECES NEXT TIME)
    private var year = 0
    private lateinit var rootLayout : RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_year, container, false)
        rootLayout = rootView.findViewById<RelativeLayout>(R.id.year_view_holder)

        // extract value from bundle
        year = arguments?.getInt(Constants.ARG_YEAR)!!

        // initialize components
        initYearViewWrapper()

        return rootView
    }

    // initialize the 12 months small views inside year fragment
    private fun initYearViewWrapper() {
        val yearViewWrapper = rootLayout.findViewById<TableLayout>(R.id.year_view_wrapper)

        // initialize all 12 months
        for (i in 1..12) {
            // get view
            val smallMonthView = yearViewWrapper.findViewById<CalSmallMonthView>(resources.getIdentifier("mini_month_view_$i", "id", context!!.packageName))

            // get number of days in month
            val thisMonthDateTime = DateFormatter.getDateTimeObjFromYearMonthDay(year, i, 1)
            val numOfDays = thisMonthDateTime.dayOfMonth().maximumValue

            // compute isWeekend, isToday
            val daysToDisplay = ArrayList<CalendarDate>(numOfDays)
            for (j in 0..numOfDays-1) {
                val displayDateTime = thisMonthDateTime.plusDays(j)
                val isToday = DateFormatter.isDateTimeToday(displayDateTime)
                val isSunday = DateFormatter.isDateTimeSunday(displayDateTime)
                val display = DateFormatter.getDateStringFromDateTimeObj(displayDateTime)
                val dateToDisplay = CalendarDate(display, displayDateTime, true, isSunday, isToday)
                daysToDisplay.add(dateToDisplay)
            }

            // calculate first day index of the month
            val weekIndex = thisMonthDateTime.dayOfWeek
            var firstDayIndexOfMonth: Int
            var daysLetterInWeek: ArrayList<String>
            if (isSundayFirst) {
                firstDayIndexOfMonth = weekIndex % 7
                daysLetterInWeek = arrayListOf("S", "M", "T", "W", "T", "F", "S")
            } else {
                firstDayIndexOfMonth = weekIndex - 1
                daysLetterInWeek = arrayListOf("M", "T", "W", "T", "F", "S", "S")
            }

            // set onclick listener to open up month
            smallMonthView.setOnClickListener {
                val monthViewDateStr = DateFormatter.getDateStringFromDateTimeObj(thisMonthDateTime)
                (activity as CalendarActivity).openMonthViewFromYearView(monthViewDateStr)
            }

            // check if date is current month
            val isCurrentMonth = DateFormatter.isDateTimeCurMonth(thisMonthDateTime)
            if (isCurrentMonth) {
                val dateLabel = yearViewWrapper.findViewById<TextView>(resources.getIdentifier("mini_month_label_$i", "id", context!!.packageName))
                dateLabel.setTextColor(ContextCompat.getColor((activity as CalendarActivity), R.color.colorCalendarCurrentMonthText))
                dateLabel.setTypeface(null, Typeface.BOLD)
            }

            // pass variables to small month view
            smallMonthView.updateSmallMonthView(daysToDisplay, isSundayFirst, daysLetterInWeek, firstDayIndexOfMonth)
        }
    }
}
