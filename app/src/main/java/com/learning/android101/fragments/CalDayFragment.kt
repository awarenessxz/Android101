package com.learning.android101.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.learning.android101.Constants

import com.learning.android101.R
import com.learning.android101.activities.CalendarActivity
import com.learning.android101.helpers.DateFormatter
import com.learning.android101.interfaces.CalNavigationListener

/**
 * Calendar Day Fragment
 */
class CalDayFragment : Fragment() {

    private var dateStr = ""
    var navListener : CalNavigationListener? = null                 // listener for top navigation bar
    private lateinit var rootLayout : RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_day, container, false)
        rootLayout = rootView.findViewById<RelativeLayout>(R.id.day_view_holder)

        // extract value from bundle
        dateStr = arguments?.getString(Constants.ARG_DATE)!!

        // Init Components
        initTopNavigation()
        initDayViewWrapper()

        return rootView
    }

    // initialize the top navigation bar
    private fun initTopNavigation() {
        // initialize left button
        val leftArrow = rootLayout.findViewById<ImageView>(R.id.top_left_arrow)
        leftArrow.setOnClickListener {
            navListener?.swipeLeft()
        }

        // initialize right button
        val rightArrow = rootLayout.findViewById<ImageView>(R.id.top_right_arrow)
        rightArrow.setOnClickListener {
            navListener?.swipeRight()
        }

        // initialize value
        val navValue = rootLayout.findViewById<TextView>(R.id.top_value)
        navValue.text = DateFormatter.formatDateStr(dateStr)
        navValue.setOnClickListener {
            (activity as CalendarActivity).showGoToDateDialog()
        }
    }

    // initialize the recycler view
    private fun initDayViewWrapper() {

    }
}
