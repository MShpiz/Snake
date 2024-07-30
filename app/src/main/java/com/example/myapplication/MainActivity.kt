package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OnClickListener {

    private var gameView: GameView? = null
    private var scoreText: TextView? = null
    private var highScoreText: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        gameView = findViewById<GameView>(R.id.game_view)
        scoreText = findViewById<TextView>(R.id.score_text)
        highScoreText = findViewById<TextView>(R.id.high_score_text)
        gameView?.setTexts(scoreText, highScoreText)
        gameView?.onGameOver = this::showDialog
    }

    override fun onClick(p0: View?) {

        when (p0!!.id ) {
            R.id.btn_left -> {
                gameView?.changeDirection(Direction.left)
            }
            R.id.btn_right -> {
                gameView?.changeDirection(Direction.right)
            }
            R.id.btn_up -> {
                gameView?.changeDirection(Direction.up)
            }
            R.id.btn_down -> {
                gameView?.changeDirection(Direction.down)
            }
            R.id.reset_btn -> {
                gameView?.reset()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView?.resume()
    }

    private fun showDialog() {
        val newFragment = GameOverDialog(gameView!!::reset)
        newFragment.show(supportFragmentManager, "game")
    }


}