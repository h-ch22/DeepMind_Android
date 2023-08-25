package com.cj.deepmind.inspection.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.inspection.helper.InspectionHelper
import com.cj.deepmind.inspection.models.AnalysisResult
import com.cj.deepmind.inspection.models.InspectionTypeModel
import com.cj.deepmind.inspection.models.Result
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.background
import com.cj.deepmind.ui.theme.backgroundAsDark
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.green
import com.cj.deepmind.ui.theme.orange
import com.cj.deepmind.ui.theme.white
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun onInspectionView() {
    val navController = rememberNavController()

    var current by remember {
        mutableStateOf<InspectionTypeModel?>(InspectionTypeModel.HOUSE)
    }

    var isError by remember {
        mutableStateOf<InspectionTypeModel?>(null)
    }

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    var result_CL01 by remember{
       mutableStateOf<AnalysisResult?>(null)
    }

    var result_CL02 by remember{
        mutableStateOf<AnalysisResult?>(null)
    }

    var result_CL03_1 by remember{
        mutableStateOf<AnalysisResult?>(null)
    }

    var result_CL03_2 by remember{
        mutableStateOf<AnalysisResult?>(null)
    }

    val helper = InspectionHelper()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "onInspectionView") {
            composable("InspectionResultView") {

            }

            composable("onInspectionView") {
                LaunchedEffect(key1 = true) {
                    thread(start = true) {
                        val CL01 = helper.predict(InspectionTypeModel.HOUSE, context)

                        if (CL01 != null) {
                            result_CL01 = CL01
                            current = InspectionTypeModel.TREE

                            val CL02 = helper.predict(InspectionTypeModel.TREE, context)

                            if (CL02 != null) {
                                result_CL02 = CL02
                                current = InspectionTypeModel.PERSON_1

                                val CL03_1 =
                                    helper.predict(InspectionTypeModel.PERSON_1, context)

                                if (CL03_1 != null) {
                                    result_CL03_1 = CL03_1
                                    current = InspectionTypeModel.PERSON_2

                                    val CL03_2 =
                                        helper.predict(InspectionTypeModel.PERSON_2, context)

                                    if (CL03_2 != null) {
                                        result_CL03_2 = CL03_2
                                        current = null
                                    } else {
                                        current = null
                                        isError = InspectionTypeModel.PERSON_2
                                    }
                                } else {
                                    current = null
                                    isError = InspectionTypeModel.PERSON_1
                                }
                            } else {
                                current = null
                                isError = InspectionTypeModel.TREE
                            }
                        } else {
                            current = null
                            isError = InspectionTypeModel.HOUSE
                        }
                    }

                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    AnimatedVisibility(visible = true) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                text = "검사 진행 중",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DeepMindColorPalette.current.txtColor
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "DeepMind에서 고객님의 요청사항을 처리하고 있습니다.\n잠시 기다려 주십시오.",
                                color = gray,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                lineHeight = 15.sp
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (current == InspectionTypeModel.HOUSE) {
                                    CircularProgressIndicator(
                                        color = accent,
                                        modifier = Modifier.size(10.dp),
                                        strokeWidth = 1.dp
                                    )
                                } else if (current != InspectionTypeModel.HOUSE && isError != InspectionTypeModel.HOUSE) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = green,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else if (current == null && isError == InspectionTypeModel.HOUSE) {
                                    Icon(
                                        imageVector = Icons.Rounded.Warning,
                                        contentDescription = null,
                                        tint = orange,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "CL01 (집) 오브젝트 검출",
                                    color = if (current == InspectionTypeModel.HOUSE) DeepMindColorPalette.current.txtColor else gray,
                                    fontSize = if (current == InspectionTypeModel.HOUSE) 15.sp else 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (current == InspectionTypeModel.TREE) {
                                    CircularProgressIndicator(
                                        color = accent,
                                        modifier = Modifier.size(10.dp),
                                        strokeWidth = 1.dp
                                    )
                                } else if ((current != InspectionTypeModel.HOUSE) && (isError != InspectionTypeModel.TREE)) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = green,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else if (current == null && isError == InspectionTypeModel.TREE) {
                                    Icon(
                                        imageVector = Icons.Rounded.Warning,
                                        contentDescription = null,
                                        tint = orange,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "CL02 (나무) 오브젝트 검출",
                                    color = if (current == InspectionTypeModel.TREE) DeepMindColorPalette.current.txtColor else gray,
                                    fontSize = if (current == InspectionTypeModel.TREE) 15.sp else 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (current == InspectionTypeModel.PERSON_1) {
                                    CircularProgressIndicator(
                                        color = accent,
                                        modifier = Modifier.size(10.dp),
                                        strokeWidth = 1.dp
                                    )
                                } else if (current != InspectionTypeModel.PERSON_1 && current != InspectionTypeModel.TREE && current != InspectionTypeModel.HOUSE && isError != InspectionTypeModel.PERSON_1) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = green,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else if (current == null && isError == InspectionTypeModel.PERSON_1) {
                                    Icon(
                                        imageVector = Icons.Rounded.Warning,
                                        contentDescription = null,
                                        tint = orange,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "CL03 (첫번째 사람) 오브젝트 검출",
                                    color = if (current == InspectionTypeModel.PERSON_1) DeepMindColorPalette.current.txtColor else gray,
                                    fontSize = if (current == InspectionTypeModel.PERSON_1) 15.sp else 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (current == InspectionTypeModel.PERSON_2) {
                                    CircularProgressIndicator(
                                        color = accent,
                                        modifier = Modifier.size(10.dp),
                                        strokeWidth = 1.dp
                                    )
                                } else if (current == null && isError == null) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = green,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else if (current == null && isError == InspectionTypeModel.PERSON_2) {
                                    Icon(
                                        imageVector = Icons.Rounded.Warning,
                                        contentDescription = null,
                                        tint = orange,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "CL03 (두번째 사람) 오브젝트 검출",
                                    color = if (current == InspectionTypeModel.PERSON_2) DeepMindColorPalette.current.txtColor else gray,
                                    fontSize = if (current == InspectionTypeModel.PERSON_2) 15.sp else 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            if (current == null && isError != null) {
                                Text(
                                    text = "DeepMind에서 고객님이 요청하신 작업을 처리하는 중 문제가 발생했습니다.\n필수 구성 요소 (예: 집 전체, 나무 전체, 사람 전체)가 인식되지 않았거나, 내부 오류일 수 있습니다. 다른 그림으로 다시 시도하거나, 나중에 다시 시도하십시오.",
                                    fontSize = 12.sp,
                                    color = gray,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 15.sp
                                )
                            } else if (current == null) {
                                Text(
                                    text = "DeepMind에서 고객님이 요청하신 작업을 모두 완료하였습니다.",
                                    fontSize = 12.sp,
                                    color = gray,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 15.sp
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(onClick = {
                                    coroutineScope.launch {
                                        modalSheetState.show()
                                    }
                                }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("검출 결과 확인", color = white)
                                        Icon(
                                            imageVector = Icons.Default.ChevronRight,
                                            contentDescription = null,
                                            tint = white
                                        )
                                    }
                                }
                            }
                        }

                        ModalBottomSheetLayout(
                            sheetContent = {
                                Surface(color = DeepMindColorPalette.current.background) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(color = if(isSystemInDarkTheme()) backgroundAsDark else background)) {
                                            Spacer(modifier = Modifier.weight(1f))

                                            IconButton(onClick = {
                                                coroutineScope.launch {
                                                    modalSheetState.hide()
                                                }
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Cancel,
                                                    contentDescription = null,
                                                    tint = gray
                                                )
                                            }
                                        }
                                        DetectionResultView(
                                            result_CL01 = result_CL01,
                                            result_CL02 = result_CL02,
                                            result_CL03_1 = result_CL03_1,
                                            result_CL03_2 = result_CL03_2
                                        )
                                    }
                                }

                            },
                            sheetState = modalSheetState,
                            modifier = Modifier.fillMaxSize(),
                            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun onInspectionView_previews() {
    onInspectionView()
}