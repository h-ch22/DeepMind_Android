package com.cj.deepmind.inspection.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin

data class PathProperties(val strokeWidth: Float = 10f,
                    val color: Color = Color.Black,
                    val alpha: Float = 1f,
                    val strokeCap: StrokeCap = StrokeCap.Round,
                    val strokeJoin: StrokeJoin = StrokeJoin.Round,
                    var isErase: Boolean = false)