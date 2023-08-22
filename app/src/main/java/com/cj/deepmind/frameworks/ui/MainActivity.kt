package com.cj.deepmind.frameworks.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.cj.deepmind.frameworks.models.MainViewModel
import com.cj.deepmind.frameworks.models.MainViewModelFactory
import com.cj.deepmind.ui.theme.DeepMindTheme


class MainActivity : ComponentActivity() {
    private val viewModel by lazy{
        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainView(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
fun MainActivity_previews(){
    DeepMindTheme {
        Surface {
            MainActivity()
        }
    }
}