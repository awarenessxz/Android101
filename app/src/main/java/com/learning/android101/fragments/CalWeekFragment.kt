package com.learning.android101.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.learning.android101.Constants
import com.learning.android101.MyPreferences

import com.learning.android101.R
import com.learning.android101.helpers.DateFormatter
import com.learning.android101.interfaces.SyncScrollListener
import com.learning.android101.views.CalWeekView
import com.learning.android101.views.SyncScrollView

/**
 * Calendar Week Fragment
 */
class CalWeekFragment : Fragment() {

    private var isSundayFirst = MyPreferences.getIsSundayFirst()    // is Sunday or Monday First (GET FROM PREFERNECES NEXT TIME)
    private var daysLetterInWeek = ArrayList<String>()              // [M T W T F S S]
    private var dateStr = ""
    private var clickStartTime = 0L                                 // cell grid
    private val CLICK_DURATION_THRESHOLD = 1500L                    // cell grid
    private var selectedCell: ImageView? = null                          // selected cell to add event

    private lateinit var rootLayout : RelativeLayout
    private lateinit var innerScrollView: SyncScrollView

    // variables for sync scroll view
    var outerSyncScrollListener : SyncScrollListener? = null        // gets listener from CalWeekFragmentHolder
    private var minScrollY = -1
    private var maxScrollY = -1
    private var cellHeight = 0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cal_week, container, false)
        rootLayout = rootView.findViewById<RelativeLayout>(R.id.week_view_holder)
        innerScrollView = rootLayout.findViewById<SyncScrollView>(R.id.sv_vertical_inner)

        // extract value from bundle
        dateStr = arguments?.getString(Constants.ARG_DATE)!!

        // initialize components
        initWeekViewHeader()
        initWeekViewWrapper()

        // initialize Scroll View (inner -- Week Grid)
        initWeekGridScroll()

        return rootView
    }

    /************************************************************************************
     * functions for week header
     ************************************************************************************/

    // init the top header
    private fun initWeekViewHeader() {
        if (isSundayFirst) {
            daysLetterInWeek = arrayListOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        } else {
            daysLetterInWeek = arrayListOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        }

        var curDatetime = DateFormatter.getDateTimeObjFromDateStr(dateStr)
        var textColor : Int
        for (i in 0..6) {
            // Init Display Text
            val day = curDatetime.dayOfMonth
            val dayLetter = daysLetterInWeek[i]
            val displayLabel = "$dayLetter $day"

            if (DateFormatter.isDateTimeSunday(curDatetime)) {
                textColor = ContextCompat.getColor(context!!, R.color.colorCalendarWeekendText)
            } else {
                textColor = ContextCompat.getColor(context!!, R.color.colorCalendarText)
            }

            // Get & Set TextView
            val textLabel = rootLayout.findViewById<TextView>(resources.getIdentifier("week_label_$i", "id", context!!.packageName))
            textLabel.text = displayLabel
            textLabel.setTextColor(textColor)

            if (DateFormatter.isDateTimeToday(curDatetime)) {
                textLabel.setBackgroundResource(R.drawable.rectangle)
                textLabel.setTextColor(ContextCompat.getColor(context!!, R.color.colorCalendarTodayText))
            }

            // Increment Date
            curDatetime = curDatetime.plusDays(1)
        }
    }

    /************************************************************************************
     * functions for week grid
     ************************************************************************************/

    // init data inside grids
    @SuppressLint("ClickableViewAccessibility")
    private fun initWeekViewWrapper() {
        cellHeight = resources.getDimension(R.dimen.week_view_cell_height)
        minScrollY = (cellHeight * 7).toInt()
        maxScrollY = (cellHeight * 23).toInt()

        // compute weekend (Update Week Grid View)
        val isCellSunday = ArrayList<Boolean>()
        var curDatetime = DateFormatter.getDateTimeObjFromDateStr(dateStr)
        for (i in 0..6) {
            // Init Display Text
            val day = curDatetime.dayOfMonth
            val isSunday = DateFormatter.isDateTimeSunday(curDatetime)
            isCellSunday.add(isSunday)

            // Increment Date
            curDatetime = curDatetime.plusDays(1)
        }
        val weekView = innerScrollView.findViewById<CalWeekView>(R.id.week_view)
        weekView.updateWeekView(isCellSunday)

        // set on click listener
        for (i in 0..6) {
            val column = innerScrollView.findViewById<RelativeLayout>(resources.getIdentifier("week_column_$i", "id", context!!.packageName))
            column.removeAllViews()
            column.setOnTouchListener(object: View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    handleGridCellTouch(event!!, i, column)
                    return true
                }
            })
        }
    }

    @SuppressLint("NewApi")
    private fun handleGridCellTouch(event: MotionEvent, index: Int, view: ViewGroup) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clickStartTime = System.currentTimeMillis()
            }
            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis() - clickStartTime < CLICK_DURATION_THRESHOLD) {
                    selectedCell?.animation?.cancel()
                    selectedCell?.visibility = View.GONE

                    val hour = (event.y / cellHeight).toInt()
                    selectedCell = layoutInflater.inflate(R.layout.custom_week_grid_item, null, false) as ImageView
                    view.addView(selectedCell)
                    selectedCell?.let {
                        it.layoutParams?.let {
                            // https://stackoverflow.com/questions/13995665/unexpected-layoutparams-with-an-inflated-linearlayout
                            it.width = view.width
                            it.height = cellHeight.toInt()
                        }
                        it.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorCalendarWeekGridCells))
                        it.setColorFilter(ContextCompat.getColor(context!!, R.color.colorWhite))
                        it.y = hour * cellHeight
                        it.setOnClickListener {
                            Toast.makeText(context, "Index: $index, Hour: $hour got clicked! Add Events", Toast.LENGTH_SHORT).show()
                        }
                        it.animate().setStartDelay(3000).alpha(0f).withEndAction(object: Runnable {
                            override fun run() {
                                it.visibility = View.GONE
                            }
                        })
                    }
                }
            }
        }
    }

    /************************************************************************************
     * Sync Scrollbar functions
     ************************************************************************************/

    private fun initWeekGridScroll() {
        // Add Listener
        innerScrollView.setOnScrollviewListener(object: SyncScrollView.ScrollViewListener {
            override fun onScrollChanged(scrollView: SyncScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                outerSyncScrollListener?.scrollTo(y)
            }
        })
    }

    // function for outer scroll view to call to update inner scroll view
    fun updateScrollY(y: Int) {
        innerScrollView.scrollY = y
    }
}
