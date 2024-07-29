package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class Apple(): Square() {
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.setARGB(0xff, 0xe3, 0x25, 0x6b)
        canvas.drawRect(x.toFloat()*size,
            y.toFloat()*size,
            x*size + size.toFloat(),
            y*size + size.toFloat(),
            paint)
    }
}