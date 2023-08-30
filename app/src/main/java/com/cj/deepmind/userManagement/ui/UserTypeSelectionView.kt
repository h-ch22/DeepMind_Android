package com.cj.deepmind.userManagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.white
import com.cj.deepmind.userManagement.models.UserTypeModel

@Composable
fun UserTypeSelectionView(){
    val navController = rememberNavController()
    var selectedType by remember {
        mutableStateOf(UserTypeModel.CUSTOMER)
    }

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "UserTypeSelectionView") {
            composable("SignUpView") {
                SignUpView(type = selectedType)
            }

            composable("UserTypeSelectionView") {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    Column(modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start) {
                        Text(text = "환영합니다!", fontWeight = FontWeight.SemiBold, color = DeepMindColorPalette.current.txtColor, fontSize = 18.sp)

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = {
                            selectedType = UserTypeModel.CUSTOMER
                            navController.navigate("SignUpView") {
                                popUpTo("UserTypeSelectionView") {
                                    inclusive = false
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = DeepMindColorPalette.current.btnColor), elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.padding(10.dp)) {
                                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                                    Text(text = "상담 대상자 또는 부모님 등의 고객이신가요?", color = gray, fontSize = 12.sp)
                                    Text(text = "일반 사용자 회원가입", color = DeepMindColorPalette.current.txtColor, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Icon(imageVector = Icons.Filled.ArrowCircleRight, contentDescription = null, tint = DeepMindColorPalette.current.txtColor)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = {
                            selectedType = UserTypeModel.PROFESSIONAL
                            navController.navigate("SignUpView") {
                                popUpTo("UserTypeSelectionView") {
                                    inclusive = false
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = DeepMindColorPalette.current.btnColor), elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.padding(10.dp)) {
                                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                                    Text(text = "의사/교사/선생님/상담사 등 전문가 고객이신가요?", color = gray, fontSize = 12.sp)
                                    Text(text = "전문가 회원가입", color = DeepMindColorPalette.current.txtColor, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Icon(imageVector = Icons.Filled.ArrowCircleRight, contentDescription = null, tint = DeepMindColorPalette.current.txtColor)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UserTypeSelectioinView_previews(){
    UserTypeSelectionView()
}