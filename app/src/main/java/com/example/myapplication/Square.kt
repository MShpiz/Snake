package com.example.myapplication

import android.graphics.Canvas

abstract class Square: Comparable<Square> {
    var x: Int = 0
    var y: Int = 0

    abstract fun draw(canvas: Canvas, size: Int, offsetX: Int = 0, offsetY: Int = 0)

    override fun compareTo(other: Square): Int {
        return if (x == other.x && y == other.y) return 0 else return 1
    }

}