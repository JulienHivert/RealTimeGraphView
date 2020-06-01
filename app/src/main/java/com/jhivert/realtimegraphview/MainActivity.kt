package com.jhivert.realtimegraphview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jhivert.graphviewlib.DataPoint
import com.jhivert.graphviewlib.GraphView
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {
    private lateinit var graphView: GraphView
    private var thread: Thread? = null
    var x: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        graphView = findViewById(R.id.graphView)
        mainThread()
    }

    private fun mainThread() {
        if (thread == null) {
            thread = object : Thread() {
                override fun run() {
                    super.run()
                    while (!isInterrupted) {
                        try {
                            sleep(100)
                            drawFirstGraph()
                            drawSecondGraph()
                        } catch (e1: InterruptedException) {
                            e1.printStackTrace()
                        }
                    }
                }
            }
            thread!!.start()
        }
    }

    fun drawFirstGraph() {
        val y = generateRandomNumbers(75.0)
        graphView.drawFirstGraph(DataPoint(x, y))
    }

    private fun drawSecondGraph() {
        val y = generateRandomNumbers(100.0)
        graphView.drawSecondGraph(DataPoint(x, y))
        if (y == 100.0) {
            thread!!.interrupt()
        }
    }

    private fun generateRandomNumbers(max: Double): Double {
        x += 1
        return ThreadLocalRandom.current().nextDouble(0.0, max)
    }
}