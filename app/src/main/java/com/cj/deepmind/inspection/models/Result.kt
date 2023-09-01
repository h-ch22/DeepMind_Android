package com.cj.deepmind.inspection.models

import android.graphics.RectF

data class Result(
    val boundingBox: RectF,
    val classId: Int,
    val score: Float,
)
