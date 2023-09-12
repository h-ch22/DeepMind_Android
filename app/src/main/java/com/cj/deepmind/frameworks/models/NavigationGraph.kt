package com.cj.deepmind.frameworks.models

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cj.deepmind.community.ui.CommunityListView
import com.cj.deepmind.diary.ui.DiaryView
import com.cj.deepmind.history.ui.HistoryView
import com.cj.deepmind.home.ui.HomeView
import com.cj.deepmind.inspection.ui.InspectionDrawingView
import com.cj.deepmind.inspection.ui.InspectionMainView
import com.cj.deepmind.inspection.ui.onInspectionView
import com.cj.deepmind.more.ui.MoreView

@Composable
fun NavigationGraph(navController : NavHostController){
    NavHost(navController = navController, startDestination = BottomNavigationItem.Home.screenRoute){
        composable(BottomNavigationItem.Home.screenRoute){
            HomeView()
        }

        composable(BottomNavigationItem.Map.screenRoute){
            HomeView()
        }

        composable(BottomNavigationItem.ManageConsulting.screenRoute){
            HomeView()
        }

        composable(BottomNavigationItem.Community.screenRoute){
            CommunityListView()
        }

        composable(BottomNavigationItem.More.screenRoute){
            MoreView()
        }

        composable(BottomNavigationItem.inspectionDrawingView.screenRoute){
            InspectionDrawingView()
        }
    }
}