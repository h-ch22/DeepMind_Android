package com.cj.deepmind.community.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cj.deepmind.community.helper.CommunityHelper
import com.cj.deepmind.community.models.CommunityArticleDataModel
import com.cj.deepmind.community.models.CommunityArticleListModel
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent

@Composable
fun CommunityListView() {
    val helper = CommunityHelper()

    var articleList = remember {
        mutableStateListOf<CommunityArticleDataModel>()
    }

    val showProgress = remember {
        mutableStateOf(true)
    }

    DeepMindTheme {
        Surface(
            color = DeepMindColorPalette.current.background,
            modifier = Modifier.fillMaxSize()
        ) {
            LaunchedEffect(key1 = true) {
                helper.getAllArticles {
                    if (it) {
                        articleList = helper.getAllArticles().toMutableStateList()
                    }

                    showProgress.value = false
                }
            }

            if (showProgress.value) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = accent)
                }
            } else {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn {
                        itemsIndexed(
                            articleList.toList()
                        ) { _, item ->
                            CommunityArticleListModel(data = item)
                        }
                    }

                }
            }


        }
    }
}

@Preview
@Composable
fun CommunityListView_previews() {
    CommunityListView()
}