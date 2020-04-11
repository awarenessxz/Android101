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

// JvmOverloads annotation to indicate the presence of multiple constructor
class CalMonthView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val ROW_COUNT = 6                               // number of rows in calendar month view
    private var paint: Paint
    private var gridPaint: Paint
    private var cellWidth = 0f                              // width of each individual blocks
    private var cellHeight = 0f                             // height of each individual blocks
    private var daysLetterInWeek = ArrayList<String>()      // [M T W T F S S]
    private var dayLetterHeight = 0                         // space assigned for drawing [M T W T F S S] on canvas

    private var isSundayFirst = false                       // Sunday or Monday first
    private var showGrid = false                            // show grids
    private var daysToDisplay = ArrayList<CalendarDate>()   // days in the month to display

    // initialize variables
    init {
        val normal_text_size = resources.getDimensionPixelSize(R.dimen.normal_text_size)
        dayLetterHeight = normal_text_size * 2

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = ContextCompat.getColor(context, R.color.colorCalendarText)
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = normal_text_size.toFloat()
        gridPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gridPaint.color = ContextCompat.getColor(context, R.color.colorCalendarGridLines)
    }

    // function for updating the view (Note: this happens after init)
    fun updateMonthView(daysToDisplay: ArrayList<CalendarDate>, isSundayFirst: Boolean, showGrid: Boolean = false) {
        this.daysToDisplay = daysToDisplay
        this.isSundayFirst = isSundayFirst
        this.showGrid = showGrid
        setDayLetterHeader()
    }

    // identify the current day of thw week index (for highlighting the [M T W T F S S] header)
    private fun setDayLetterHeader() {
        if (isSundayFirst) {
            daysLetterInWeek = arrayListOf("S", "M", "T", "W", "T", "F", "S")
        } else {
            daysLetterInWeek = arrayListOf("M", "T", "W", "T", "F", "S", "S")
        }
    }

    /************************** Canvas Related *******************************/

    // core logic of custom view is here [START HERE]
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            measureCanvas(canvas)       // auto adjust width and height of components
            displayWeekHeader(canvas)   // add [M T W T F S S] to the calendar month view
            displayDates(canvas)        // add the dates number to the calendar month view
            drawGrid(canvas)            // draw grid for calendar month view        }
        }
    }

    // measure the height and width of canvas to adjust the components accordingly
    private fun measureCanvas(canvas: Canvas) {
        cellWidth = canvas.width / 7f
        cellHeight = (canvas.height - dayLetterHeight) / ROW_COUNT.toFloat()
    }

    // draw the grids
    private fun drawGrid(canvas: Canvas) {
        if (showGrid) {
            // vertical lines
            for (i in 0..ROW_COUNT) {
                var lineX = i * cellWidth
                canvas.drawLine(lineX, 0f, lineX, canvas.height.toFloat(), gridPaint)
            }

            // horizontal lines
            // canvas.drawLine(0f, 0f, canvas.width.toFloat(), 0f, gridPaint)      // top line
            for (i in 0..ROW_COUNT-1) {
                canvas.drawLine(0f, i * cellHeight + dayLetterHeight, canvas.width.toFloat(), i * cellHeight + dayLetterHeight, gridPaint)
            }
        } else {
            // draw below week headers
            canvas.drawLine(0f, dayLetterHeight.toFloat(), canvas.width.toFloat(), dayLetterHeight.toFloat(), gridPaint)
        }
    }

    // display the week headers in the calendar view
    private fun displayWeekHeader(canvas: Canvas) {
        for (i in 0..ROW_COUNT) {
            // calculate center position of cell block
            val xPos = (i + 1) * cellWidth - cellWidth / 2

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

            // set y position
            val yPos = dayLetterHeight * 0.7f

            // draw onto canvass
            canvas.drawText(daysLetterInWeek[i], xPos, yPos, weekDayLetterPaint)
        }
    }

    // display the dates in the calendar view
    private fun displayDates(canvas: Canvas) {
        var counter = 0
        for (y in 0 until ROW_COUNT) {
            for (x in 0 .. ROW_COUNT) {
                val xPos = x * cellWidth
                val yPos = y * cellHeight + dayLetterHeight
                val xPosCenter = xPos + cellWidth / 2

                val cellPaint = Paint(paint)
                if (daysToDisplay[counter].isSunday) {
                    val rectPaint = Paint()
                    rectPaint.color = ContextCompat.getColor(context, R.color.colorCalendarWeekendShade)
                    canvas.drawRect(xPos, yPos, (x+1) * cellWidth, (y+1) * cellHeight + dayLetterHeight, rectPaint)
                    cellPaint.color = ContextCompat.getColor(context, R.color.colorCalendarWeekendText)
                }
                if (daysToDisplay[counter].isToday) {
                    val circlePaint = Paint()
                    circlePaint.color = ContextCompat.getColor(context, R.color.colorCalendarTodayBackground)
                    canvas.drawCircle(xPosCenter, yPos + paint.textSize * 0.7f, paint.textSize * 0.75f, circlePaint)
                    cellPaint.color = ContextCompat.getColor(context, R.color.colorCalendarTodayText)
                }
                if (!daysToDisplay[counter].isThisMonth) {
                    cellPaint.alpha = 100
                }

                val value = DateFormatter.getDayFromDateStr(daysToDisplay[counter].display)
                canvas.drawText(value.toString(), xPosCenter, yPos + paint.textSize, cellPaint)
                counter++
            }
        }
    }
}