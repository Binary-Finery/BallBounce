package com.spencer_studios.ballbounce

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.random.Random

class AnimView(context: Context, attr: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context, attr) {

    var gravity = 0.2f
    var friction = 0.89f

    var balls = ArrayList<Ball>()
    var numOfBalls = 10
    var isFirstDraw = true

    override fun onDraw(canv: Canvas) {
        super.onDraw(canv)

        if (isFirstDraw) {
            addBalls()
            isFirstDraw = false
        }

        balls.indices.forEach {
            val ball = balls[it]
            when {
                ball.y + ball.radius + ball.dy > height -> {
                    ball.dy *= -1
                    ball.dy *= friction
                }
                else -> ball.dy += gravity
            }

            when {
                ball.x + ball.radius > width || ball.x - ball.radius < 0 -> {
                    ball.dx = -ball.dx
                }
            }
        }

        balls.indices.forEach {
            val ball = balls[it]
            ball.y += ball.dy
            ball.x += ball.dx
        }

        balls.indices.forEach {
            val ball = balls[it]
            canv.drawCircle(ball.x, ball.y, ball.radius, ball.paint)
        }

        invalidate()
    }

    private fun rdm(min: Float, max: Float): Float {
        return (min.toInt()..max.toInt()).random().toFloat()
    }

    private fun addBalls() {
        for (i in 0 until numOfBalls) {
            val rad = rdm(width / 50f, width / 25f)
            balls.add(
                Ball(
                    rdm(rad, width - rad),
                    rdm(0f, height / 2f),
                    3f,
                    if (Random.nextBoolean()) -2f else 2f,
                    rad,
                    Paint().apply {
                        isAntiAlias = true
                        strokeWidth = width / 50f
                        style = Paint.Style.STROKE
                        color = rdmColor()
                    })
            )
        }
    }

    private fun rdmColor(): Int {
        return Color.argb(200, (0..200).random(), (0..200).random(), (0..200).random())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            balls.clear()
            isFirstDraw = true
        }
        return true
    }
}