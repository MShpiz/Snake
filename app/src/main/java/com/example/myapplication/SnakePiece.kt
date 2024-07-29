package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class SnakePiece(): Square() {
    override fun draw(canvas: Canvas) {
        canvas.drawRect(x.toFloat()*size,
            y.toFloat()*size,
            x*size + size.toFloat(),
            y*size + size.toFloat(),
            Paint())
    }
}