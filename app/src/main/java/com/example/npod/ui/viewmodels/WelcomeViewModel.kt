package com.example.npod.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {
    private val _inWelcomeContentLiveData = MutableLiveData(true)
    val inWelcomeContentLiveData: LiveData<Boolean> = _inWelcomeContentLiveData

    private val _canSkipWelcomeLiveData = MutableLiveData(true)
    val canSkipWelcomeLiveData: LiveData<Boolean> = _canSkipWelcomeLiveData

    fun skipWelcome() {
        closeWelcomeFlow()
    }

    fun goToApp() {
        closeWelcomeFlow()
    }

    fun cantSkip() {
        _canSkipWelcomeLiveData.value = false
    }

    fun canSkip() {
        _canSkipWelcomeLiveData.value = true
    }

    private fun closeWelcomeFlow() {
        _inWelcomeContentLiveData.value = false
    }
}