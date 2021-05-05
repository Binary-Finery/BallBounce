package com.spencer_studios.ballbounce

import android.graphics.Paint

data class Ball(
    var x : Float,
    var y : Float,
    var dy : Float,
    var dx : Float,
    var radius : Float,
    var paint : Paint
)