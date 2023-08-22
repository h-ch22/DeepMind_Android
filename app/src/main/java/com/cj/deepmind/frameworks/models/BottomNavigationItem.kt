package com.cj.deepmind.frameworks.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.ui.graphics.vector.ImageVector

const val HOME = "HOME"
const val DIARY = "DIARY"
const val INSPECTION = "INSPECTION"
const val HISTORY = "HISTORY"
const val MORE = "MORE"
const val DRAWING_VIEW = "DRAWING_VIEW"

sealed class BottomNavigationItem(
    val title : String, val icon : ImageVector, val screenRoute : String
){
    object Home : BottomNavigationItem(
        "홈", Icons.Default.Home, HOME
    )

    object Diary : BottomNavigationItem(
        "하루 일기", Icons.Default.NoteAlt, DIARY
    )

    object Inspection : BottomNavigationItem(
        "검사", Icons.Default.Edit, INSPECTION
    )

    object History : BottomNavigationItem(
        "검사 기록", Icons.Default.History, HISTORY
    )

    object More : BottomNavigationItem(
        "더 보기", Icons.Default.MoreHoriz, MORE
    )

    object inspectionDrawingView: BottomNavigationItem(
        "HTP 검사", Icons.Default.Edit, DRAWING_VIEW
    )
}