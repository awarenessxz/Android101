package com.learning.android101.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.learning.android101.R
import com.learning.android101.models.CalendarDate

class CalWeekView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val ROWS_COUNT = 24
    private val COLS_COUNT = 7
    private var gridPaint: Paint

    private var isCellSunday = ArrayList<Boolean>()

    // initialize variables
    init {
        gridPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gridPaint.color = ContextCompat.getColor(context, R.color.colorCalendarGridLines)
    }

    fun updateWeekView(isCellSunday: ArrayList<Boolean>) {
        this.isCellSunday = isCellSunday
    }

    /************************** Canvas Related *******************************/

    // core logic of custom view is here [START HERE]
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            shadeWeekendCells(canvas)    // shade weekend cells
            drawGrid(canvas)             // draw grid
        }
    }

    private fun drawGrid(canvas: Canvas) {
        val height = canvas.height.toFloat()
        val width = canvas.width.toFloat()

        val rowHeight = height / ROWS_COUNT.toFloat()
        for (i in 0 until ROWS_COUNT) {
            val y = rowHeight * i.toFloat()
            canvas.drawLine(0f, y, width, y, gridPaint)
        }

        val rowWidth = width / COLS_COUNT.toFloat()
        for (i in 0 until COLS_COUNT) {
            val x = rowWidth * i.toFloat()
            canvas.drawLine(x, 0f, x, height, gridPaint)
        }
    }

    // shade the weekend cells
    private fun shadeWeekendCells(canvas: Canvas) {
        val cellWidth = (canvas.width / COLS_COUNT).toFloat()
        val cellHeight = (canvas.height / ROWS_COUNT).toFloat()
        val cellPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        cellPaint.color = ContextCompat.getColor(context, R.color.colorCalendarWeekendShade)

        for (i in 0 until COLS_COUNT) {
            for (j in 0 until ROWS_COUNT) {
                // check if column is sunday
                if (isCellSunday[i]) {
                    canvas.drawRect(i * cellWidth, j * cellHeight, (i+1) * cellWidth, (j+1) * cellHeight, cellPaint)
                }
            }
        }
    }
}