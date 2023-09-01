package com.cj.deepmind.inspection.ui

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.inspection.models.DrawMode
import com.cj.deepmind.inspection.models.InspectionDrawingViewModel
import com.cj.deepmind.inspection.models.InspectionTypeModel
import com.cj.deepmind.inspection.models.MotionEvent
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.white
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.util.Timer
import kotlin.concurrent.timer

fun getDescriptionByCode(code: Int): String? {
    InspectionTypeModel.values().forEach {
        if (it.getCode() == code) {
            return it.getDescription()
        }
    }

    return null
}

fun getACode(code: Int): String? {
    InspectionTypeModel.values().forEach {
        if (it.getCode() == code) {
            return it.getACode()
        }
    }

    return null
}

@Composable
fun InspectionDrawingView(viewModel: InspectionDrawingViewModel = InspectionDrawingViewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val currentIndex = remember {
        mutableStateOf(0)
    }

    var elapsedTime by remember {
        mutableStateOf(0)
    }

    var canvasBitmap: ImageBitmap? by remember {
        mutableStateOf(null)
    }

    var timerTask: Timer? = null
    val decimalFormat = DecimalFormat("00")
    val captureController = rememberCaptureController()
    val showErrorAlert = remember {
        mutableStateOf(false)
    }

    DeepMindTheme {
        LaunchedEffect(key1 = true) {
            timerTask = timer(period = 1) {
                elapsedTime += 1
            }
        }

        NavHost(navController = navController, startDestination = "InspectionDrawingView") {
            composable(route = "onInspectionView") {
                onInspectionView()
            }

            composable(route = "InspectionDrawingView") {
                Surface(modifier = Modifier.fillMaxSize(), color = white) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        getDescriptionByCode(currentIndex.value)?.let { description ->
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "${description}을 그려주세요.")

                                if (currentIndex.value == 3) {
                                    Text(text = "이전과 반대 성별로 그려주세요.", fontSize = 12.sp, color = gray)
                                }
                            }
                        }

                        Capturable(controller = captureController, onCaptured = { bitmap, error ->
                            if (bitmap != null) {
                                canvasBitmap = bitmap
                            } else if (error != null) {
                                showErrorAlert.value = true
                            }
                        }, modifier = Modifier.weight(1f)) {
                            InspectionCanvasView(viewModel = viewModel)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${decimalFormat.format(elapsedTime / 1000 / 60)}:${
                                    decimalFormat.format(
                                        elapsedTime / 1000 % 60
                                    )
                                }", color = accent
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            DrawingPropertiesMenu(
                                modifier = Modifier
                                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                                    .shadow(1.dp, RoundedCornerShape(8.dp))
                                    .background(DeepMindColorPalette.current.btnColor)
                                    .padding(4.dp),
                                pathProperties = viewModel.getCurrentPathProperty(),
                                drawMode = viewModel.getDrawMode(),
                                onPathPropertiesChange = {
                                    viewModel.updateMotionEvent(MotionEvent.IDLE)
                                },
                                onDrawModeChange = {
                                    viewModel.updateMotionEvent(MotionEvent.IDLE)
                                    viewModel.updateDrawMode(it)
                                    viewModel.getCurrentPathProperty().isErase =
                                        (viewModel.getDrawMode() == DrawMode.ERASE)
                                }
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(onClick = {
                                captureController.capture()
                            }) {
                                Text(text = if (currentIndex.value < 3) "다음" else "검사 종료")
                            }
                        }
                    }

                    canvasBitmap?.let{ bitmap ->
                        LaunchedEffect(key1 = true){
                            if (canvasBitmap != null) {
                                try {
                                    val contextWrapper = ContextWrapper(context)
                                    val directory: File = contextWrapper.getDir("HTP", Context.MODE_PRIVATE)

                                    if(!directory.exists()){
                                        directory.mkdir()
                                    }

                                    val path = File(directory, "${getACode(currentIndex.value)}.png")

                                    val fileOutputStream: FileOutputStream =
                                        FileOutputStream(path)

                                    canvasBitmap!!.asAndroidBitmap().compress(
                                        Bitmap.CompressFormat.PNG,
                                        100,
                                        fileOutputStream
                                    )
                                    fileOutputStream.close()

                                    Log.d("InspectionDrawingView", "Image saved to ${path}")

                                    canvasBitmap = null

                                    viewModel.updateClearState(true)
                                    elapsedTime = 0

                                    if (currentIndex.value < 3) {
                                        currentIndex.value += 1

                                    } else {
                                        navController.navigate("onInspectionView") {
                                            popUpTo("InspectionDrawingView") {
                                                inclusive = false
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    showErrorAlert.value = true
                                }
                            } else{
                                showErrorAlert.value = true
                                Log.d("InspectionDrawingView", "CanvasBitmap is null.")
                            }
                        }
                    }

                    if (showErrorAlert.value) {
                        AlertDialog(
                            onDismissRequest = { showErrorAlert.value = false },

                            confirmButton = {
                                TextButton(onClick = {
                                    showErrorAlert.value = false
                                }) {
                                    Text("확인", color = accent, fontWeight = FontWeight.Bold)
                                }
                            },
                            title = {
                                Text("오류")
                            },
                            text = {
                                Text("이미지를 저장하는 중 문제가 발생했습니다.\n나중에 다시 시도하십시오.")
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

@Preview
@Composable
fun InspectionDrawingView_previews() {
    InspectionDrawingView()
}