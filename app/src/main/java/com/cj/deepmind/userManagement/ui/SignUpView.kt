package com.cj.deepmind.userManagement.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.red
import com.cj.deepmind.ui.theme.white
import com.cj.deepmind.userManagement.helper.UserManagement
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView() {
    val title = remember {
        mutableStateOf("반가워요!")
    }

    val name = remember {
        mutableStateOf("")
    }

    val nickName = remember {
        mutableStateOf("")
    }

    val phoneNumber = remember {
        mutableStateOf("")
    }

    val birthDayDefaultValue = "생년월일을 선택해주세요."
    val helper = UserManagement()

    val birthDay = remember {
        mutableStateOf(birthDayDefaultValue)
    }

    val isLicenseAccepted = remember {
        mutableStateOf(false)
    }

    val isPrivacyLicenseAccepted = remember {
        mutableStateOf(false)
    }

    val isSensitiveLicenseAccepted = remember {
        mutableStateOf(false)
    }

    val showView = remember {
        mutableStateOf(false)
    }

    val showDatePicker = remember{
        mutableStateOf(false)
    }

    val showProgress = remember{
        mutableStateOf(false)
    }

    val navController = rememberNavController()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())


    DeepMindTheme {
        NavHost(navController = navController, startDestination = "SignUpView") {
            composable(route = "UploadFeatureView") {
                
            }

            composable(route = "SignUpView") {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepMindColorPalette.current.background
                ) {
                    Scaffold(topBar = {
                        androidx.compose.material3.TopAppBar(
                            title = {
                                androidx.compose.material.Text(
                                    text = "회원가입",
                                    color = DeepMindColorPalette.current.txtColor
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    androidx.compose.material.Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = null,
                                        tint = accent
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = DeepMindColorPalette.current.background,
                                titleContentColor = DeepMindColorPalette.current.txtColor
                            )
                        )
                    }, content = {
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .background(DeepMindColorPalette.current.background).padding(it)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {

                                AnimatedVisibility(visible = true) {
                                    Text(
                                        text = title.value,
                                        color = DeepMindColorPalette.current.txtColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                }

                                LaunchedEffect(Unit) {
                                    delay(1.seconds)
                                    title.value = "이름을 입력해주세요."
                                    showView.value = true
                                }

                                Spacer(modifier = Modifier.height(40.dp))

                                AnimatedVisibility(visible = showView.value) {

                                    OutlinedTextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = name.value,
                                        onValueChange = { textVal: String -> name.value = textVal },
                                        label = { Text("이름") },
                                        placeholder = { Text("이름") },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.AccountCircle,
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
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                AnimatedVisibility(visible = name.value != "") {
                                    title.value = "닉네임을 입력해주세요."

                                    OutlinedTextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = nickName.value,
                                        onValueChange = { textVal: String ->
                                            nickName.value = textVal
                                        },
                                        label = { Text("닉네임") },
                                        placeholder = { Text("닉네임") },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Person,
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
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                AnimatedVisibility(visible = nickName.value != "") {
                                    title.value = "휴대폰 번호를 입력해주세요."

                                    OutlinedTextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = phoneNumber.value,
                                        onValueChange = { textVal: String ->
                                            phoneNumber.value = textVal
                                        },
                                        label = { Text("휴대폰 번호") },
                                        placeholder = { Text("휴대폰 번호") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Phone,
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
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                AnimatedVisibility(visible = phoneNumber.value != "") {
                                    title.value = "생년월일을 선택해주세요."

                                    Surface(modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(5.dp)
                                        .shadow(5.dp),
                                        color= DeepMindColorPalette.current.btnColor,
                                        shape = RoundedCornerShape(size = 30f),
                                        content = {
                                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
                                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
                                                    Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = DeepMindColorPalette.current.txtColor)
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(text = "생년월일", color = DeepMindColorPalette.current.txtColor)
                                                }

                                                Row(verticalAlignment = Alignment.CenterVertically){
                                                    TextButton(onClick = { showDatePicker.value = true }) {
                                                        Text(text = birthDay.value, color = accent)
                                                    }
                                                }

                                            }
                                        })
                                }

                                Spacer(modifier = Modifier.height(20.dp))


                                AnimatedVisibility(visible = birthDay.value != birthDayDefaultValue) {
                                    title.value = "이용약관을 읽어주세요."

                                    Column(horizontalAlignment = Alignment.Start) {
                                        Row(horizontalArrangement = Arrangement.Start) {
                                            Text(
                                                text = "이용약관을 읽어주세요.",
                                                color = DeepMindColorPalette.current.txtColor,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(5.dp))

                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = isLicenseAccepted.value,
                                                onCheckedChange = { isChecked ->
                                                    isLicenseAccepted.value = isChecked
                                                },
                                                colors = CheckboxDefaults.colors(
                                                    uncheckedColor = accent,
                                                    checkedColor = accent
                                                )
                                            )
                                            Text(
                                                text = "서비스 이용약관 (필수)",
                                                color = DeepMindColorPalette.current.txtColor,
                                                fontSize = 12.sp
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            Button(
                                                onClick = { },
                                                colors = ButtonDefaults.buttonColors(containerColor = DeepMindColorPalette.current.background)
                                            ) {
                                                Text("읽기", color = accent, fontSize = 12.sp)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(5.dp))

                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = isPrivacyLicenseAccepted.value,
                                                onCheckedChange = { isChecked ->
                                                    isPrivacyLicenseAccepted.value = isChecked
                                                },
                                                colors = CheckboxDefaults.colors(
                                                    uncheckedColor = accent,
                                                    checkedColor = accent
                                                )
                                            )
                                            Text(
                                                text = "개인정보 수집 및 이용방침 (필수)",
                                                color = DeepMindColorPalette.current.txtColor,
                                                fontSize = 12.sp
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            Button(
                                                onClick = { },
                                                colors = ButtonDefaults.buttonColors(containerColor = DeepMindColorPalette.current.background)
                                            ) {
                                                Text("읽기", color = accent, fontSize = 12.sp)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(5.dp))

                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = isSensitiveLicenseAccepted.value,
                                                onCheckedChange = { isChecked ->
                                                    isSensitiveLicenseAccepted.value = isChecked
                                                },
                                                colors = CheckboxDefaults.colors(
                                                    uncheckedColor = accent,
                                                    checkedColor = accent
                                                )
                                            )
                                            Text(
                                                text = "민감정보 수집 및 이용방침 (필수)",
                                                color = DeepMindColorPalette.current.txtColor,
                                                fontSize = 12.sp
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            Button(
                                                onClick = { },
                                                colors = ButtonDefaults.buttonColors(containerColor = DeepMindColorPalette.current.background)
                                            ) {
                                                Text("읽기", color = accent, fontSize = 12.sp)
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                AnimatedVisibility(visible = isLicenseAccepted.value && isPrivacyLicenseAccepted.value) {
                                    title.value = "다음 단계로 이동해주세요!"

                                    Button(
                                        onClick = {
                                            navController.navigate("UploadFeatureView") {
                                                popUpTo("SignUpView") {
                                                    inclusive = true
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        enabled = !name.value.isEmpty() && !nickName.value.isEmpty() && !birthDay.value.equals(birthDayDefaultValue) && !phoneNumber.value.isEmpty() && isLicenseAccepted.value && isPrivacyLicenseAccepted.value,
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

                                if(showDatePicker.value){
                                    DatePickerDialog(
                                        onDismissRequest = {
                                            showDatePicker.value = false
                                        },
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    val formatter = SimpleDateFormat("yy/MM/dd")
                                                    val calendar = Calendar.getInstance()
                                                    calendar.timeInMillis = datePickerState.selectedDateMillis!!
                                                    birthDay.value = formatter.format(calendar.time)
                                                    showDatePicker.value = false
                                                }
                                            ){
                                                Text("확인", color = accent)
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(
                                                onClick = {
                                                    showDatePicker.value = false
                                                }
                                            ){
                                                Text("취소", color = accent)
                                            }
                                        },

                                        tonalElevation = 5.dp) {
                                        DatePicker(state = datePickerState, colors = DatePickerDefaults.colors(
                                            containerColor = DeepMindColorPalette.current.background,
                                            titleContentColor = DeepMindColorPalette.current.txtColor,
                                            weekdayContentColor = DeepMindColorPalette.current.txtColor,
                                            todayContentColor = accent,
                                            selectedDayContentColor = white,
                                            selectedYearContentColor = white,
                                            todayDateBorderColor = accent,
                                            selectedYearContainerColor = accent,
                                            selectedDayContainerColor = accent
                                        ))
                                    }
                                }

                                if(showProgress.value){

                                }
                            }
                        }

                    })

                }
            }
        }
    }
}