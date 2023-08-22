package com.cj.deepmind.frameworks.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class :: ${modelClass::class.java.simpleName}")
    }
}