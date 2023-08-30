package com.cj.deepmind.frameworks.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.ui.graphics.vector.ImageVector

const val HOME = "HOME"
const val MAP = "MAP"
const val MANAGE_CONSULTING = "MANAGE_CONSULTING"
const val INSPECTION = "INSPECTION"
const val COMMUNITY = "COMMUNITY"
const val MORE = "MORE"
const val DRAWING_VIEW = "DRAWING_VIEW"

sealed class BottomNavigationItem(
    val title : String, val icon : ImageVector, val screenRoute : String
){
    object Home : BottomNavigationItem(
        "홈", Icons.Default.Home, HOME
    )

    object Map: BottomNavigationItem(
        "병원 찾기 및 예약", Icons.Filled.Map, MAP
    )

    object ManageConsulting: BottomNavigationItem(
        "상담 관리", Icons.Filled.CalendarMonth, MANAGE_CONSULTING
    )

    object Inspection : BottomNavigationItem(
        "검사", Icons.Default.Edit, INSPECTION
    )

    object Community: BottomNavigationItem(
        "커뮤니티", Icons.Default.SupervisedUserCircle, COMMUNITY
    )

    object More : BottomNavigationItem(
        "더 보기", Icons.Default.MoreHoriz, MORE
    )

    object inspectionDrawingView: BottomNavigationItem(
        "HTP 검사", Icons.Default.Edit, DRAWING_VIEW
    )
}