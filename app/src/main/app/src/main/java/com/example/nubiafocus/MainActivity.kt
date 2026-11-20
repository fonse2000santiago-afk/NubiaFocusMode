
package com.example.nubiafocus

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    private lateinit var timerText: TextView
    private lateinit var statusText: TextView

    private var timer: CountDownTimer? = null
    private var remaining = 25 * 60 * 1000L
    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createScreen()
        updateTimer()
    }

    private fun createScreen() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(30, 40, 30, 40)
            setBackgroundColor(Color.rgb(16, 22, 34))
        }

        val title = TextView(this).apply {
            text = "FOCUS MODE"
            textSize = 26f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        statusText = TextView(this).apply {
            text = "Ready to focus?"
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(Color.LTGRAY)
            setPadding(0, 35, 0, 15)
        }

        timerText = TextView(this).apply {
            textSize = 58f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            typeface = Typeface.MONOSPACE
        }

        val start = Button(this).apply {
            text = "Start / Pause"
            setOnClickListener { startTimer() }
        }

        val reset = Button(this).apply {
            text = "Reset"
            setOnClickListener { resetTimer() }
        }

        layout.addView(title)
        layout.addView(statusText)
        layout.addView(timerText)
        layout.addView(start)
        layout.addView(reset)

        setContentView(layout)
    }

    private fun startTimer() {
        if (running) {
            timer?.cancel()
            running = false
            statusText.text = "Paused"
            return
        }

        running = true
        statusText.text = "Stay focused!"

        timer = object : CountDownTimer(remaining, 1000) {
            override fun onTick(ms: Long) {
                remaining = ms
                updateTimer()
            }

            override fun onFinish() {
                remaining = 0
                running = false
                updateTimer()
                statusText.text = "Focus session completed!"
            }
        }.start()
    }

    private fun resetTimer() {
        timer?.cancel()
        running = false
        remaining = 25 * 60 * 1000L
        statusText.text = "Ready to focus?"
        updateTimer()
    }

    private fun updateTimer() {
        val minutes = remaining / 60000
        val seconds = (remaining / 1000) % 60
        timerText.text = "%02d:%02d".format(minutes, seconds)
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
