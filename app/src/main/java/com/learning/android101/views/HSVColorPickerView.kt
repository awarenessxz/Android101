package com.learning.android101.views

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

/*
 * Links:
 *      - https://github.com/jaredrummler/ColorPicker
 */
class HSVColorPickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val WHITE = ContextCompat.getColor(context, R.color.colorPickerWhite)
    private val BLACK = ContextCompat.getColor(context, R.color.colorPickerBlack)
    private val GREY = ContextCompat.getColor(context, R.color.colorPickerGrey)
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var borderPaint: Paint

    private var panelGap: Int
    private var satvalPanelHeight: Float
    private var huePanelHeight: Float
    private var alphaPanelHeight: Float
    private var border : Float
    private var pointerRadius: Float
    private var pointerWidth: Float
    private lateinit var satvalRect: RectF
    private lateinit var hueRect: RectF
    private lateinit var alphaRect: RectF

    private var selectedColor : Int
    private var alpha = 0xff
    private var hue = 360f
    private var saturation = 0f
    private var value = 0f
    var mlistener: OnColorSelectedListener? = null

    init {
        borderPaint = Paint(paint)
        borderPaint.apply {
            this.color = WHITE
        }

        panelGap = resources.getDimensionPixelSize(R.dimen.color_picker_panel_gap)
        satvalPanelHeight = resources.getDimensionPixelSize(R.dimen.color_picker_sat_val_panel_height).toFloat()
        huePanelHeight = resources.getDimensionPixelSize(R.dimen.color_picker_hue_panel_height).toFloat()
        alphaPanelHeight = resources.getDimensionPixelSize(R.dimen.color_picker_alpha_panel_height).toFloat()
        border = resources.getDimensionPixelSize(R.dimen.color_picker_panel_border).toFloat()
        pointerRadius = resources.getDimensionPixelSize(R.dimen.color_picker_pointer_radius).toFloat()
        pointerWidth = resources.getDimensionPixelSize(R.dimen.color_picker_pointer_width).toFloat()

        selectedColor = getSelectedColor()
        mlistener?.onColorChanged(selectedColor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            measureSize(canvas)
            drawSaturationValuePanel(canvas)
            drawHuePanel(canvas)
        }
    }

    /*************************** MEASURING SIZE OF PANEL *******************************/

    private fun measureSize(canvas: Canvas) {
        // measure size for saturation value panel
        measureSaturationValuePanelSpace(canvas)
        // measure size for hue panel
        measureHuePanelSpace(canvas)
    }

    // measure space for saturation and value panel minus the border
    private fun measureSaturationValuePanelSpace(canvas: Canvas) {
        val left = border
        val top = border
        val right = canvas.width - border
        val bottom = satvalPanelHeight - border
        satvalRect = RectF(left, top,  right, bottom)
    }

    // measure space for hue panel minus the border
    private fun measureHuePanelSpace(canvas: Canvas) {
        val left = border
        val top = satvalPanelHeight + panelGap + border
        val right = canvas.width.toFloat() - border
        val bottom = satvalPanelHeight + panelGap + huePanelHeight - border
        hueRect = RectF(left, top,  right, bottom)
    }

    /*************************** DRAW PANEL ***********************************************/

    private fun drawSaturationValuePanel(canvas: Canvas) {
        // draw border
        val left = satvalRect.left - border
        val top = satvalRect.top - border
        val right = satvalRect.right + border
        val bottom = satvalRect.bottom + border
        val satvalPanel = RectF(left, top, right, bottom)
        canvas.drawRect(satvalPanel, borderPaint)

        // set gradient
        val valueGradient = LinearGradient(satvalRect.left, satvalRect.top, satvalRect.left, satvalRect.bottom, WHITE, BLACK, Shader.TileMode.CLAMP)
        val rgb = Color.HSVToColor(floatArrayOf(hue, 1f, 1f))
        val saturationGradient = LinearGradient(satvalRect.left, satvalRect.top, satvalRect.right, satvalRect.top, WHITE, rgb, Shader.TileMode.CLAMP)
        val shader = ComposeShader(valueGradient, saturationGradient, PorterDuff.Mode.MULTIPLY)

        // set Paint
        val rectPaint = Paint(paint)
        rectPaint.apply {
            this.shader = shader
        }

        // Draw bitmap
        val bitmap = Bitmap.createBitmap(satvalRect.width().toInt(), satvalRect.height().toInt(), Bitmap.Config.ARGB_8888)
        val bitmapCanvas = Canvas(bitmap)
        bitmapCanvas.drawRect(satvalRect, rectPaint)
        canvas.drawBitmap(bitmap, null, satvalRect, null)

        // Draw Pointer
        val xPos = saturation * satvalRect.width() + satvalRect.left
        val yPos = (1f - value) * satvalRect.height() + satvalRect.top
        val pointerPaint = Paint(paint)
        // draw pointer border
        pointerPaint.color = BLACK
        canvas.drawCircle(xPos, yPos, pointerRadius, pointerPaint)
        // draw pointer
        pointerPaint.color = GREY
        canvas.drawCircle(xPos, yPos, pointerRadius - border, pointerPaint)
    }

    private fun drawHuePanel(canvas: Canvas) {
        // draw border
        val left = hueRect.left - border
        val top = hueRect.top - border
        val right = hueRect.right + border
        val bottom = hueRect.bottom + border
        val huePanel = RectF(left, top, right, bottom)
        canvas.drawRect(huePanel, borderPaint)

        // compute colors
        val size = (hueRect.width() + 0.5f).toInt()
        val hueColors = IntArray(size)
        var h = 360f
        for (i in 0 until hueColors.size) {
            hueColors[i] = Color.HSVToColor(floatArrayOf(h, 1f, 1f))
            h -= 360f / hueColors.size
        }

        // set Paint
        val linePaint = Paint(paint)
        paint.apply {
            this.strokeWidth = 0f
        }

        // Draw bitmap
        val bitmap = Bitmap.createBitmap(hueRect.width().toInt(), hueRect.height().toInt(), Bitmap.Config.ARGB_8888)
        val bitmapCanvas = Canvas(bitmap)
        for (i in 0 until hueColors.size) {
            linePaint.color = hueColors[i]
            bitmapCanvas.drawLine(i.toFloat(), 0f, i.toFloat(), hueRect.height(), linePaint)
        }
        canvas.drawBitmap(bitmap, null, hueRect, null)

        // Draw pointer
        val xPos = (hueRect.width() - (hue * (hueRect.width() - pointerWidth) / 360f) - hueRect.left).toInt()
        val yPos = (hueRect.top).toInt()
        val pointerPaint = Paint(paint)
        val pleft = xPos - (pointerWidth / 2)
        val ptop = hueRect.top
        val pright = xPos + (pointerWidth / 2)
        val pbottom = hueRect.bottom
        // Draw Pointer border
        pointerPaint.color = BLACK
        val rectBorder = RectF(pleft.toFloat(), ptop, pright.toFloat(), pbottom)
        canvas.drawRoundRect(rectBorder, 2f, 2f, pointerPaint)
        // Draw Pointer
        pointerPaint.color = GREY
        val rect = RectF(pleft + border, ptop + border, pright - border, pbottom - border)
        canvas.drawRoundRect(rect, 2f, 2f, pointerPaint)
    }

    /*************************** POINTER *****************************************************/

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var update = false
        when(event?.action) {
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                if (hueRect.contains(event.x, event.y)) {
                    hue = moveHuePointer(event.x)
                    update = true
                } else if (satvalRect.contains(event.x, event.y)) {
                    val results = moveSatValPointer(event.x, event.y)
                    saturation = results[0]
                    value = results[1]
                    update = true
                }
            }
            MotionEvent.ACTION_UP -> {

            }
            else -> {

            }
        }

        if (update) {
            selectedColor = getSelectedColor()
            mlistener?.onColorChanged(selectedColor)
            postInvalidateOnAnimation()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun moveHuePointer(xPos: Float) : Float {
        var x = xPos
        if (x < hueRect.left) {
            x = 0f
        } else if (x > hueRect.right) {
            x = hueRect.width()
        } else {
            x -= hueRect.left
        }
        val result = 360f - (x * 360f / hueRect.width());
        return result;
    }

    private fun moveSatValPointer(xPos: Float, yPos: Float) : FloatArray {
        var x = xPos
        var y = yPos

        if (x < satvalRect.left) {
            x = 0f
        } else if (x > satvalRect.right) {
            x = satvalRect.width()
        } else {
            x -= satvalRect.left
        }

        if (y < satvalRect.top) {
            y = 0f
        } else if (y > satvalRect.bottom) {
            y = satvalRect.height()
        } else {
            y -= satvalRect.top
        }

        val resultA = 1f / satvalRect.width() * x
        val resultB = 1f - (1f / satvalRect.height() * y)

        return floatArrayOf(resultA, resultB)
    }

    /***********************************************************************************************
     * Colors
     ***********************************************************************************************/

    private fun getSelectedColor() : Int {
        return Color.HSVToColor(floatArrayOf(hue, saturation, value))
    }

    /***********************************************************************************************
     * Listener
     ***********************************************************************************************/

    interface OnColorSelectedListener {
        fun onColorChanged(color : Int)
    }
}