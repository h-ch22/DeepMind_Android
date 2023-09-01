package com.cj.deepmind.inspection.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class InspectionEssentialQuestionViewModel: ViewModel() {
    var answer_House = mutableListOf(
        "Y", "Y", "", "Y", "", "", "", "", "", "", "Y", "", "Y", "Y"
    )

    var answer_Tree = mutableListOf(
        "Y", "Y", "", "Y", "", "Y", "", "Y", "", "", "Y", "Y", "Y", "Y"
    )

    var answer_Person_1 = mutableListOf(
        "", "Y", "", "", "Y", "Y", "", "", "Y", "Y", "Y", "Y", "Y", "", ""
    )

    var answer_Person_2 = mutableListOf(
        "", "Y", "", "", "Y", "Y", "", "", "Y", "Y", "Y", "Y", "Y", "", ""
    )
}