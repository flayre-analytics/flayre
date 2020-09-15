package com.wbrawner.flayre.app

import android.app.Application
import com.wbrawner.flayre.app.di.AppComponent
import com.wbrawner.flayre.app.di.DaggerAppComponent

class FlayreApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        resetAppComponent()
    }

    fun resetAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}