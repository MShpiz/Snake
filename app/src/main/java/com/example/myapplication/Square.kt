package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Paint

abstract class Square: Comparable<Square> {
    protected val paint: Paint = Paint()

    var x: Int = 0
    var y: Int = 0

    fun draw(canvas: Canvas, size: Int, offsetX: Int = 0, offsetY: Int = 0) {
        canvas.drawRect(
            x.toFloat()*size + offsetX,
            y.toFloat()*size + offsetY,
            (x + 1)*size.toFloat() + offsetX,
            (y + 1)*size.toFloat() + offsetY,
            paint
        )
    }

    override fun compareTo(other: Square): Int {
        return if (x == other.x && y == other.y) return 0 else return 1
    }

}