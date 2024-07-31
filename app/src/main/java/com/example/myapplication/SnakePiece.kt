package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class SnakePiece(): Square() {
    override fun draw(canvas: Canvas, size: Int, offsetX: Int, offsetY: Int) {
        canvas.drawRect(
            x.toFloat()*size + offsetX,
            y.toFloat()*size + offsetY,
            (x + 1)*size.toFloat() + offsetX,
            (y + 1)*size.toFloat() + offsetY,
            Paint()
        )
    }
}