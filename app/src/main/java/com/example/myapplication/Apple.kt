package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class Apple(): Square() {
    override fun draw(canvas: Canvas, size: Int, offsetX: Int, offsetY: Int) {
        val paint = Paint()
        paint.setARGB(0xff, 0xe3, 0x25, 0x6b)
        canvas.drawRect(
            x.toFloat()*size + offsetX,
            y.toFloat()*size + offsetY,
            (x + 1)*size.toFloat() + offsetX,
            (y + 1)*size.toFloat() + offsetY,
            paint
        )
    }
}