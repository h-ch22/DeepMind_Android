package com.cj.deepmind.inspection.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.inspection.models.InspectionEssentialQuestionType
import com.cj.deepmind.inspection.models.InspectionEssentialQuestionViewModel
import com.cj.deepmind.inspection.models.InspectionTypeModel
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent

@Composable
fun InspectionEssentialQuestionView(type: InspectionTypeModel, viewModel: InspectionEssentialQuestionViewModel, navController: NavController){
    val questions_House = remember {
        mutableStateListOf(
            InspectionEssentialQuestionType(question = "이 집은 도심에 있는 집인가요?", isYesNoQuestion = true),
            InspectionEssentialQuestionType(question = "이 집 가까이에 다른 집이 있나요?", isYesNoQuestion = true),
            InspectionEssentialQuestionType(question = "이 그림에서 날씨는 어떠한가요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "이 집은 당신에게서 멀리 있는 집인가요?", isYesNoQuestion = true),
            InspectionEssentialQuestionType(question = "이 집에 살고 있는 가족은 몇 사람인가요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "이 집에 살고 있는 가족은 어떤 사람들인가요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "가정의 분위기는 어떤가요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "이 집을 보면 누가 생각나나요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "당신은 이 집의 어느 방에 살고 싶은가요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "당신은 누구와 이 집에 살고 싶나요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "당신의 집은 이 집보다 큰가요?", isYesNoQuestion = true),
            InspectionEssentialQuestionType(question = "이 집을 그릴 때 누구의 집을 생각하고 그렸나요?", isYesNoQuestion = false),
            InspectionEssentialQuestionType(question = "이 그림에 첨가하여 더 그리고 싶은 것이 있나요?", isYesNoQuestion = true),
            InspectionEssentialQuestionType(question = "당신이 그리려고 했던 대로 잘 그려졌나요?", isYesNoQuestion = true)
        )
    }

    val questions_Tree = remember{
        mutableStateListOf(
            InspectionEssentialQuestionType(question= "이 나무는 상록수인가요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 나무는 숲 속에 있나요? (아니오= 한 그루만 있음)", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 그림의 날씨는 어떠한가요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "바람이 불고 있나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 나무는 몇 년쯤 된 나무인가요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 나무는 살아 있나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 나무가 죽었다면 언제쯤 말라죽었나요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 나무는 강한 나무인가요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 나무는 당신에게 누구를 생각나게 하나요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 나무는 남자와 여자 중 어느 쪽을 닮았나요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 나무는 당신으로부터 멀리 있나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 나무는 당신보다 큰가요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 그림에 더 첨가하여 그리고 싶은 것이 있나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "당신이 그리려고 했던 대로 잘 그려졌나요?", isYesNoQuestion= true)
        )
    }

    val questions_Person = remember{
        mutableStateListOf(
            InspectionEssentialQuestionType(question= "이 사람의 나이는 몇 살인가요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 사람은 결혼을 했나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 사람의 가족은 몇 명인가요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 사람의 가족은 어떤 사람들인가요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 사람의 신체는 건강한가요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 사람은 친구가 많나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 사람의 친구는 어떤 친구들인가요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 사람의 성질은 어떠한가요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 사람은 행복한가요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "이 사람에게 필요한게 있나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "당신은 이 사람이 좋나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "당신은 이 사람처럼 되고 싶나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "당신은 이 사람과 함께 생활하고 친구가 되고 싶나요?", isYesNoQuestion= true),
            InspectionEssentialQuestionType(question= "당신은 이 사람을 그릴 때 누구를 생각하고 있었나요?", isYesNoQuestion= false),
            InspectionEssentialQuestionType(question= "이 사람은 누구를 닮았나요?", isYesNoQuestion= false)
        )
    }

    var answer_House = remember {
        mutableStateListOf(
            "Y", "Y", "", "Y", "", "", "", "", "", "", "Y", "", "Y", "Y"
        )
    }

    var answer_Tree = remember{
        mutableStateListOf(
            "Y", "Y", "", "Y", "", "Y", "", "Y", "", "", "Y", "Y", "Y", "Y"
        )
    }

    var answer_Person_1 = remember {
        mutableStateListOf(
            "", "Y", "", "", "Y", "Y", "", "", "Y", "Y", "Y", "Y", "Y", "", ""
        )
    }

    var answer_Person_2 = remember{
        mutableStateListOf(
            "", "Y", "", "", "Y", "Y", "", "", "Y", "Y", "Y", "Y", "Y", "", ""
        )
    }

    val scrollState = rememberScrollState()

    DeepMindTheme {
        LaunchedEffect(key1 = true){
            when(type){
                InspectionTypeModel.HOUSE -> answer_House = viewModel.answer_House.toMutableStateList()
                InspectionTypeModel.TREE -> answer_Tree = viewModel.answer_Tree.toMutableStateList()
                InspectionTypeModel.PERSON_1 -> answer_Person_1 = viewModel.answer_Person_1.toMutableStateList()
                InspectionTypeModel.PERSON_2 -> answer_Person_2 = viewModel.answer_Person_2.toMutableStateList()
            }
        }

        Surface(modifier = Modifier
            .fillMaxSize()
            .background(DeepMindColorPalette.current.background)) {
            Column(modifier = Modifier
                .padding(20.dp)
                .verticalScroll(scrollState)) {
                when(type){
                    InspectionTypeModel.HOUSE -> {
                        for(question in questions_House){
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
                                Text(text = question.question, color = DeepMindColorPalette.current.txtColor, modifier = Modifier.weight(0.8F))

                                if(question.isYesNoQuestion){
                                    Row(modifier = Modifier.weight(0.2F), verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = answer_House[questions_House.indexOf(question)] == "Y",
                                            onCheckedChange = { isChecked ->
                                                if(isChecked){
                                                    answer_House[questions_House.indexOf(question)] = "Y"
                                                } else{
                                                    answer_House[questions_House.indexOf(question)] = "N"
                                                }
                                            },
                                            colors = CheckboxDefaults.colors(
                                                uncheckedColor = accent,
                                                checkedColor = accent
                                            )
                                        )
                                        Text(
                                            text = "예",
                                            color = DeepMindColorPalette.current.txtColor,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            if(!question.isYesNoQuestion){
                                TextField(value = answer_House[questions_House.indexOf(question)], onValueChange = {
                                    answer_House[questions_House.indexOf(question)] = it
                                }, label = {
                                    Text(text = "답변")
                                })
                            }
                        }
                    }

                    InspectionTypeModel.TREE -> {
                        for(question in questions_Tree){
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
                                Text(text = question.question, color = DeepMindColorPalette.current.txtColor, modifier = Modifier.weight(0.8F))

                                if(question.isYesNoQuestion){
                                    Row(modifier = Modifier.weight(0.2F), verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = answer_Tree[questions_Tree.indexOf(question)] == "Y",
                                            onCheckedChange = { isChecked ->
                                                if(isChecked){
                                                    answer_Tree[questions_Tree.indexOf(question)] = "Y"
                                                } else{
                                                    answer_Tree[questions_Tree.indexOf(question)] = "N"
                                                }
                                            },
                                            colors = CheckboxDefaults.colors(
                                                uncheckedColor = accent,
                                                checkedColor = accent
                                            )
                                        )
                                        Text(
                                            text = "예",
                                            color = DeepMindColorPalette.current.txtColor,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            if(!question.isYesNoQuestion){
                                TextField(value = answer_Tree[questions_Tree.indexOf(question)], onValueChange = {
                                    answer_Tree[questions_Tree.indexOf(question)] = it
                                }, label = {
                                    Text(text = "답변")
                                })
                            }
                        }
                    }

                    InspectionTypeModel.PERSON_1,
                    InspectionTypeModel.PERSON_2 -> {
                        for(question in questions_Person){
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
                                Text(text = question.question, color = DeepMindColorPalette.current.txtColor, modifier = Modifier.weight(0.8F))

                                if(question.isYesNoQuestion){
                                    Row(modifier = Modifier.weight(0.2F), verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = if (type == InspectionTypeModel.PERSON_1) answer_Person_1[questions_Person.indexOf(question)] == "Y"
                                                    else answer_Person_2[questions_Person.indexOf(question)] == "Y",
                                            onCheckedChange = { isChecked ->
                                                if(isChecked){
                                                    if (type == InspectionTypeModel.PERSON_1) answer_Person_1[questions_Person.indexOf(question)] = "Y"
                                                    else answer_Person_2[questions_Person.indexOf(question)] = "Y"
                                                } else{
                                                    if (type == InspectionTypeModel.PERSON_1) answer_Person_1[questions_Person.indexOf(question)] = "N"
                                                    else answer_Person_2[questions_Person.indexOf(question)] = "N"
                                                }
                                            },
                                            colors = CheckboxDefaults.colors(
                                                uncheckedColor = accent,
                                                checkedColor = accent
                                            )
                                        )
                                        Text(
                                            text = "예",
                                            color = DeepMindColorPalette.current.txtColor,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            if(!question.isYesNoQuestion){
                                TextField(value = if (type == InspectionTypeModel.PERSON_1) answer_Person_1[questions_Person.indexOf(question)]
                                                else answer_Person_2[questions_Person.indexOf(question)],
                                    onValueChange = {
                                        if (type == InspectionTypeModel.PERSON_1) answer_Person_1[questions_Person.indexOf(question)] = it
                                        else answer_Person_2[questions_Person.indexOf(question)] = it
                                }, label = {
                                    Text(text = "답변")
                                })
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {
                    when(type){
                        InspectionTypeModel.HOUSE -> viewModel.answer_House = answer_House
                        InspectionTypeModel.TREE -> viewModel.answer_Tree = answer_Tree
                        InspectionTypeModel.PERSON_1 -> viewModel.answer_Person_1 = answer_Person_1
                        InspectionTypeModel.PERSON_2 -> viewModel.answer_Person_2 = answer_Person_2
                    }

                    navController.popBackStack()
                }) {
                    Text(text = "완료")
                }
            }
        }
    }
}

@Preview
@Composable
fun InspectionEssentialQuestionView_previews(){
    InspectionEssentialQuestionView(type = InspectionTypeModel.HOUSE, viewModel = InspectionEssentialQuestionViewModel(), navController = rememberNavController())
}