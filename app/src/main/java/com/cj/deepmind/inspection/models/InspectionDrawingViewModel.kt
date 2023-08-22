package com.cj.deepmind.inspection.models

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel

class InspectionDrawingViewModel: ViewModel() {
    private var drawMode = DrawMode.DRAW
    private var currentPathProperty = PathProperties()
    private var motionEvent = MotionEvent.IDLE

    var currentPath = Path()

    private val _requestToClear = mutableStateOf(false)
    val requestToClear: State<Boolean> = _requestToClear

    fun updateDrawMode(drawMode: DrawMode){
        this.drawMode = drawMode
    }

    fun updateCurrentPathProperties(pathProperties: PathProperties){
        this.currentPathProperty = pathProperties
    }

    fun updateMotionEvent(motionEvent: MotionEvent){
        this.motionEvent = motionEvent
    }

    fun getDrawMode() : DrawMode{
        return this.drawMode
    }

    fun getCurrentPathProperty(): PathProperties{
        return this.currentPathProperty
    }

    fun getMotionEvent() : MotionEvent{
        return this.motionEvent
    }

    fun updateClearState(state: Boolean){
        _requestToClear.value = state
    }
}