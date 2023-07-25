package com.cj.deepmind.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme

@Composable
fun HomeView(){
    val navController = rememberNavController()

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "HomeView") {
            composable("HomeView") {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeView_previews(){
    HomeView()
}