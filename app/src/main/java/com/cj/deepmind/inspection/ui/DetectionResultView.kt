package com.cj.deepmind.inspection.ui

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.R
import com.cj.deepmind.inspection.models.AnalysisResult
import com.cj.deepmind.inspection.models.InspectionEssentialQuestionViewModel
import com.cj.deepmind.inspection.models.InspectionTypeModel
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.gray
import java.io.File

fun indexToString(index: Int): String{
    when(index){
        0 -> return "집"
        1 -> return "나무"
        2 -> return "첫번째 사람"
        3 -> return "두번째 사람"
        else -> return ""
    }
}

fun indexToType(index: Int): InspectionTypeModel{
    when(index){
        0 -> return InspectionTypeModel.HOUSE
        1 -> return InspectionTypeModel.TREE
        2 -> return InspectionTypeModel.PERSON_1
        3 -> return InspectionTypeModel.PERSON_2
        else -> return InspectionTypeModel.HOUSE
    }
}

fun loadImage(type: Int, context: Context): Bitmap?{
    val contextWrapper = ContextWrapper(context)
    val directory: File = contextWrapper.getDir("HTP", Context.MODE_PRIVATE)

    when(type) {
        0 -> {
            val path = File(directory, "HOUSE.png")
            if (path.exists()) {
                return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path.path), 1280, 1280, true)
            } else {
                Log.d("DetectionResultView", "File not found : HOUSE.png")
                return null
            }
        }

        1 -> {
            val path = File(directory, "TREE.png")
            if (path.exists()) {
                return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path.path), 1280, 1280, true)
            } else {
                Log.d("DetectionResultView", "File not found : TREE.png")
                return null

            }
        }

        2 -> {
            val path = File(directory, "PERSON_1.png")
            if (path.exists()) {
                return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path.path), 1280, 1280, true)
            } else {
                Log.d("DetectionResultView", "File not found : PERSON_1.png")
                return null
            }
        }

        3 -> {
            val path = File(directory, "PERSON_2.png")
            if (path.exists()) {
                return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path.path), 1280, 1280, true)
            } else {
                Log.d("DetectionResultView", "File not found : PERSON_2.png")
                return null
            }
        }

        else -> return null
    }
}

@Composable
fun DetectionResultView(result_CL01: AnalysisResult?,
                        result_CL02: AnalysisResult?,
                        result_CL03_1: AnalysisResult?,
                        result_CL03_2: AnalysisResult?){
    val navController = rememberNavController()
    val currentIndex = remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val viewModel = InspectionEssentialQuestionViewModel()

    DeepMindTheme {
        NavHost(navController = navController, startDestination = "DetectionResultView") {
            composable("InspectionEssentialQuestionView"){
                InspectionEssentialQuestionView(type = indexToType(currentIndex.value), viewModel = viewModel, navController = navController)
            }
            composable("DetectionResultView") {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row {
                            Spacer(modifier = Modifier.weight(1f))

                            Text(text = "현재 오브젝트: ${indexToString(currentIndex.value)}",
                                fontSize = 12.sp,
                                color = gray)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(text = "고객님의 이미지에서 오브젝트를 검출한 결과입니다.",
                            fontWeight = FontWeight.SemiBold,
                            color = DeepMindColorPalette.current.txtColor,
                            textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.weight(1f))

                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)){
                            AndroidView(factory = {
                                val view = LayoutInflater.from(it).inflate(R.layout.layout_detection_result, null, false)
                                val imgView = view.findViewById<ImageView>(R.id.imgView)
                                val resultView = view.findViewById<DetectionResult>(R.id.resultView)

                                imgView.setImageBitmap(loadImage(currentIndex.value, context))
                                resultView.setResult(
                                    when(currentIndex.value){
                                        0 -> result_CL01
                                        1 -> result_CL02
                                        2 -> result_CL03_1
                                        3 -> result_CL03_2
                                        else -> null
                                    }
                                )

                                view
                            }, update = {
                                val resultView = it.findViewById<DetectionResult>(R.id.resultView)
                                val imgView = it.findViewById<ImageView>(R.id.imgView)
                                if(imgView != null){
                                    imgView.setImageBitmap(loadImage(currentIndex.value, context))
                                } else{
                                    Log.d("DetectionResultView", "Given Imagebitmap is null.")
                                }

                                if(result_CL01 != null && result_CL02 != null && result_CL03_1 != null && result_CL03_2 != null){
                                    if(resultView != null){
                                        resultView.setResult(
                                            when(currentIndex.value){
                                                0 -> result_CL01
                                                1 -> result_CL02
                                                2 -> result_CL03_1
                                                3 -> result_CL03_2
                                                else -> null
                                            }
                                        )

                                        resultView.invalidate()
                                    } else{
                                        Log.d("DetectionResultView", "Given resultView is null.")
                                    }
                                } else{
                                    Log.d("DetectionResultView", "Given results is null.")
                                }
                            })
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = {
                            navController.navigate("InspectionEssentialQuestionView") {
                                popUpTo("DetectionResultView") {
                                    inclusive = false
                                }
                            }
                        }) {
                            Text(text = "필수 문답 작성하기")
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = {
                                if(currentIndex.value > 0){
                                    currentIndex.value -= 1
                                }
                            }, enabled = currentIndex.value > 0) {
                                Text(text = "이전")
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(onClick = {
                                if(currentIndex.value < 3){
                                    currentIndex.value += 1
                                }
                            }) {
                                Text(text = if(currentIndex.value < 3) "다음" else "완료")
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
fun DetectResultView_previews(){
    DetectionResultView(result_CL01 = null, result_CL02 = null, result_CL03_1 = null, result_CL03_2 = null)
}