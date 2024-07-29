package com.example.myapplication

import android.graphics.Canvas

abstract class Square: Comparable<Square> {
    var x: Int = 0
    var y: Int = 0
    var size = 0

    abstract fun draw(canvas: Canvas)

    override fun compareTo(other: Square): Int {
        return if (x == other.x && y == other.y) return 0 else return 1
    }

}