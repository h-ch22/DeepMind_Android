package com.cj.deepmind.userManagement.models

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cj.deepmind.ui.theme.DeepMindTheme
import com.cj.deepmind.ui.theme.orange
import com.cj.deepmind.ui.theme.white

@Composable
fun ProBadgeView(){
    DeepMindTheme {
        Box {
            Text(text = "PRO",
                fontWeight = FontWeight.SemiBold,
                fontSize=8.sp,
                textAlign = TextAlign.Center,
                color = white,
                modifier = Modifier
                    .width(30.dp)
                    .align(Alignment.Center)
                    .background(orange, RoundedCornerShape(5.dp))
            )
        }
    }
}

@Preview
@Composable
fun ProBadgeView_previews(){
    ProBadgeView()
}