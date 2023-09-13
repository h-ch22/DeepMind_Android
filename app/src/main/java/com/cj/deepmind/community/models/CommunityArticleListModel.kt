package com.cj.deepmind.community.models

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityArticleListModel(
    data: CommunityArticleDataModel,
    modifier: Modifier = Modifier,
    onClickStartSource: () -> Unit
){
    DeepMindTheme {
        Card(shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = DeepMindColorPalette.current.btnColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            onClick = {
                onClickStartSource()
            }) {
            Surface(modifier = Modifier.fillMaxWidth(),
                color = DeepMindColorPalette.current.btnColor) {
                Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.Center) {
                    Column {
                        Text(text = data.title,
                            color = DeepMindColorPalette.current.txtColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp)

                        Text(text = data.nickName, fontSize = 12.sp, color = gray)

                        Text(text = "${data.board} | ${data.createDate}", fontSize = 12.sp, color = gray)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(text = data.commentCount.toString(), color = accent)
                }
            }
        }

    }
}

@Preview
@Composable
fun CommunityArticleListModel_previews(){
    CommunityArticleListModel(
        data = CommunityArticleDataModel(),
        onClickStartSource = {}
    )
}