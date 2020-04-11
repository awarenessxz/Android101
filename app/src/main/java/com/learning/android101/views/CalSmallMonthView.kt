package com.learning.android101.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.learning.android101.R
import com.learning.android101.helpers.DateFormatter
import com.learning.android101.models.CalendarDate

class CalSmallMonthView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val ROW_COUNT = 6                               // number of rows in calendar month view
    private var paint: Paint
    private var cellWidth = 0f                              // width of each individual block
    private var cellHeight = 0f                             // height of each individual block
    private var daysLetterInWeek = ArrayList<String>()      // [M T W T F S S]
    private var dayLetterHeight = 0f                        // space assigned for drawing [M T W T F S S] on canvas

    private var numOfDays = 31                              // number of days in the month
    private var daysToDisplay = ArrayList<CalendarDate>()   // days in the month to display
    private var isSundayFirst = true                        // Sunday or Monday first
    private var firstDayOfMonthIndex = 0                    // first day index of the month to display on view

    init {
        val yearTextSize = resources.getDimensionPixelSize(R.dimen.year_text_size)
        dayLetterHeight = yearTextSize * 1.2f

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = yearTextSize.toFloat()
        paint.textAlign = Paint.Align.RIGHT
        paint.color = ContextCompat.getColor(context, R.color.colorCalendarText)
    }

    // function for updating the view (Note: this happens after init)
    fun updateSmallMonthView(daysToDisplay: ArrayList<CalendarDate>, isSundayFirst: Boolean, daysLetterInWeek: ArrayList<String>, firstDayOfMonthIndex: Int) {
        this.daysToDisplay = daysToDisplay
        this.numOfDays = daysToDisplay.size
        this.isSundayFirst = isSundayFirst
        this.firstDayOfMonthIndex = firstDayOfMonthIndex
        this.daysLetterInWeek = daysLetterInWeek
    }

    /************************** Canvas Related *******************************/

    // core logic of custom view is here [START HERE]
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            measureCanvas(canvas)           // auto adjust width and height of components
            displayWeekHeader(canvas)       // add [M T W T F S S] to the calendar month view
            displayDates(canvas)            // add the dates number to the calendar month view
        }
    }

    // measure the width of canvas to adjust the components accordingly
    private fun measureCanvas(canvas: Canvas) {
        cellWidth = canvas.width / 7f
        cellHeight = (canvas.height - dayLetterHeight) / ROW_COUNT.toFloat()
    }

    // display the week headers in the calendar view
    private fun displayWeekHeader(canvas: Canvas) {
        for (i in 0..ROW_COUNT) {
            // calculate center position of cell block
            val xPos = (i + 1) * cellWidth

            // highlight the weekends
            val weekDayLetterPaint = Paint(paint)
            if (isSundayFirst) {
                if (i == 0) {
                    weekDayLetterPaint.color = ContextCompat.getColor(context, R.color.colorCalendarWeekendText)
                }
            } else {
                if (i == 6) {
                    weekDayLetterPaint.color = ContextCompat.getColor(context, R.color.colorCalendarWeekendText)
                }
            }

            // set text size
            val yPos = dayLetterHeight * 0.7f

            // draw onto canvass
            canvas.drawText(daysLetterInWeek[i], xPos, yPos, weekDayLetterPaint)
        }
    }

    // display the dates in the calendar view
    private fun displayDates(canvas: Canvas) {

        var ptr = 1 - firstDayOfMonthIndex
        for (y in 1..ROW_COUNT) {
            for (x in 1..7) {
                if (ptr in 1..numOfDays) {
                    val xPos = x * cellWidth
                    val yPos = y * cellHeight + dayLetterHeight

                    val cellPaint = Paint(paint)
                    if (daysToDisplay[ptr-1].isSunday) {
                        cellPaint.color = ContextCompat.getColor(context, R.color.colorCalendarWeekendText)
                    }
                    if (daysToDisplay[ptr-1].isToday) {
                        val circlePaint = Paint()
                        circlePaint.color = ContextCompat.getColor(context, R.color.colorCalendarTodayBackground)
                        canvas.drawCircle(xPos - cellWidth / 3, yPos - cellHeight / 4, cellWidth * 0.5f, circlePaint)
                        cellPaint.color = ContextCompat.getColor(context, R.color.colorCalendarTodayText)
                    }

                    canvas.drawText(ptr.toString(), xPos, yPos, cellPaint)
                }
                ptr++
            }
        }
    }
}