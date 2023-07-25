package com.cj.deepmind.userManagement.ui

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cj.deepmind.frameworks.ui.MainActivity
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.white
import com.cj.deepmind.userManagement.helper.UserManagement

@Composable
fun UploadFeatureView(){
    val isChildAbuseAttacker = remember {
        mutableStateOf(false)
    }

    val isChildAbuseVictim = remember{
        mutableStateOf(false)
    }

    val isDomesticViolenceAttacker = remember{
        mutableStateOf(false)
    }

    val isDomesticViolenceVictim = remember{
        mutableStateOf(false)
    }

    val isPsychosis = remember{
        mutableStateOf(false)
    }

    val showProgress = remember{
        mutableStateOf(false)
    }

    val showAlert = remember{
        mutableStateOf(false)
    }

    val helper = UserManagement()
    val context = LocalContext.current

    DeepMindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DeepMindColorPalette.current.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DeepMindColorPalette.current.background)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text("추가정보를 입력해주세요.",
                    color = DeepMindColorPalette.current.txtColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Text("모든 정보는 암호화되어 저장되며, 해당 정보는 사용자를 제외한 모든 사람이 조회할 수 없습니다.",
                    color = gray,
                    fontSize = 12.sp)

                Spacer(modifier = Modifier.height(20.dp))

                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "귀하께서는 아동학대 가해자로 판정받으신 적이 있습니까?",
                            color = DeepMindColorPalette.current.txtColor,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(0.4f))

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = isChildAbuseAttacker.value, onClick = { isChildAbuseAttacker.value = true }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("예", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }
                        
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = !isChildAbuseAttacker.value, onClick = { isChildAbuseAttacker.value = false }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("아니오", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "귀하께서는 아동학대 피해자로 판정받으신 적이 있습니까?",
                            color = DeepMindColorPalette.current.txtColor,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(0.4f))

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = isChildAbuseVictim.value, onClick = { isChildAbuseVictim.value = true }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("예", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = !isChildAbuseVictim.value, onClick = { isChildAbuseVictim.value = false }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("아니오", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "귀하께서는 가정폭력 가해자로 판정받으신 적이 있습니까?",
                            color = DeepMindColorPalette.current.txtColor,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(0.4f))

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = isDomesticViolenceAttacker.value, onClick = { isDomesticViolenceAttacker.value = true }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("예", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = !isDomesticViolenceAttacker.value, onClick = { isDomesticViolenceAttacker.value = false }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("아니오", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "귀하께서는 가정폭력 피해자로 판정받으신 적이 있습니까?",
                            color = DeepMindColorPalette.current.txtColor,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(0.4f))

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = isDomesticViolenceVictim.value, onClick = { isDomesticViolenceVictim.value = true }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("예", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = !isDomesticViolenceVictim.value, onClick = { isDomesticViolenceVictim.value = false }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("아니오", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "귀하께서는 전문의로부터 정신병 및 관련 질환을 판정받았거나 의심 증상이 있습니까?",
                            color = DeepMindColorPalette.current.txtColor,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(0.4f))

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = isPsychosis.value, onClick = { isPsychosis.value = true }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("예", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.2f)){
                            RadioButton(selected = !isPsychosis.value, onClick = { isPsychosis.value = false }, colors = RadioButtonDefaults.colors(
                                selectedColor = accent, unselectedColor = accent
                            ))
                            Text("아니오", fontSize = 12.sp, color = DeepMindColorPalette.current.txtColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        showProgress.value = true

                        helper.uploadFeatures(isChildAbuseAttacker.value, isChildAbuseVictim.value, isDomesticViolenceAttacker.value, isDomesticViolenceVictim.value, isPsychosis.value){
                            if(it){
                                helper.getUserInfo {
                                    if(it){
                                        context.startActivity(Intent(context, MainActivity :: class.java))
                                    }
                                }
                            } else{
                                showAlert.value = true
                                showProgress.value = false
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        Text("다음 단계로", color = white)
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = white
                        )
                    }
                }

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
                            Text("요청하신 작업을 처리하는 중 문제가 발생하였습니다.\n일치하는 회원정보가 없거나 불안정한 네트워크 상태가 원인일 수 있습니다.")
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

@Preview(showBackground = true)
@Composable
fun UploadFeatureView_previews(){
    UploadFeatureView()
}