package com.cj.deepmind.inspection.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.inspection.models.DrawMode
import com.cj.deepmind.inspection.models.InspectionTypeModel
import com.cj.deepmind.inspection.models.MotionEvent
import com.cj.deepmind.inspection.models.PathProperties
import com.cj.deepmind.inspection.models.dragMotionEvent
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.white
import java.text.DecimalFormat
import java.util.Timer
import kotlin.concurrent.timer

fun getDescriptionByCode(code: Int) : String?{
    InspectionTypeModel.values().forEach {
        if(it.getCode() == code){
            return it.getDescription()
        }
    }

    return null
}

@Composable
fun InspectionDrawingView(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val currentIndex = remember {
        mutableStateOf(0)
    }

    val paths = remember {
        mutableStateListOf<Pair<Path, PathProperties>>()
    }

    val pathsUndone = remember{
        mutableStateListOf<Pair<Path, PathProperties>>()
    }

    var motionEvent by remember {
        mutableStateOf(
            MotionEvent.IDLE
        )
    }

    var currentPosition by remember{
        mutableStateOf(Offset.Unspecified)
    }

    var previousPosition by remember{
        mutableStateOf(Offset.Unspecified)
    }

    var drawMode by remember{
        mutableStateOf(DrawMode.DRAW)
    }

    var currentPath by remember{
        mutableStateOf(Path())
    }

    var currentPathProperty by remember{
        mutableStateOf(PathProperties())
    }

    var elapsedTime by remember{
        mutableStateOf(0)
    }

    var timerTask: Timer? = null
    val decimalFormat = DecimalFormat("00")

    DeepMindTheme {
        LaunchedEffect(key1 = true){
            timerTask = kotlin.concurrent.timer(period = 1){
                elapsedTime += 1
            }
        }

        NavHost(navController = navController, startDestination = "InspectionDrawingView" ) {
            composable(route = "onInspectionView") {

            }

            composable(route = "InspectionDrawingView"){
                Surface(modifier = Modifier.fillMaxSize(), color = white){
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        getDescriptionByCode(currentIndex.value)?.let { description ->
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${description}을 그려주세요.")

                                if(currentIndex.value == 3){
                                    Text(text = "이전과 반대 성별로 그려주세요.", fontSize = 12.sp, color = gray)
                                }
                            }
                        }

                        val drawModifier = Modifier
                            .padding(8.dp)
                            .shadow(1.dp)
                            .fillMaxWidth()
                            .weight(1f)
                            .background(white)
                            .dragMotionEvent(
                                onDragStart = {
                                    motionEvent = MotionEvent.DOWN
                                    currentPosition = it.position
                                    it.consumeDownChange()
                                },

                                onDrag = {
                                    motionEvent = MotionEvent.MOVE
                                    currentPosition = it.position

                                    if (drawMode == DrawMode.TOUCH) {
                                        val change = it.positionChange()

                                        paths.forEach {
                                            val path: Path = it.first
                                            path.translate(change)
                                        }

                                        currentPath.translate(change)
                                    }

                                    it.consumePositionChange()
                                },

                                onDragEnd = {
                                    motionEvent = MotionEvent.UP
                                    it.consumeDownChange()
                                }
                            )

                        Canvas(modifier = drawModifier){
                            when(motionEvent){
                                MotionEvent.DOWN -> {
                                    if(drawMode != DrawMode.TOUCH){
                                        currentPath.moveTo(currentPosition.x, currentPosition.y)
                                    }

                                    previousPosition = currentPosition
                                }

                                MotionEvent.MOVE -> {
                                    if(drawMode != DrawMode.TOUCH){
                                        currentPath.quadraticBezierTo(
                                            previousPosition.x,
                                            previousPosition.y,
                                            (previousPosition.x + previousPosition.x) / 2,
                                            (previousPosition.y + currentPosition.y) / 2
                                        )
                                    }

                                    previousPosition = currentPosition
                                }

                                MotionEvent.UP -> {
                                    if(drawMode != DrawMode.TOUCH){
                                        currentPath.lineTo(currentPosition.x, currentPosition.y)
                                        paths.add(Pair(currentPath, currentPathProperty))

                                        currentPath = Path()

                                        currentPathProperty = PathProperties(isErase = currentPathProperty.isErase)
                                    }

                                    pathsUndone.clear()

                                    currentPosition = Offset.Unspecified
                                    previousPosition = currentPosition
                                    motionEvent = MotionEvent.IDLE
                                }

                                else -> Unit
                            }

                            with(drawContext.canvas.nativeCanvas){
                                val checkPoint = saveLayer(null, null)

                                paths.forEach{
                                    val path = it.first
                                    val property = it.second

                                    if(!property.isErase){
                                        drawPath(
                                            color = property.color,
                                            path = path,
                                            style = Stroke(
                                                width = property.strokeWidth,
                                                cap = property.strokeCap,
                                                join = property.strokeJoin
                                            )
                                        )
                                    } else{
                                        drawPath(
                                            color = Color.Transparent,
                                            path = path,
                                            style = Stroke(
                                                width = currentPathProperty.strokeWidth,
                                                cap = currentPathProperty.strokeCap,
                                                join = currentPathProperty.strokeJoin
                                            ),
                                            blendMode = BlendMode.Clear
                                        )
                                    }
                                }

                                if(motionEvent != MotionEvent.IDLE){
                                    if(!currentPathProperty.isErase){
                                        drawPath(
                                            color = currentPathProperty.color,
                                            path = currentPath,
                                            style = Stroke(
                                                width = currentPathProperty.strokeWidth,
                                                cap = currentPathProperty.strokeCap,
                                                join = currentPathProperty.strokeJoin
                                            )
                                        )
                                    } else{
                                        drawPath(
                                            color = Color.Transparent,
                                            path = currentPath,
                                            style = Stroke(
                                                width = currentPathProperty.strokeWidth,
                                                cap = currentPathProperty.strokeCap,
                                                join = currentPathProperty.strokeJoin
                                            ),
                                            blendMode = BlendMode.Clear
                                        )
                                    }
                                }

                                restoreToCount(checkPoint)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${decimalFormat.format(elapsedTime / 1000 / 60)}:${decimalFormat.format(elapsedTime / 1000 % 60)}", color = accent)

                            Spacer(modifier = Modifier.weight(1f))

                            DrawingPropertiesMenu(
                                modifier = Modifier
                                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                                    .shadow(1.dp, RoundedCornerShape(8.dp))
                                    .background(DeepMindColorPalette.current.btnColor)
                                    .padding(4.dp),
                                pathProperties = currentPathProperty,
                                drawMode = drawMode,
                                onPathPropertiesChange = {
                                    motionEvent = MotionEvent.IDLE
                                },
                                onDrawModeChange = {
                                    motionEvent = MotionEvent.IDLE
                                    drawMode = it
                                    currentPathProperty.isErase = (drawMode == DrawMode.ERASE)
                                }
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(onClick = {
                                elapsedTime = 0

                                if(currentIndex.value < 3){
                                    currentIndex.value += 1

                                } else{

                                }
                            }) {
                                Text(text = if(currentIndex.value < 3) "다음" else "검사 종료")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InspectionDrawingView_previews(){
    InspectionDrawingView()
}