package com.cj.deepmind.frameworks.ui

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
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val activity = (LocalContext.current as? StartActivity)

            DeepMindTheme {
                NavHost(navController = navController, startDestination = "Splash"){
                    composable(route="Splash"){
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = DeepMindColorPalette.current.background
                        ) {
                            splash(navController = navController, viewModel = viewModel)
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

@Composable
fun splash(navController: NavController, viewModel: SplashViewModel){
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        if(!viewModel.isSignedIn.value){
            navController.navigate("SignInView"){
                popUpTo("Splash"){
                    inclusive = true
                }
            }
        } else{
            //TODO : Navigate to MainActivity
        }
    }

    Column(modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_deepmind),
            contentDescription = null,
            modifier = Modifier
                .width(180.dp)
                .height(180.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = true
                )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "DeepMind",
            modifier = Modifier,
            color = DeepMindColorPalette.current.txtColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        CircularProgressIndicator(modifier = Modifier,
            color = accent)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DeepMindTheme {
        splash(navController = NavController(LocalContext.current), viewModel = SplashViewModel())
    }
}