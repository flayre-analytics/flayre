package com.wbrawner.flayre.app

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wbrawner.flayre.App
import com.wbrawner.flayre.Event
import com.wbrawner.flayre.app.di.AppComponent
import com.wbrawner.flayre.app.network.FlayreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : ViewModel() {
    @Inject
    lateinit var flayreRepository: FlayreRepository

    val apps = MediatorLiveData<List<App>>()
    val events = MediatorLiveData<List<Event>>()

    fun init(appComponent: AppComponent) {
        appComponent.inject(this)
        apps.addSource(flayreRepository.apps) {
            apps.postValue(it)
        }
        events.addSource(flayreRepository.events) {
            events.postValue(it)
        }
    }

    fun loadEvents() {
        viewModelScope.launch {
            flayreRepository.loadEvents()
        }
    }
}