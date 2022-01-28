package com.example.l5z1

import android.content.Context

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet), SurfaceHolder.Callback {
    private val thread : GameThread
    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.setRunning(false)
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread.setRunning(true)
        thread.start()
    }

    fun update() {
        ballX+=dx
        ballY+=dy
        if (ballX <= 0 || ballX+size >= width) {
            dx = -dx
        }
        if (ballY <= 0) {
            pointsB++
            reset()
        } else if (ballY+size >= height) {
            pointsT++
            reset()
        }
        if(ballX+size/2 in (bottomRectX-size)..(bottomRectX + rectSize + size) && ballY in (bottomRectY-size)..bottomRectY && dy > 0) {
            if(ballX+size/2 < bottomRectX + rectSize/3 && dx > -15f)
                dx -= 5f
            else if(ballX+size/2 > bottomRectX + 2*rectSize/3 && dx < 15f)
                dx += 5f
            dy = -dy*1.05f
        }
        else if(ballX+size/2 in (topRectX-size)..(topRectX + rectSize + size) && ballY in topRectY..(topRectY+size) && dy < 0) {
            if(ballX+size/2 < topRectX + rectSize/3 && dx > -15f)
                dx -= 5f
            else if(ballX+size/2 > topRectX + 2*rectSize/3 && dx < 15f)
                dx += 5f
            dy = -dy*1.05f
        }

        if(gameType == 1) {
            if(ballX < topRectX - size)
                topRectX -=botSpeed
            else if (ballX + 2*size > topRectX + rectSize)
                topRectX +=botSpeed
        }
        if (topRectX <= 0)
            topRectX = 2f
        if (topRectX+ rectSize >= width)
            topRectX = width.toFloat() - rectSize - 2f
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return

        val white = Paint()
        white.setARGB(255,255,255,255)
        canvas.drawOval(RectF(ballX, ballY,ballX+size,ballY+size), white)
        canvas.drawRect(RectF(bottomRectX, bottomRectY, bottomRectX+rectSize, bottomRectY+ rectSize/8), white)
        canvas.drawRect(RectF(topRectX, topRectY, topRectX+rectSize, topRectY+ rectSize/8), white)

        val textStyle = Paint()
        textStyle.setARGB(255,255,255,255)
        textStyle.textSize = (height/20).toFloat()
        canvas.drawText(pointsB.toString(), width*0.9f, height*0.6f, textStyle)
        canvas.drawText(pointsT.toString(), width*0.9f, height*0.4f, textStyle)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount

        val getPointer1 = event.getPointerId(0)
        val pointer1 = event.findPointerIndex(getPointer1)
        val x1 = event.getX(pointer1)
        val y1 = event.getY(pointer1)

        if(y1 > height/2)
            bottomRectX = x1- (rectSize/2)
        else if (gameType == 2)
            topRectX = x1 - (rectSize/2)

        if(pointerCount > 1 && gameType == 2) {
            val getPointer2 = event.getPointerId(1)
            val pointer2 = event.findPointerIndex(getPointer2)
            val x2 = event.getX(pointer2)
            val y2 = event.getY(pointer2)
            if(y2 > height/2)
                bottomRectX = x2- (rectSize/2)
            else topRectX = x2 - (rectSize/2)
        }

        if (bottomRectX <= 0)
            bottomRectX = 2f
        if (bottomRectX+ rectSize >= width)
            bottomRectX = width.toFloat() - rectSize - 2f
        if (topRectX <= 0)
            topRectX = 2f
        if (topRectX+ rectSize >= width)
            topRectX = width.toFloat() - rectSize - 2f
        return true
    }

    private fun reset() {
        ballY = (height/2).toFloat()
        ballX = (width/2).toFloat()
        dx = 0f
        dy = 15f
        bottomRectX = 300f
        topRectX = 300f
    }
}