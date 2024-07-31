package com.example.myapplication

import android.graphics.Canvas

class SnakeController() {

    private val snake = mutableListOf<SnakePiece>(SnakePiece())

    private var dir: Direction = Direction.stop

    fun changeDirection(dir: Direction) {
        this.dir = dir
    }

    fun updatePosition(): Boolean{
        if (dir == Direction.stop) return checkSelfCollision()

        for (i in snake.size-1 downTo 1) {
            snake[i].x = snake[i-1].x
            snake[i].y = snake[i-1].y
        }
        when(dir) {
            Direction.up -> {
                snake[0].y -= 1
            }
            Direction.down -> {
                snake[0].y += 1
            }
            Direction.left -> {
                snake[0].x -= 1
            }
            Direction.right -> {
                snake[0].x += 1
            }
            Direction.stop -> {
            }
        }

        snake[0].x = (snake[0].x + 20)%20
        snake[0].y = (snake[0].y + 20)%20

        return checkSelfCollision()
    }

    fun drawSnake(canvas: Canvas, squareSize: Int, fieldOffsetX: Int, fieldOffsetY: Int) {
        for (elem in snake) {
            elem.draw(canvas, squareSize, fieldOffsetX, fieldOffsetY)
        }
    }

    private fun checkSelfCollision():Boolean {
        val res = snake.indexOfLast { it.compareTo(snake[0]) == 0 } != 0
        return res
    }

    fun checkApple(apple: Apple): Boolean {
        return snake[0].compareTo(apple) == 0
    }

    fun elongate() {
        snake.add(SnakePiece())
        snake.last().x = snake[snake.size-2].x
        snake.last().y = snake[snake.size-2].y
    }

    fun reset() {
        snake.clear()
        dir = Direction.stop
        snake.add(SnakePiece())
    }

    fun makeSnake(pos: ArrayList<Int>?) {
        if (pos == null) {
            reset()
        } else {
            snake.clear()
            for (i in 0..<pos.size step 2) {
                snake.add(SnakePiece())
                snake.last().x = pos[i]
                snake.last().y = pos[i + 1]
            }
        }
    }

    fun saveSnake():ArrayList<Int> {
        val pos = ArrayList<Int>()
        for (i in snake) {
            pos.add(i.x)
            pos.add(i.y)
        }
        return pos
    }

}
