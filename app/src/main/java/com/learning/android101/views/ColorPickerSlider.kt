package com.learning.android101.views

import android.graphics.Color
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.learning.android101.R
import kotlin.math.round

// Link:
//      - https://medium.com/@stepango/kotlin-color-picker-part-1-427983cbcd66#.ivfjdg5iv
//      - https://www.youtube.com/watch?v=5NM_mM3LtlQ
class ColorPickerSlider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val RED = ContextCompat.getColor(context, R.color.colorPickerRed)
    private val ORANGE = ContextCompat.getColor(context, R.color.colorPickerOrange)
    private val YELLOW = ContextCompat.getColor(context, R.color.colorPickerYellow)
    private val GREEN1 = ContextCompat.getColor(context, R.color.colorPickerGreen1)
    private val GREEN2 = ContextCompat.getColor(context, R.color.colorPickerGreen2)
    private val GREEN3 = ContextCompat.getColor(context, R.color.colorPickerGreen3)
    private val CYAN = ContextCompat.getColor(context, R.color.colorPickerCyan)
    private val BLUE1 = ContextCompat.getColor(context, R.color.colorPickerBlue1)
    private val BLUE2 = ContextCompat.getColor(context, R.color.colorPickerBlue2)
    private val PURPLE = ContextCompat.getColor(context, R.color.colorPickerPurple)
    private val PINK = ContextCompat.getColor(context, R.color.colorPickerPink)
    private val WHITE = ContextCompat.getColor(context, R.color.colorPickerWhite)
    private val BLACK = ContextCompat.getColor(context, R.color.colorPickerBlack)

    private var paint: Paint
    private var colors: IntArray
    private var selectedColor: Int
    private var pickPos: Float             // for sliding the picker position (%)
    private var colorBarBorder: Int
    private var pickerBorder: Int
    var mlistener : OnColorSelectedListener? = null

    init {
        pickPos = 0.5f
        colorBarBorder = resources.getDimensionPixelSize(R.dimen.color_picker_bar_border)
        pickerBorder = resources.getDimensionPixelSize(R.dimen.color_picker_circle_border)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Initalize Colors
        colors = intArrayOf(WHITE, RED, ORANGE, YELLOW, GREEN1, GREEN2, GREEN3, CYAN, BLUE1, BLUE2, PURPLE, PINK, RED, BLACK)
        selectedColor = interpolateColor()
        mlistener?.onColorChanged(selectedColor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawColorSliderBackground(canvas)
            drawColorPicker(canvas)
        }
    }

    // draw the color background
    private fun drawColorSliderBackground(canvas: Canvas) {
        // set color gradient
        val gradient = LinearGradient(0f, height.toFloat(), width.toFloat(), height.toFloat(), colors, null, Shader.TileMode.CLAMP)

        // prepare paint (color bar)
        val colorBarPaint = Paint(paint)
        colorBarPaint.apply {
            this.shader = gradient
        }
        // prepare paint (border)
        val borderPaint = Paint(paint)
        borderPaint.apply {
            this.color = WHITE
        }

        // computer rectangle position
        val rectLeft = 0f
        val rectTop = 0f
        val rectRight = canvas.width.toFloat()
        val rectBottom = canvas.height.toFloat()
        val radius = (canvas.height / 2f) - colorBarBorder

        // draw border rectangle
        val borderRect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRoundRect(borderRect, radius, radius, borderPaint)

        // draw colorbar rectangle
        val colorBarRect = RectF(rectLeft + colorBarBorder, rectTop + colorBarBorder, rectRight - colorBarBorder, rectBottom - colorBarBorder)
        canvas.drawRoundRect(colorBarRect, radius, radius, colorBarPaint)
    }

    // draw the picker
    private fun drawColorPicker(canvas: Canvas) {
        // compute
        val pickerRadius = canvas.height / 2f
        val xPosCenter = pickerRadius + pickPos * (canvas.width - pickerRadius * 2)
        val yPos = canvas.height / 2f

        // paint
        val circlePaint = Paint(paint)
        circlePaint.color = selectedColor
        val borderPaint = Paint(paint)
        borderPaint.color = WHITE

        // draw circle
        canvas.drawCircle(xPosCenter, yPos, pickerRadius, borderPaint)                        // outer ring
        canvas.drawCircle(xPosCenter, yPos, pickerRadius - pickerBorder, circlePaint)  // inner ring

        // inform listener (on first load)
        mlistener?.onColorChanged(selectedColor)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                pickPos = event.x / measuredWidth.toFloat()
                if (pickPos < 0) {
                    pickPos = 0f
                } else if (pickPos > 1) {
                    pickPos = 1f
                }
                selectedColor = interpolateColor()
                mlistener?.onColorChanged(selectedColor)
            }
            MotionEvent.ACTION_UP -> {

            }
            else -> { }
        }
        postInvalidateOnAnimation()
        return true
    }

    /***********************************************************************************************
     * Interpolating Colors
     ***********************************************************************************************/

    // Interpolate Colors
    private fun interpolateColor() : Int {
        if (pickPos <= 0) return colors[0]
        if (pickPos >= 1) return colors[colors.size - 1]

        // get the rough position of colors
        var p = pickPos * (colors.size -1)
        val idx = p.toInt()
        p -= idx                        // take fractional part

        val c0 = colors[idx]
        val c1 = colors[idx + 1]

        // Calculate each channel separately
        val a = avg(Color.alpha(c0), Color.alpha(c1), p).toInt()
        val r = avg(Color.red(c0), Color.red(c1), p).toInt()
        val g = avg(Color.green(c0), Color.green(c1), p).toInt()
        val b = avg(Color.blue(c0), Color.blue(c1), p).toInt()

        return Color.argb(a, r, g, b)
    }

    // s = start, e = end, p = scaled percentage
    private fun avg(s: Int, e:Int, p: Float) : Float {
        return s + round(p * (e - s))
    }

    /***********************************************************************************************
     * Listener
     ***********************************************************************************************/

    interface OnColorSelectedListener {
        fun onColorChanged(color : Int)
    }
}