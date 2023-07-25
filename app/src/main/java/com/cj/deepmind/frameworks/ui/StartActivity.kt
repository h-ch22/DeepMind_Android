package com.cj.deepmind.frameworks.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.R
import com.cj.deepmind.frameworks.models.SplashViewModel
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.userManagement.ui.SignInView

class StartActivity : ComponentActivity() {
    private val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            DeepMindTheme {
                NavHost(navController = navController, startDestination = "Splash"){
                    composable(route="Splash"){
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = DeepMindColorPalette.current.background
                        ) {
                            SplashView(navController = navController, viewModel = viewModel)
                        }
                    }

                    composable(route = "SignInView"){
                        BackHandler {
                            finishAffinity()
                        }

                        SignInView()
                    }
                }
            }
        }
    }
}