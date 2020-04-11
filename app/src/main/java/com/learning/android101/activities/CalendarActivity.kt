package com.learning.android101.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.learning.android101.Constants
import com.learning.android101.R
import com.learning.android101.fragments.*
import com.learning.android101.helpers.DateFormatter
import kotlinx.android.synthetic.main.activity_calendar.*

class CalendarActivity : AppCompatActivity() {

    private var current_fragments = ArrayList<CalFragmentHolder>()   // stack of fragments
    private var calendarView = Constants.DAILY_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Display Calendar View
        updateCalendarView(DateFormatter.getCurrentDateString())
    }

    /************************************************************************************
     * functions related to options Menu
     ************************************************************************************/

    // Menu Bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.calendar_menu, menu)
        return true
    }

    // Menu Bar OnClickListener
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.go_to_today -> goToToday()
            R.id.change_view -> showChangeCalenderViewDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    // function to show dialog to change calender view
    private fun showChangeCalenderViewDialog() {
        val items = this.resources.getStringArray(R.array.calenderViews)

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Select Calender View")
        dialog.setSingleChoiceItems(R.array.calenderViews, calendarView, { inter : DialogInterface, which : Int ->
            val selected = items[which]
            calendarView = which
            updateCalendarView(DateFormatter.getCurrentDateString())
            inter.dismiss()
        })
        dialog.show()
    }

    /************************************************************************************
     * functions to manipulate fragments holders
     ************************************************************************************/

    // function to choose the fragments (Monthly / Day / Week / Year holder).
    private fun updateCalendarView(dateStr: String) {
        val fragmentHolder = getFragmentHolder()

        // Empty current fragments
        current_fragments.forEach {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        current_fragments.clear()

        // add fragment holder
        current_fragments.add(fragmentHolder)
        val bundle = Bundle()
        bundle.putString(Constants.ARG_DATE, dateStr)    // add date to load (mainly for month / week / day views)
        fragmentHolder.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.fl_calfragmentHolder, fragmentHolder).commitNow()
    }

    // Initialize Fragment Holder for the different views
    private fun getFragmentHolder() = when (calendarView) {
        Constants.YEARLY_VIEW -> CalYearFragmentHolder()
        Constants.MONTHLY_VIEW -> CalMonthFragmentHolder()
        Constants.WEEKLY_VIEW -> CalWeekFragmentHolder()
        Constants.DAILY_VIEW -> CalDayFragmentHolder()
        else -> { CalMonthFragmentHolder() }
    }

    /************************************************************************************
     * Helper functions
     ************************************************************************************/

    // function to open Month View from Year View
    fun openMonthViewFromYearView(dateToOpen: String) {
        // Get Month Fragment Holder
        calendarView = Constants.MONTHLY_VIEW
        updateCalendarView(dateToOpen)
    }

    /************************************************************************************
     * Abstract functions (intended for CalFragmentHolder)
     ************************************************************************************/

    // function to go the date
    fun showGoToDateDialog() {
        current_fragments.last().showGoToDateDialog()
    }

    // function to go to today
    fun goToToday() {
        current_fragments.last().goToToday()
    }

    // function to update action bar title
    fun updateActionBarTitle(newTitle: String) {
        supportActionBar?.setTitle(newTitle)
    }
}