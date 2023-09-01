package com.cj.deepmind.inspection.models

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput

suspend fun AwaitPointerEventScope.awaitDragMotionEvent(
    onDragStart: (PointerInputChange) -> Unit = {},
    onDrag: (PointerInputChange) -> Unit = {},
    onDragEnd: (PointerInputChange) -> Unit = {}
){
    val down: PointerInputChange = awaitFirstDown()
    onDragStart(down)

    var pointer = down

    val change: PointerInputChange? =
        awaitTouchSlopOrCancellation(down.id){change: PointerInputChange, over: Offset ->
            change.consumePositionChange()
        }

        if (change != null){
            drag(change.id){pointerInputChange: PointerInputChange ->
                pointer = pointerInputChange
                onDrag(pointer)
            }

            onDragEnd(pointer)
        } else{
            onDragEnd(pointer)
        }
}

fun Modifier.dragMotionEvent(
    onDragStart: (PointerInputChange) -> Unit = {},
    onDrag: (PointerInputChange) -> Unit = {},
    onDragEnd: (PointerInputChange) -> Unit = {}
) = this.then(
    Modifier.pointerInput(Unit){
        forEachGesture{
            awaitPointerEventScope{
                awaitDragMotionEvent(onDragStart, onDrag, onDragEnd)
            }
        }
    }
)