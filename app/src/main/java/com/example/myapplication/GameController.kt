package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle

class GameController(val fieldSize: Int, context: Context?) {


    private val snakeController: SnakeController = SnakeController()
    private val apple = Apple()
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

    fun updateSize(squareSize: Int) {
        snakeController.updateSize(squareSize)
        apple.size = squareSize
    }

    fun drawAll(canvas: Canvas) {
        apple.draw(canvas)
        snakeController.drawSnake(canvas)
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

    fun restoreSnake(integerArrayList: ArrayList<Int>?) {
        snakeController.reset()
        snakeController.makeSnake(integerArrayList)
    }

    fun restoreApple(integerArrayList: ArrayList<Int>?) {
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
            savedInstanceState.putInt("score", score)
            savedInstanceState.putIntegerArrayList("snake_positions", snakeController.saveSnake())
            savedInstanceState.putIntegerArrayList("apple", saveApple())
        }
    }
}