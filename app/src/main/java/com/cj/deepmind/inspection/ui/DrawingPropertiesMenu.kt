package com.cj.deepmind.inspection.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cj.deepmind.inspection.models.DrawMode
import com.cj.deepmind.inspection.models.PathProperties
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.btnColor
import com.cj.deepmind.ui.theme.gray

@Composable
fun DrawingPropertiesMenu(
    modifier: Modifier = Modifier,
    pathProperties: PathProperties,
    drawMode: DrawMode,
    onPathPropertiesChange: (PathProperties) -> Unit,
    onDrawModeChange: (DrawMode) -> Unit
){
    val properties by rememberUpdatedState(newValue = pathProperties)
    var currentDrawMode = drawMode

    Row(
        modifier = Modifier.background(DeepMindColorPalette.current.btnColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = {
            currentDrawMode = DrawMode.TOUCH
            onDrawModeChange(currentDrawMode)
        }) {
            Icon(Icons.Filled.TouchApp, contentDescription = null, tint = if(currentDrawMode == DrawMode.TOUCH) accent else gray)
        }

        IconButton(onClick = {
            currentDrawMode = DrawMode.DRAW
            onDrawModeChange(currentDrawMode)
        }){
            Icon(Icons.Filled.Edit, contentDescription = null, tint = if(currentDrawMode == DrawMode.DRAW) accent else gray)
        }

        IconButton(onClick = {
            currentDrawMode = DrawMode.ERASE
            onDrawModeChange(currentDrawMode)
        }) {
            Icon(Icons.Filled.Delete, contentDescription = null, tint = if(currentDrawMode == DrawMode.ERASE) accent else gray)
        }
    }
}