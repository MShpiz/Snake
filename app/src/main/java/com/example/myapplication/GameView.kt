package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import java.lang.Integer.min

class GameView(private val context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var scoreText: TextView? = null
    private var highScoreText: TextView? = null

    private var squareSize: Int = 0
    private var fieldOffsetX: Int = 0
    private var fieldOffsetY: Int = 0

    private val SUPER_STATE = "superstate"

    private val sharedPrefFile = "snake_scores"
    private val highScoreKey = "high_score"

    var controller: GameController = GameController(20)
    var onGameOver: (() -> Unit)? = null


    var timer = object : CountDownTimer(Long.MAX_VALUE, 500) {
        override fun onTick(p0: Long) {
            if (!controller.gameStep()) {
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
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val recordedScore = sharedPreferences.getInt(highScoreKey, 0)
        if (recordedScore < controller.highScore) {
            sharedPreferences.edit().putInt(highScoreKey, controller.highScore).apply()
        }
    }

    init {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        controller.highScore = sharedPreferences.getInt(highScoreKey, 0)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        squareSize= min(this.width/20, this.height/20)
        fieldOffsetX = (this.width - squareSize*20)/2
        fieldOffsetY = (this.height - squareSize*20)/2
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawField(canvas)
        controller.drawAll(canvas, squareSize, fieldOffsetX, fieldOffsetY)
        scoreText?.text = controller.score.toString()
        highScoreText?.text = controller.highScore.toString()
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val bundle = Bundle()
        bundle.putParcelable(SUPER_STATE, superState)
        controller.saveAll(bundle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state == null) return
        val bundle = state as Bundle
        super.onRestoreInstanceState(bundle.getParcelable(SUPER_STATE))
        controller.restoreAll(bundle)

    }

    private fun drawField(canvas: Canvas) {
        val lines = mutableListOf<Float>()
        for (i in 0..20) {
            // horizontal line
            lines.add(i*squareSize.toFloat() + fieldOffsetX) // x start
            lines.add(0f + fieldOffsetY) // y start
            lines.add(i*squareSize.toFloat() + fieldOffsetX) // x end
            lines.add((20)*squareSize.toFloat() + fieldOffsetY) // y end

            // vertical line
            lines.add(0f + fieldOffsetX) // x start
            lines.add(i*squareSize.toFloat() + fieldOffsetY) // y start
            lines.add((20)*squareSize.toFloat() + fieldOffsetX) // x end
            lines.add(i*squareSize.toFloat() + fieldOffsetY) // y end

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

}