package com.cj.deepmind.more.ui

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.R
import com.cj.deepmind.frameworks.helper.AES256Util
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.red
import com.cj.deepmind.ui.theme.txtColor
import com.cj.deepmind.userManagement.helper.UserManagement

@Composable
fun MoreView(){
    val navController = rememberNavController()

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "MoreView") {
            composable(route = "MoreView") {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "DeepMind",
                                fontWeight = FontWeight.Bold,
                                color = DeepMindColorPalette.current.txtColor
                            )

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_deepmind),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                AES256Util.decrypt(UserManagement.userInfo?.nickName),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepMindColorPalette.current.txtColor
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Divider(modifier = Modifier.fillMaxWidth(), color = gray.copy(alpha = 0.5f))

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepMindColorPalette.current.btnColor
                            ), elevation = ButtonDefaults.buttonElevation(5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = accent,
                                    modifier = Modifier.size(30.dp))

                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    "공지사항",
                                    fontSize = 18.sp,
                                    color = DeepMindColorPalette.current.txtColor
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepMindColorPalette.current.btnColor
                            ), elevation = ButtonDefaults.buttonElevation(5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = red,
                                    modifier = Modifier.size(30.dp))

                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    "피드백 허브",
                                    fontSize = 18.sp,
                                    color = DeepMindColorPalette.current.txtColor
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepMindColorPalette.current.btnColor
                            ), elevation = ButtonDefaults.buttonElevation(5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null, tint = red,
                                    modifier = Modifier.size(30.dp))

                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    "민감정보 삭제 요청 및 동의 철회",
                                    fontSize = 18.sp,
                                    color = DeepMindColorPalette.current.txtColor
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepMindColorPalette.current.btnColor
                            ), elevation = ButtonDefaults.buttonElevation(5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = DeepMindColorPalette.current.txtColor,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    "정보",
                                    fontSize = 18.sp,
                                    color = DeepMindColorPalette.current.txtColor
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            TextButton(onClick = { }) {
                                Text(text = "로그아웃", color = gray, fontSize = 10.sp, textDecoration = TextDecoration.Underline)
                            }

                            Text(text = "또는", color = gray, fontSize = 10.sp)

                            TextButton(onClick = { }) {
                                Text(text = "회원 탈퇴", color = gray, fontSize = 10.sp, textDecoration = TextDecoration.Underline)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoreView_previews(){
    MoreView()
}