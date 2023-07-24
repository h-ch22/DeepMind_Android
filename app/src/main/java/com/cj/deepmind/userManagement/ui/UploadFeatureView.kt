package com.cj.deepmind.userManagement.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray

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
                    .padding(10.dp), color = DeepMindColorPalette.current.btnColor) {
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
                    .padding(10.dp), color = DeepMindColorPalette.current.btnColor) {
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadFeatureView_previews(){
    UploadFeatureView()
}