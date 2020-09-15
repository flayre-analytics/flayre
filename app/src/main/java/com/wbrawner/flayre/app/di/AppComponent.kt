package com.wbrawner.flayre.app.di

import android.content.Context
import com.wbrawner.flayre.app.MainViewModel
import com.wbrawner.flayre.app.SplashActivity
import com.wbrawner.flayre.app.login.LoginViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    val context: Context

    fun inject(loginViewModel: LoginViewModel)
    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}