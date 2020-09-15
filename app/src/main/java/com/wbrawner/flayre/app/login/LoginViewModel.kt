package com.wbrawner.flayre.app.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wbrawner.flayre.app.network.FlayreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel: ViewModel() {
    @Inject
    lateinit var flayreRepository: FlayreRepository

    fun loadApps() {
        viewModelScope.launch {
            flayreRepository.loadApps()
        }
    }
}