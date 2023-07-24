package com.cj.deepmind.frameworks.models

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel : ViewModel() {
    private val _isSignedIn = MutableStateFlow<Boolean>(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    fun checkSignInStatus(){
        val auth = FirebaseAuth.getInstance()
        _isSignedIn.value = auth.currentUser?.uid != null
    }
}