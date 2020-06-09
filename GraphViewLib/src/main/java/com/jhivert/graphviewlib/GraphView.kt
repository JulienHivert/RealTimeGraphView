package com.jhivert.graphviewlib

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class GraphView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val firstDataSet = mutableListOf<DataPoint>()
    private val secondDataSet = mutableListOf<DataPoint>()
    private val stroke: Float = 4f

    private var yMax: Double = 0.0
    private val mPath: Path = Path()
    private var colorGraphOne = Paint()
    private var colorGraphSecond = Paint()

    init {
        initAttributes(context, attributeSet)
    }

    private fun initAttributes(
        context: Context,
        attributeSet: AttributeSet?
    ) {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.GraphView)
         colorGraphOne = Paint().apply {
            color = a.getColor(R.styleable.GraphView_graphColor_1, 0)
            strokeWidth = a.getFloat(R.styleable.GraphView_stroke, 4.0f)
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
         colorGraphSecond = Paint().apply {
            color = a.getColor(R.styleable.GraphView_graphColor_2, 0)
            strokeWidth = stroke
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        a.recycle()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        firstGraph(canvas)
        secondGraph(canvas)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun firstGraph(canvas: Canvas?) {
        mPath.reset()
        firstDataSet.forEachIndexed { index, currentDataPoint ->
            if (index < firstDataSet.size) {
                if (index == 0 ) {
                    mPath.moveTo(currentDataPoint.progress.toCoordX(), currentDataPoint.value.toCoordY() + (stroke / 2))
                } else {
                    mPath.lineTo(currentDataPoint.progress.toCoordX(), currentDataPoint.value.toCoordY() + (stroke / 2))
                }
                canvas?.drawPath(mPath, colorGraphOne)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun secondGraph(canvas: Canvas?) {
        mPath.reset()
        secondDataSet.forEachIndexed { index, currentDataPoint ->
            if (index < secondDataSet.size - 1) {
                if(index ==  0) {
                    mPath.moveTo(currentDataPoint.progress.toCoordX(), currentDataPoint.value.toCoordY() + (stroke / 2))
                } else {
                    mPath.lineTo(currentDataPoint.progress.toCoordX(), currentDataPoint.value.toCoordY() + (stroke / 2))
                }
                canvas?.drawPath(mPath, colorGraphSecond)
            }
        }
    }

    /**
     * Search the Ymax and use it for draw the graph
     * @param  dataPoint is an object containing the value and the progress send by user
     *
     */
    fun drawFirstGraph(dataPoint: DataPoint) {
        // Search the Ymax for redraw the graph
        if (dataPoint.value > yMax) {
            yMax = dataPoint.value
        }
        firstDataSet.add(DataPoint(dataPoint.progress, dataPoint.value))
        invalidate()
    }

    /**
     * Search the Ymax and use it for draw the graph
     * @param  dataPoint is an object containing the value and the progress send by user
     *
     */
    fun drawSecondGraph(dataPoint: DataPoint) {
        if (dataPoint.value > yMax) {
            yMax = dataPoint.value
        }
        secondDataSet.add(DataPoint(dataPoint.progress, dataPoint.value))
        invalidate()
    }

    // measuredWidth && measuredHeight are in pixel
    private fun Double.toCoordX(): Float {
        return (toFloat() / 100) * measuredWidth
    }
    /**
    draw the graph inside the view cleanly
    We add some padding inside
    The graph will generated from left to right
    this function ONLY applies to a double and returns a float
     */

    private fun Double.toCoordY(): Float {
        return (measuredHeight - (stroke / 2) ) - (toFloat() / (yMax.toFloat()) * (measuredHeight - (stroke / 2 )))
    }
}