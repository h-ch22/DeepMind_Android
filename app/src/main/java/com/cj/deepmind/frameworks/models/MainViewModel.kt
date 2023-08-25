package com.cj.deepmind.frameworks.models

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _showInspectionDrawingView = mutableStateOf(false)

    val showInspectionDrawingView: State<Boolean> = _showInspectionDrawingView

    fun setInspectionDrawingViewState(state: Boolean){
        _showInspectionDrawingView.value = state
    }
}