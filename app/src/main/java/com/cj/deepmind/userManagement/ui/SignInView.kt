package com.cj.deepmind.userManagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.R
import com.cj.deepmind.frameworks.helper.AES256Util
import com.cj.deepmind.frameworks.helper.DataStoreUtil
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.red
import com.cj.deepmind.ui.theme.white
import com.cj.deepmind.userManagement.helper.UserManagement
import com.cj.deepmind.userManagement.models.AuthInfoModel

@Composable
fun SignInView(){
    val navController = rememberNavController()
    val email = remember{
        mutableStateOf("")
    }

    val password = remember{
        mutableStateOf("")
    }

    val showProgress = remember{
        mutableStateOf(false)
    }
    
    val showAlert = remember{
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val helper = UserManagement()
    val dataStoreUtil = DataStoreUtil(context)
    val authInfo = dataStoreUtil.getFromDataStore().collectAsState(initial = AuthInfoModel(email = "", password = ""))

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "SignInView") {
            composable("SignUpView"){
                SignUpView()
            }

            composable("SignInView"){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    LaunchedEffect(key1 = true){
                        if(AES256Util.decrypt(authInfo.value.email) != "" &&
                            AES256Util.decrypt(authInfo.value.password) != ""){
                            showProgress.value = true
                        }
                    }

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = DeepMindColorPalette.current.background
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.weight(1f))

                            Image(
                                painter = painterResource(id = R.drawable.ic_deepmind),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .shadow(
                                        elevation = 8.dp,
                                        shape = RoundedCornerShape(10.dp),
                                        clip = true
                                    )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "DeepMind",
                                modifier = Modifier,
                                color = DeepMindColorPalette.current.txtColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(20.dp))


                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = email.value,
                                onValueChange = { textVal: String -> email.value = textVal },
                                label = { Text("E-Mail") },
                                placeholder = { Text("E-Mail") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.AlternateEmail,
                                        contentDescription = null
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    cursorColor = accent,
                                    focusedBorderColor = accent,
                                    errorCursorColor = red,
                                    errorLeadingIconColor = red,
                                    disabledPlaceholderColor = gray,
                                    focusedTextColor = accent,
                                    focusedLabelColor = accent,
                                    focusedLeadingIconColor = accent,
                                    disabledTextColor = gray,
                                    unfocusedLabelColor = DeepMindColorPalette.current.txtColor,
                                    unfocusedLeadingIconColor = DeepMindColorPalette.current.txtColor,
                                    unfocusedSupportingTextColor = DeepMindColorPalette.current.txtColor,
                                    selectionColors = TextSelectionColors(
                                        handleColor = accent,
                                        backgroundColor = accent.copy(alpha = 0.5f)
                                    )
                                ),
                                maxLines = 1,
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(20.dp))


                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = password.value,
                                onValueChange = { textVal: String -> password.value = textVal },
                                label = { Text("비밀번호") },
                                placeholder = { Text("비밀번호") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Key,
                                        contentDescription = null
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    cursorColor = accent,
                                    focusedBorderColor = accent,
                                    errorCursorColor = red,
                                    errorLeadingIconColor = red,
                                    disabledPlaceholderColor = gray,
                                    focusedTextColor = accent,
                                    focusedLabelColor = accent,
                                    focusedLeadingIconColor = accent,
                                    disabledTextColor = gray,
                                    unfocusedLabelColor = DeepMindColorPalette.current.txtColor,
                                    unfocusedLeadingIconColor = DeepMindColorPalette.current.txtColor,
                                    unfocusedSupportingTextColor = DeepMindColorPalette.current.txtColor,
                                    selectionColors = TextSelectionColors(
                                        handleColor = accent,
                                        backgroundColor = accent.copy(alpha = 0.5f)
                                    )

                                ),
                                visualTransformation = PasswordVisualTransformation(),
                                maxLines = 1,
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    showProgress.value = true

                                    helper.signIn(email.value, password.value){
                                        if(it){

                                        } else{
                                            
                                        }

                                        showProgress.value = false
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                enabled = !email.value.isEmpty() && !password.value.isEmpty() && !showProgress.value,
                                contentPadding = PaddingValues(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = accent, disabledContainerColor = gray
                                ),
                                elevation = ButtonDefaults.buttonElevation(
                                    5.dp,
                                    disabledElevation = 5.dp
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("로그인", color = white)
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = white
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    navController.navigate("SignUpView") {
                                        popUpTo("SignInView") {
                                            inclusive = false
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = accent
                                ),
                                elevation = ButtonDefaults.buttonElevation(5.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            "처음 사용하시나요?",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = white
                                        )
                                        Text("회원가입 바로가기", fontSize = 12.sp, color = white)
                                    }

                                    Spacer(modifier = Modifier.width(60.dp))

                                    Icon(
                                        imageVector = Icons.Default.ArrowCircleRight,
                                        contentDescription = null,
                                        tint = white
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "© 2023. Changjin Ha, Yujee Jang\nAll Rights Reserved.",
                                color = gray,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )

                            if(showProgress.value){
                                Dialog(
                                    onDismissRequest = { showProgress.value = false },
                                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                                ) {
                                    Box(
                                        contentAlignment= Alignment.Center,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .background(
                                                DeepMindColorPalette.current.background.copy(alpha = 0.7f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        CircularProgressIndicator(color = accent)
                                    }
                                }
                            }

                            if(showAlert.value){
                                AlertDialog(
                                    onDismissRequest = { showAlert.value = false },

                                    confirmButton = {
                                        TextButton(onClick = {
                                            showAlert.value = false
                                        }){
                                            Text("확인", color = accent, fontWeight = FontWeight.Bold)
                                        }
                                    },
                                    title = {
                                        Text("오류")
                                    },
                                    text = {
                                        Text("로그인을 진행하는 중 문제가 발생하였습니다.\n일치하는 회원정보가 없거나 불안정한 네트워크 상태가 원인일 수 있습니다.")
                                    },
                                    icon = {
                                        Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                                    }
                                )
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
fun SignInView_previews(){
    SignInView()
}
