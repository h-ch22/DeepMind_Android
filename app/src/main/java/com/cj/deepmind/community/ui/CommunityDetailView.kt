package com.cj.deepmind.community.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.cj.deepmind.community.helper.CommunityHelper
import com.cj.deepmind.community.models.CommunityArticleDataModel

@Composable
fun CommunityDetailView(
    data: CommunityArticleDataModel
){
    val helper = CommunityHelper()
    var comment = remember {
        mutableStateOf("")
    }

    var showProgress = remember{
        mutableStateOf(false)
    }
}