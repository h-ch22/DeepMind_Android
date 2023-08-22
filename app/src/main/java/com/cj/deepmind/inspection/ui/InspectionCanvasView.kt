package com.cj.deepmind.inspection.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cj.deepmind.inspection.models.DrawMode
import com.cj.deepmind.inspection.models.InspectionDrawingViewModel
import com.cj.deepmind.inspection.models.MotionEvent
import com.cj.deepmind.inspection.models.PathProperties
import com.cj.deepmind.inspection.models.dragMotionEvent
import com.cj.deepmind.ui.theme.white
import com.google.firebase.annotations.PreviewApi
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@Composable
fun InspectionCanvasView(viewModel: InspectionDrawingViewModel){
    val paths = remember {
        mutableStateListOf<Pair<Path, PathProperties>>()
    }

    val pathsUndone = remember{
        mutableStateListOf<Pair<Path, PathProperties>>()
    }

    var currentPosition by remember{
        mutableStateOf(Offset.Unspecified)
    }

    var previousPosition by remember{
        mutableStateOf(Offset.Unspecified)
    }

    Column {
        val drawModifier = Modifier
            .padding(8.dp)
            .shadow(1.dp)
            .fillMaxWidth()
            .weight(1f)
            .background(white)
            .dragMotionEvent(
                onDragStart = {
                    viewModel.updateMotionEvent(MotionEvent.DOWN)
                    currentPosition = it.position
                    it.consumeDownChange()
                },

                onDrag = {
                    viewModel.updateMotionEvent(MotionEvent.MOVE)
                    currentPosition = it.position

                    if (viewModel.getDrawMode() == DrawMode.TOUCH) {
                        val change = it.positionChange()

                        paths.forEach {
                            val path: Path = it.first
                            path.translate(change)
                        }

                        viewModel.currentPath.translate(change)
                    }

                    it.consumePositionChange()
                },

                onDragEnd = {
                    viewModel.updateMotionEvent(MotionEvent.UP)
                    it.consumeDownChange()
                }
            )

        Canvas(modifier = drawModifier){
            if(viewModel.requestToClear.value){
                paths.clear()
                pathsUndone.clear()
                currentPosition = Offset.Unspecified
                previousPosition = Offset.Unspecified

                viewModel.updateClearState(false)
            }

            when(viewModel.getMotionEvent()){
                MotionEvent.DOWN -> {
                    if(viewModel.getDrawMode() != DrawMode.TOUCH){
                        viewModel.currentPath.moveTo(currentPosition.x, currentPosition.y)
                    }

                    previousPosition = currentPosition
                }

                MotionEvent.MOVE -> {
                    if(viewModel.getDrawMode() != DrawMode.TOUCH){
                        viewModel.currentPath.quadraticBezierTo(
                            previousPosition.x,
                            previousPosition.y,
                            (previousPosition.x + previousPosition.x) / 2,
                            (previousPosition.y + currentPosition.y) / 2
                        )
                    }

                    previousPosition = currentPosition
                }

                MotionEvent.UP -> {
                    if(viewModel.getDrawMode() != DrawMode.TOUCH){
                        viewModel.currentPath.lineTo(currentPosition.x, currentPosition.y)
                        paths.add(Pair(viewModel.currentPath, viewModel.getCurrentPathProperty()))

                        viewModel.currentPath = Path()

                        viewModel.updateCurrentPathProperties( PathProperties(isErase = viewModel.getCurrentPathProperty().isErase))
                    }

                    pathsUndone.clear()

                    currentPosition = Offset.Unspecified
                    previousPosition = currentPosition
                    viewModel.updateMotionEvent(MotionEvent.IDLE)
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
                                width = viewModel.getCurrentPathProperty().strokeWidth,
                                cap = viewModel.getCurrentPathProperty().strokeCap,
                                join = viewModel.getCurrentPathProperty().strokeJoin
                            ),
                            blendMode = BlendMode.Clear
                        )
                    }
                }

                if(viewModel.getMotionEvent() != MotionEvent.IDLE){
                    if(!viewModel.getCurrentPathProperty().isErase){
                        drawPath(
                            color = viewModel.getCurrentPathProperty().color,
                            path = viewModel.currentPath,
                            style = Stroke(
                                width = viewModel.getCurrentPathProperty().strokeWidth,
                                cap = viewModel.getCurrentPathProperty().strokeCap,
                                join = viewModel.getCurrentPathProperty().strokeJoin
                            )
                        )
                    } else{
                        drawPath(
                            color = Color.Transparent,
                            path = viewModel.currentPath,
                            style = Stroke(
                                width = viewModel.getCurrentPathProperty().strokeWidth,
                                cap = viewModel.getCurrentPathProperty().strokeCap,
                                join = viewModel.getCurrentPathProperty().strokeJoin
                            ),
                            blendMode = BlendMode.Clear
                        )
                    }
                }

                restoreToCount(checkPoint)
            }
        }
    }
}

@Preview
@Composable
fun InspectionCanvasView_previews(){
    InspectionCanvasView(viewModel = InspectionDrawingViewModel())
}