package com.wbrawner.flayre.app.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wbrawner.flayre.App
import com.wbrawner.flayre.Event

class FlayreRepository(private val flayreService: FlayreService) {
    val apps: LiveData<List<App>> = MutableLiveData()
    val events: LiveData<List<Event>> = MutableLiveData()
    private val mutableApps = apps as MutableLiveData
    private val mutableEvents = events as MutableLiveData

    suspend fun loadApps() {
        flayreService.getApps().also {
            mutableApps.postValue(it)
        }
    }

    suspend fun getApp(id: String) = apps.value?.firstOrNull { it.id == id }
        ?: flayreService.getApp(id).also {  app ->
            mutableApps.postValue((apps.value?.toMutableList()?: mutableListOf()).also {
                it.add(app)
            })
        }

    suspend fun loadEvents() = flayreService.getEvents().also {
        mutableEvents.postValue(it)
    }
}