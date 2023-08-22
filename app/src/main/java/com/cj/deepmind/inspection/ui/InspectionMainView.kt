package com.cj.deepmind.inspection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.frameworks.models.MainViewModel
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.white

@Composable
fun InspectionMainView(viewModel: MainViewModel){
    val navController = rememberNavController()

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "InspectionMainView") {
            composable("InspectionMainView") {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "검사 시작하기", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepMindColorPalette.current.txtColor)

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = DeepMindColorPalette.current.txtColor)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(text = "딥러닝 기반 HTP 검사 진행", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = DeepMindColorPalette.current.txtColor)
                                Text(text = "딥러닝 모델을 기반으로 HTP 검사를 진행하며, 검사 결과를 바로 확인할 수 있습니다.", fontSize = 12.sp, color = gray)
                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.AutoGraph, contentDescription = null, tint = DeepMindColorPalette.current.txtColor)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(text = "검사 추이", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = DeepMindColorPalette.current.txtColor)
                                Text(text = "검사를 진행하면 홈 탭에서 검사 추이를 확인할 수 있으며, 검사 결과 탭에서 세부 검사 결과를 확인할 수 있습니다.", fontSize = 12.sp, color = gray)
                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = DeepMindColorPalette.current.txtColor)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(text = "주의사항", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = DeepMindColorPalette.current.txtColor)
                                Text(text = "DeepMind는 심리 검사 결과에 대한 정확성을 보증하지 않습니다. 사용자는 DeepMind를 통해 치료상의 이익 및/또는 의학적인 이익을 얻을 수 없으며, 정신.심리 상태에 문제가 있다고 의심되는 경우 의료기관에서 전문의와 상담을 통해 의학적 조치를 받으십시오.", fontSize = 12.sp, color = gray)
                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                viewModel.setInspectionDrawingViewState(true)
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
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InspectionMainView_previews(){
    InspectionMainView(viewModel=MainViewModel())
}