package com.cj.deepmind.userManagement.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
import com.cj.deepmind.userManagement.models.SignUpAlertModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.time.Duration.Companion.seconds

fun getAlertTitle(alertModel : SignUpAlertModel) : String{
    when(alertModel){
        SignUpAlertModel.EMPTY_FIELD -> return "공백 필드"
        SignUpAlertModel.INCORRECT_EMAIL_TYPE -> return "잘못된 E-Mail 형식"
        SignUpAlertModel.MISMATCH_PASSWORD -> return "비밀번호 불일치"
        SignUpAlertModel.REQUIRE_ACCEPT_LICENSE -> return "이용약관 동의 필요"
        SignUpAlertModel.WEAK_PASSWORD -> return "위험한 비밀번호"
        SignUpAlertModel.ERROR -> return "오류"
    }
}

fun getAlertContents(alertModel: SignUpAlertModel) : String{
    when(alertModel){
        SignUpAlertModel.EMPTY_FIELD -> return "모든 필드를 입력해주세요."
        SignUpAlertModel.INCORRECT_EMAIL_TYPE -> return "잘못된 E-Mail 형식입니다."
        SignUpAlertModel.MISMATCH_PASSWORD -> return "비밀번호와 비밀번호 확인이 일치하지 않습니다."
        SignUpAlertModel.REQUIRE_ACCEPT_LICENSE -> return "필수 이용약관을 모두 읽고 동의해주세요."
        SignUpAlertModel.WEAK_PASSWORD -> return "보안을 위해 6자리 이상의 비밀번호를 설정해주세요."
        SignUpAlertModel.ERROR -> return "회원가입을 처리하는 중 문제가 발생하였습니다.\n이미 가입된 E-Mail이거나 네트워크 상태가 불안정하였을 수 있습니다."
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView() {
    val title = remember {
        mutableStateOf("반가워요!")
    }

    val email = remember{
        mutableStateOf("")
    }

    val password = remember{
        mutableStateOf("")
    }

    val checkPassword = remember {
        mutableStateOf("")
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

    val alertModel = remember{
        mutableStateOf<SignUpAlertModel?>(null)
    }

    val showAlert = remember{
        mutableStateOf(false)
    }

    val navController = rememberNavController()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())


    DeepMindTheme {
        NavHost(navController = navController, startDestination = "SignUpView") {
            composable(route = "UploadFeatureView") {
                UploadFeatureView()
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
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DeepMindColorPalette.current.background)
                                .padding(it)
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
                                    title.value = "E-Mail을 입력해주세요."
                                    showView.value = true
                                }

                                Spacer(modifier = Modifier.height(40.dp))

                                AnimatedVisibility(visible = showView.value){
                                    OutlinedTextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = email.value,
                                        onValueChange = { textVal : String ->
                                            email.value = textVal
                                        },
                                        label = { Text("E-Mail") },
                                        placeholder = { Text("E-Mail") } ,
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
                                            selectionColors = TextSelectionColors(handleColor = accent, backgroundColor = accent.copy(alpha = 0.5f))
                                        ),
                                        maxLines = 1,
                                        singleLine = true
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                AnimatedVisibility(visible = email.value != "") {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                                        OutlinedTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            value = password.value,
                                            onValueChange = { textVal : String ->
                                                password.value = textVal
                                            },
                                            label = { Text("비밀번호") },
                                            placeholder = { Text("비밀번호") } ,
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
                                                selectionColors = TextSelectionColors(handleColor = accent, backgroundColor = accent.copy(alpha = 0.5f))
                                            ),
                                            maxLines = 1,
                                            singleLine = true,
                                            visualTransformation = PasswordVisualTransformation()
                                        )

                                        Spacer(modifier = Modifier.height(5.dp))

                                        Text("보안을 위해 6자리 이상의 비밀번호를 설정해주세요.", fontSize = 12.sp, color = gray)

                                        Spacer(modifier = Modifier.height(20.dp))

                                        OutlinedTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            value = checkPassword.value,
                                            onValueChange = { textVal : String ->
                                                checkPassword.value = textVal
                                            },
                                            label = { Text("한번 더") },
                                            placeholder = { Text("한번 더") } ,
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
                                                selectionColors = TextSelectionColors(handleColor = accent, backgroundColor = accent.copy(alpha = 0.5f))
                                            ),
                                            maxLines = 1,
                                            singleLine = true,
                                            visualTransformation = PasswordVisualTransformation()
                                        )
                                    }

                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                AnimatedVisibility(visible = checkPassword.value != "") {
                                    title.value = "이름을 입력해주세요."
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

                                AnimatedVisibility(visible = isLicenseAccepted.value && isPrivacyLicenseAccepted.value && isSensitiveLicenseAccepted.value) {
                                    title.value = "다음 단계로 이동해주세요!"

                                    Button(
                                        onClick = {
                                            if(!email.value.contains("@")){
                                                alertModel.value = SignUpAlertModel.INCORRECT_EMAIL_TYPE
                                                showAlert.value = true
                                            } else if(password.value.length < 6){
                                                alertModel.value = SignUpAlertModel.WEAK_PASSWORD
                                                showAlert.value = true
                                            } else if(password.value != checkPassword.value){
                                                alertModel.value = SignUpAlertModel.MISMATCH_PASSWORD
                                                showAlert.value = true
                                            } else if(email.value == "" || password.value == "" || name.value == "" || nickName.value == "" || checkPassword.value == "" || birthDay.value == birthDayDefaultValue || phoneNumber.value == ""){
                                                alertModel.value = SignUpAlertModel.EMPTY_FIELD
                                                showAlert.value = true
                                            } else if(!isLicenseAccepted.value || !isPrivacyLicenseAccepted.value || !isSensitiveLicenseAccepted.value){
                                                alertModel.value = SignUpAlertModel.REQUIRE_ACCEPT_LICENSE
                                                showAlert.value = true
                                            } else{
                                                showProgress.value = true

                                                helper.signUp(email.value, password.value, name.value, nickName.value, phoneNumber.value, birthDay.value){
                                                    if(it){
                                                        navController.navigate("UploadFeatureView") {
                                                            popUpTo("SignUpView") {
                                                                inclusive = true
                                                            }
                                                        }
                                                    } else{
                                                        showProgress.value = false
                                                        alertModel.value = SignUpAlertModel.ERROR
                                                        showAlert.value = true
                                                    }
                                                }

                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        enabled = !email.value.isEmpty() && !password.value.isEmpty() && !checkPassword.value.isEmpty() && !name.value.isEmpty() && !nickName.value.isEmpty() && !birthDay.value.equals(birthDayDefaultValue) && !phoneNumber.value.isEmpty() && isLicenseAccepted.value && isPrivacyLicenseAccepted.value && !showProgress.value,
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
                                    Dialog(
                                        onDismissRequest = { showProgress.value = false },
                                        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                                    ) {
                                        Box(
                                            contentAlignment= Center,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .background(
                                                    DeepMindColorPalette.current.background.copy(
                                                        alpha = 0.7f
                                                    ),
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
                                            Text(getAlertTitle(alertModel.value!!))
                                        },
                                        text = {
                                            Text(getAlertContents(alertModel.value!!))
                                        },
                                        icon = {
                                            Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
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

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SignUpView_previews(){
    SignUpView()
}