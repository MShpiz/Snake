package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import java.lang.Integer.min

class GameView(private val context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var scoreText: TextView? = null
    private var highScoreText: TextView? = null
    private var squareSize: Int = 0
    var controller: GameController = GameController(20, context)
    var onGameOver: (() -> Unit)? = null



    var timer = object : CountDownTimer(Long.MAX_VALUE, 500) {
        override fun onTick(p0: Long) {
            if (!controller!!.gameStep()) {
                this.cancel()
                setScore()
                onGameOver?.invoke()
            }
            invalidate()
        }

        override fun onFinish() {
        }
    }

    private fun setScore() {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("snake_scores", Context.MODE_PRIVATE)
        val recordedScore = sharedPreferences.getInt("high_score", 0)
        if (recordedScore < controller.highScore) {
            sharedPreferences.edit().putInt("high_score", controller.highScore).apply()
        }
    }

    init {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("snake_scores", Context.MODE_PRIVATE)
        controller.highScore = sharedPreferences.getInt("high_score", 0)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        squareSize= min((this.right - this.left)/20, (this.bottom - this.top)/20)
        controller?.updateSize(squareSize)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawField(canvas)
        controller?.drawAll(canvas)
        scoreText?.text = controller.score.toString()
        highScoreText?.text = controller.highScore.toString()
    }

    private fun drawField(canvas: Canvas) {
        val lines = mutableListOf<Float>()
        for (i in 0..20) {
                lines.add(i*squareSize.toFloat())
                lines.add(0f)
                lines.add(i*squareSize.toFloat())
                lines.add((20)*squareSize.toFloat())

                lines.add(0f)
                lines.add(i*squareSize.toFloat())
                lines.add((20)*squareSize.toFloat())
                lines.add(i*squareSize.toFloat())

        }

        val paint = Paint()
        paint.setARGB(0xff, 53, 180, 226)
        canvas.drawLines(lines.toFloatArray(), paint)
    }

    fun changeDirection(dir: Direction) {
        controller.changeDirection(dir)
    }

    fun setTexts(scoreText: TextView?, highScoreText: TextView?) {
        this.scoreText = scoreText
        this.highScoreText = highScoreText
    }

    fun reset() {
        controller.reset()
        timer.cancel()
        timer.start()
    }

    fun pause() {
        timer.cancel()
        controller.pause()
    }

    fun resume() {
        timer.start()
    }


    fun saveState(outState: Bundle) {
        controller.saveAll(outState)
    }

}