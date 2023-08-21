package com.cj.deepmind.more.ui

import android.content.pm.PackageInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Rectangle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.R
import com.cj.deepmind.more.helper.VersionManagement
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.green
import com.cj.deepmind.ui.theme.red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoView(){
    val navController = rememberNavController()
    var versionName = ""
    val latest = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val helper = VersionManagement()

    DeepMindTheme {
        LaunchedEffect(key1 = true){
            helper.getVersion {
                latest.value = if(it){
                    VersionManagement.version ?: "버전 정보를 불러올 수 없습니다."
                } else{
                    "버전 정보를 불러올 수 없습니다."
                }
            }
            versionName = try{
                val pInfo: PackageInfo =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                val version = pInfo.versionName
                version
            } catch(e : Exception){
                e.printStackTrace()
                "버전 정보를 불러올 수 없습니다."
            }

        }
        NavHost(navController = navController, startDestination = "InfoView" ){
            composable(route = "PDFView"){

            }

            composable(route = "InfoView"){
                Scaffold(topBar = {
                    TopAppBar (
                        title = {
                            androidx.compose.material.Text(text = "정보", color = DeepMindColorPalette.current.txtColor)
                        },
                        navigationIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                androidx.compose.material.Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = accent)
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = DeepMindColorPalette.current.background,
                            titleContentColor = DeepMindColorPalette.current.txtColor
                        )
                    )
                }, content = {
                    Surface(modifier = Modifier.fillMaxSize(), color = DeepMindColorPalette.current.background) {
                        Column(
                            Modifier
                                .padding(it)
                                .verticalScroll(rememberScrollState())) {
                            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(5.dp)
                                    .height(150.dp)
                                    .background(
                                        color = DeepMindColorPalette.current.btnColor,
                                        shape = RoundedCornerShape(15.dp)
                                    ), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                                    Image(painter = painterResource(id = R.drawable.ic_deepmind),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(100.dp)
                                            .shadow(
                                                elevation = 8.dp,
                                                shape = RoundedCornerShape(16.dp),
                                                clip = true
                                            ))

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {
                                        Text(text = "DeepMind", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = DeepMindColorPalette.current.txtColor)
                                        Text(text = "현재 버전 : $versionName", fontSize = 15.sp, color = DeepMindColorPalette.current.txtColor)
                                        Text(text = "최신 버전 : ${latest.value}", fontSize = 15.sp, color = DeepMindColorPalette.current.txtColor)

                                        if(latest.value != "" && latest.value != "버전 정보를 불러올 수 없습니다."){
                                            if(latest.value == versionName){
                                                Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = green)

                                                    Spacer(modifier = Modifier.width(10.dp))

                                                    Text(text = "최신 버전입니다.", fontSize = 12.sp, color = green)
                                                }
                                            } else{
                                                Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = red)

                                                    Spacer(modifier = Modifier.width(5.dp))

                                                    Text(text = "최신 버전이 아닙니다.", fontSize = 12.sp, color = red)
                                                }
                                            }
                                        }
                                    }
                                }

                                if(latest.value != "" && latest.value != "버전 정보를 불러올 수 없습니다."){
                                    if(latest.value != versionName){
                                        Spacer(Modifier.height(20.dp))

                                        Button(onClick = { /*TODO*/ },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = DeepMindColorPalette.current.btnColor
                                            ), elevation = ButtonDefaults.buttonElevation(5.dp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp),
                                            shape = RoundedCornerShape(15.dp)
                                        ) {
                                            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                                                Icon(imageVector = Icons.Default.ArrowCircleUp, contentDescription = null, tint = accent )
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Text("업데이트", fontSize = 18.sp, color = DeepMindColorPalette.current.txtColor)
                                                Spacer(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                                }

                                Spacer(Modifier.height(20.dp))

                                Button(onClick = { /*TODO*/ },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DeepMindColorPalette.current.btnColor
                                    ), elevation = ButtonDefaults.buttonElevation(5.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    shape = RoundedCornerShape(15.dp)
                                ) {
                                    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Rectangle, contentDescription = null, modifier = Modifier.size(30.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("서비스 이용 약관 읽기", fontSize = 18.sp, color = DeepMindColorPalette.current.txtColor)
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }

                                Spacer(Modifier.height(20.dp))

                                Button(onClick = { /*TODO*/ },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DeepMindColorPalette.current.btnColor
                                    ), elevation = ButtonDefaults.buttonElevation(5.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    shape = RoundedCornerShape(15.dp)
                                ) {
                                    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Rectangle, contentDescription = null, modifier = Modifier.size(30.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("개인정보 처리 방침 읽기", fontSize = 18.sp, color = DeepMindColorPalette.current.txtColor)
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }

                                Spacer(Modifier.height(20.dp))

                                Button(onClick = { /*TODO*/ },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DeepMindColorPalette.current.btnColor
                                    ), elevation = ButtonDefaults.buttonElevation(5.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    shape = RoundedCornerShape(15.dp)
                                ) {
                                    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Rectangle, contentDescription = null, modifier = Modifier.size(30.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text("민감정보 수집 및 처리 방침 읽기", fontSize = 18.sp, color = DeepMindColorPalette.current.txtColor)
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }

                                Spacer(Modifier.height(20.dp))

                                Text(text = "© 2023. Changjin Ha, Yujee Chang\n All Rights Reserved.",
                                    color = gray,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center)

                            }
                        }

                    }
                })

            }
        }

    }
}

@Preview
@Composable
fun InfoView_previews(){
    InfoView()
}