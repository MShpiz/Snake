package com.example.myapplication

import android.graphics.Canvas
import android.os.Bundle

class GameController(private val fieldSize: Int) {

    private val snakeController: SnakeController = SnakeController()
    private val apple = Apple()

    private val scoreKey = "score"
    private val snakePosKey = "snake_positions"
    private val applePosKey = "apple"

    var score = 0
    var highScore = 0

    private fun spawnApple() {
        apple.x = (0..<fieldSize).random()
        apple.y = (0..<fieldSize).random()
    }

    init {
        spawnApple()
    }

    private fun checkApples(): Boolean {
        if (snakeController.checkApple(apple)) {
            score += 10
            if (score > highScore) {
                highScore = score
            }
            snakeController.elongate()
            return true
        }
        return false
    }

    fun drawAll(canvas: Canvas, squareSize: Int, fieldOffsetX: Int, fieldOffsetY: Int) {
        apple.draw(canvas, squareSize, fieldOffsetX, fieldOffsetY)
        snakeController.drawSnake(canvas, squareSize, fieldOffsetX, fieldOffsetY)
    }

    fun gameStep(): Boolean {
        if (snakeController.updatePosition()) {
            return false
        }
        if (checkApples()) {
            spawnApple()
        }
        return true
    }

    fun changeDirection(dir: Direction) {
        snakeController.changeDirection(dir)
    }

    fun reset() {
        this.score = 0
        snakeController.reset()
        spawnApple()
    }

    fun pause() {
        changeDirection(Direction.stop)
    }

    private fun restoreSnake(integerArrayList: ArrayList<Int>?) {
        snakeController.reset()
        snakeController.makeSnake(integerArrayList)
    }

    private fun restoreApple(integerArrayList: ArrayList<Int>?) {
        apple.x = integerArrayList?.get(0) ?: 0
        apple.y = integerArrayList?.get(1) ?: 0
    }

    private fun saveApple(): ArrayList<Int> {
        val res = ArrayList<Int>()
        res.add(apple.x)
        res.add(apple.y)
        return res
    }

    fun saveAll(savedInstanceState: Bundle){
        savedInstanceState.run {
            savedInstanceState.putInt(scoreKey, score)
            savedInstanceState.putIntegerArrayList(snakePosKey, snakeController.saveSnake())
            savedInstanceState.putIntegerArrayList(applePosKey, saveApple())
        }
    }

    fun restoreAll(bundle: Bundle) {
        score = bundle.getInt(scoreKey, 0)
        restoreApple(bundle.getIntegerArrayList(applePosKey))
        restoreSnake(bundle.getIntegerArrayList(snakePosKey))
    }
}