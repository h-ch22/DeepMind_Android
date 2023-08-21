package com.cj.deepmind.userManagement.ui

import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
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
import com.cj.deepmind.frameworks.helper.DataStoreUtil
import com.cj.deepmind.frameworks.ui.StartActivity
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.red
import com.cj.deepmind.userManagement.helper.UserManagement
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(){
    val navController = rememberNavController()
    var showDialog by remember { mutableStateOf(false) }
    var showFailAlert by remember { mutableStateOf(false) }
    var showSignOutAlert by remember { mutableStateOf(false) }
    var showSecessionAlert by remember { mutableStateOf(false) }
    var showSecessionCompleteAlert by remember{ mutableStateOf(false) }
    var showSecessionFailAlert by remember{ mutableStateOf(false) }

    val context = LocalContext.current
    val dataStoreUtil = DataStoreUtil(context)
    val coroutineScope = rememberCoroutineScope()

    val helper = UserManagement()

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "ProfileView"){
            composable(route = "ProfileView"){
                Surface(color = DeepMindColorPalette.current.background, modifier = Modifier.fillMaxSize()) {
                    Scaffold(topBar = {
                        TopAppBar (
                            title = {
                                Text(text = "프로필 보기", color = DeepMindColorPalette.current.txtColor)
                            },
                            navigationIcon = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = accent)
                                }
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = DeepMindColorPalette.current.background,
                                titleContentColor = DeepMindColorPalette.current.txtColor
                            )
                        )
                    }, content = {
                        Column(
                            Modifier
                                .background(DeepMindColorPalette.current.background)
                                .padding(it)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_deepmind),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                androidx.compose.material3.Text(
                                    AES256Util.decrypt(UserManagement.userInfo?.nickName),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepMindColorPalette.current.txtColor
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                androidx.compose.material3.Text(
                                    AES256Util.decrypt(UserManagement.userInfo?.email),
                                    fontSize = 12.sp,
                                    color = gray
                                )

                                Spacer(modifier = Modifier.height(20.dp))

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
                                        androidx.compose.material3.Icon(
                                            imageVector = Icons.Default.DeleteForever,
                                            contentDescription = null,
                                            tint = red,
                                            modifier = Modifier.size(30.dp)
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))
                                        androidx.compose.material3.Text(
                                            "민감정보 삭제 요청 및 동의 철회",
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
                                        androidx.compose.material3.Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            tint = red,
                                            modifier = Modifier.size(30.dp)
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))
                                        androidx.compose.material3.Text(
                                            "닉네임 변경",
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
                                        androidx.compose.material3.Icon(
                                            imageVector = Icons.Default.Phone,
                                            contentDescription = null,
                                            tint = red,
                                            modifier = Modifier.size(30.dp)
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))
                                        androidx.compose.material3.Text(
                                            "연락처 변경",
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
                                        androidx.compose.material3.Icon(
                                            imageVector = Icons.Default.Key,
                                            contentDescription = null,
                                            tint = red,
                                            modifier = Modifier.size(30.dp)
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))
                                        androidx.compose.material3.Text(
                                            "비밀번호 변경",
                                            fontSize = 18.sp,
                                            color = DeepMindColorPalette.current.txtColor
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    TextButton(onClick = {
                                        showSignOutAlert = true
                                    }) {
                                        androidx.compose.material3.Text(text = "로그아웃", color = gray, fontSize = 10.sp, textDecoration = TextDecoration.Underline)
                                    }

                                    androidx.compose.material3.Text(
                                        text = "또는",
                                        color = gray,
                                        fontSize = 10.sp
                                    )

                                    TextButton(onClick = {
                                        showSecessionAlert = true
                                    }) {
                                        androidx.compose.material3.Text(text = "회원 탈퇴", color = gray, fontSize = 10.sp, textDecoration = TextDecoration.Underline)
                                    }
                                }

                                if(showFailAlert){
                                    AlertDialog(
                                        onDismissRequest = { showFailAlert = false },

                                        confirmButton = {
                                            TextButton(onClick = {
                                                showFailAlert = false
                                            }){
                                                androidx.compose.material3.Text("확인", color = accent, fontWeight = FontWeight.Bold)
                                            }
                                        },
                                        title = {
                                            androidx.compose.material3.Text("오류")
                                        },
                                        text = {
                                            androidx.compose.material3.Text("요청하신 작업을 처리하는 중 문제가 발생했습니다.\n정상 로그인 여부, 네트워크 상태를 확인하거나 나중에 다시 시도해주세요.")
                                        },
                                        icon = {
                                            androidx.compose.material3.Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                                        }
                                    )
                                }

                                if(showSignOutAlert){
                                    AlertDialog(
                                        onDismissRequest = { showSignOutAlert = false },

                                        confirmButton = {
                                            TextButton(onClick = {
                                                showSignOutAlert = false
                                                showDialog = true

                                                helper.signOut {
                                                    if(it){
                                                        showDialog = false
                                                        coroutineScope.launch{
                                                            dataStoreUtil.clearDataStore()
                                                        }

                                                        context.startActivity(
                                                            Intent(
                                                            context, StartActivity :: class.java)
                                                        )

                                                    } else{
                                                        showDialog = false
                                                        showFailAlert = true
                                                    }
                                                }
                                            }){
                                                androidx.compose.material3.Text("예", color = accent, fontWeight = FontWeight.Bold)
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = {
                                                showSignOutAlert = false
                                            }){
                                                Text(text = "아니오", color = accent, fontWeight = FontWeight.Bold)
                                            }
                                        },
                                        title = {
                                            androidx.compose.material3.Text("로그아웃")
                                        },
                                        text = {
                                            androidx.compose.material3.Text("로그아웃 시 자동 로그인이 해제되며, 다시 로그인하셔야 합니다.\n계속 하시겠습니까?")
                                        },
                                        icon = {
                                            androidx.compose.material3.Icon(imageVector = Icons.Default.Output, contentDescription = null)
                                        }
                                    )
                                }

                                if(showSecessionAlert){
                                    AlertDialog(
                                        onDismissRequest = { showSecessionAlert = false },

                                        confirmButton = {
                                            TextButton(onClick = {
                                                showSecessionAlert = false
                                                showDialog = true

                                                helper.secession {
                                                    if(it){
                                                        showDialog = false
                                                        showSecessionCompleteAlert = true

                                                    } else{
                                                        showDialog = false
                                                        showSecessionFailAlert = true
                                                    }
                                                }
                                            }){
                                                androidx.compose.material3.Text("예", color = accent, fontWeight = FontWeight.Bold)
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = {
                                                showSecessionAlert = false
                                            }){
                                                Text(text = "아니오", color = accent, fontWeight = FontWeight.Bold)
                                            }
                                        },
                                        title = {
                                            androidx.compose.material3.Text("회원 탈퇴 안내")
                                        },
                                        text = {
                                            androidx.compose.material3.Text("회원 탈퇴 시 모든 가입 정보가 제거되며, 복구할 수 없습니다.\n계속 하시겠습니까?")
                                        },
                                        icon = {
                                            androidx.compose.material3.Icon(imageVector = Icons.Default.Output, contentDescription = null)
                                        }
                                    )
                                }

                                if(showSecessionCompleteAlert){
                                    AlertDialog(
                                        onDismissRequest = {
                                            showSecessionCompleteAlert = false
                                            coroutineScope.launch{
                                                dataStoreUtil.clearDataStore()
                                            }

                                            context.startActivity(Intent(
                                                context, StartActivity :: class.java)
                                            )
                                        },

                                        confirmButton = {
                                            TextButton(onClick = {
                                                showSecessionCompleteAlert = false
                                                coroutineScope.launch{
                                                    dataStoreUtil.clearDataStore()
                                                }

                                                context.startActivity(Intent(
                                                    context, StartActivity :: class.java)
                                                )
                                            }){
                                                androidx.compose.material3.Text("확인", color = accent, fontWeight = FontWeight.Bold)
                                            }
                                        },
                                        title = {
                                            androidx.compose.material3.Text("감사 인사")
                                        },
                                        text = {
                                            androidx.compose.material3.Text("회원 탈퇴가 완료되었습니다.\n더 나은 서비스로 다시 만날 수 있도록 노력하겠습니다.\n그 동안 서비스를 이용해주셔서 감사합니다.")
                                        },
                                        icon = {
                                            androidx.compose.material3.Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                                        }
                                    )
                                }

                                if(showSecessionFailAlert){
                                    AlertDialog(
                                        onDismissRequest = { showSecessionFailAlert = false },

                                        confirmButton = {
                                            TextButton(onClick = {
                                                showSecessionFailAlert = false
                                            }){
                                                androidx.compose.material3.Text("확인", color = accent, fontWeight = FontWeight.Bold)
                                            }
                                        },
                                        title = {
                                            androidx.compose.material3.Text("오류")
                                        },
                                        text = {
                                            androidx.compose.material3.Text("요청하신 작업을 처리하는 중 문제가 발생했습니다.\n이제이 고객센터로 문의해주세요.")
                                        },
                                        icon = {
                                            androidx.compose.material3.Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                                        }
                                    )
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileView_previews(){
    ProfileView()
}