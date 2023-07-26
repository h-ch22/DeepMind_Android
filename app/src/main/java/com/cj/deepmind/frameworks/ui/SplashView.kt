package com.cj.deepmind.frameworks.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cj.deepmind.R
import com.cj.deepmind.frameworks.models.SplashViewModel
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.userManagement.helper.UserManagement

@Composable
fun SplashView(navController: NavController, viewModel: SplashViewModel){
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.checkSignInStatus()

        if(!viewModel.isSignedIn.value){
            navController.navigate("SignInView"){
                popUpTo("Splash"){
                    inclusive = true
                }
            }
        } else{
            val helper = UserManagement()
            helper.getUserInfo {
                if(it){
                    context.startActivity(Intent(context, MainActivity::class.java))
                } else{
                    navController.navigate("SignInView"){
                        popUpTo("Splash"){
                            inclusive = true
                        }
                    }
                }
            }
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
            color = accent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashView_previews() {
    DeepMindTheme {
        SplashView(navController = NavController(LocalContext.current), viewModel = SplashViewModel())
    }
}