package com.cj.deepmind.frameworks.models

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cj.deepmind.diary.ui.DiaryView
import com.cj.deepmind.history.ui.HistoryView
import com.cj.deepmind.home.ui.HomeView
import com.cj.deepmind.inspection.ui.InspectionMainView
import com.cj.deepmind.more.ui.MoreView

@Composable
fun NavigationGraph(navController : NavHostController){
    NavHost(navController = navController, startDestination = BottomNavigationItem.Home.screenRoute){
        composable(BottomNavigationItem.Home.screenRoute){
            HomeView()
        }

        composable(BottomNavigationItem.Diary.screenRoute){
            DiaryView()
        }

        composable(BottomNavigationItem.Inspection.screenRoute){
            InspectionMainView()
        }

        composable(BottomNavigationItem.History.screenRoute){
            HistoryView()
        }

        composable(BottomNavigationItem.More.screenRoute){
            MoreView()
        }
    }
}