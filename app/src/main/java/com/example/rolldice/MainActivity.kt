package com.example.rolldice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var diceImageView: ImageView
    private lateinit var resultTextView: TextView
    private val handler = Handler(Looper.getMainHandler())
    private var isLongPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceImageView = findViewById(R.id.diceImageView)
        resultTextView = findViewById(R.id.resultTextView)

        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                // 單擊：1D6
                val roll = Random.nextInt(1, 7)
                resultTextView.text = "劉宥男單擊擲骰結果：$roll 點"
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                // 雙擊：2D12
                val r1 = Random.nextInt(1, 13)
                val r2 = Random.nextInt(1, 13)
                val sum = r1 + r2
                resultTextView.text = "劉宥男雙擊擲骰結果：$sum 點 ($r1, $r2)"
                return true
            }
        })

        // 長按檢測計時器
        var longPressRunnable = Runnable {
            isLongPressed = true
            // 長按：3D20
            val r1 = Random.nextInt(1, 21)
            val r2 = Random.nextInt(1, 21)
            val r3 = Random.nextInt(1, 21)
            val sum = r1 + r2 + r3
            resultTextView.text = "劉宥男長按擲骰結果：$sum 點 ($r1, $r2, $r3)"
        }

        diceImageView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isLongPressed = false
                    handler.postDelayed(longPressRunnable, 600)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacks(longPressRunnable)
                }
            }
            true
        }
    }
}
