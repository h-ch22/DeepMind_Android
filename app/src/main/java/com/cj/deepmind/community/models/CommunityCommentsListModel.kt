package com.cj.deepmind.community.models

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.white

@Composable
fun CommunityCommentsListModel(data: CommunityCommentDataModel, creatorUID: String){
    DeepMindTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = DeepMindColorPalette.current.background) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = data.nickName, color = DeepMindColorPalette.current.txtColor, fontWeight = FontWeight.SemiBold)

                    if(data.author == creatorUID){
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "작성자", modifier = Modifier
                            .background(color = accent, shape = RoundedCornerShape(15.dp))
                            .padding(5.dp), color = white)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(text = data.uploadDate, fontSize = 12.sp, color = gray)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(data.contents, color = DeepMindColorPalette.current.txtColor, textAlign = TextAlign.Start)
            }
        }
    }
}

@Preview
@Composable
fun CommunityCommentsListModel_previews(){
    CommunityCommentsListModel(data = CommunityCommentDataModel(), creatorUID = "")
}