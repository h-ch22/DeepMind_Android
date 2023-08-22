package com.cj.deepmind.frameworks.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _showModalSheet = mutableStateOf(false)
    private val _showBottomBar = mutableStateOf(true)
    private val _showInspectionDrawingView = mutableStateOf(false)

    val showModalSheet: State<Boolean> = _showModalSheet
    val showBottomBar: State<Boolean> = _showBottomBar
    val showInspectionDrawingView: State<Boolean> = _showInspectionDrawingView

    fun setModalSheetState(state: Boolean){
        _showModalSheet.value = state
    }

    fun setBottomBarState(state: Boolean){
        _showBottomBar.value = state
    }

    fun setInspectionDrawingViewState(state: Boolean){
        _showInspectionDrawingView.value = state
    }
}