package com.wbrawner.flayre.android

import android.content.Context
import androidx.startup.Initializer

class FlayreInitializer: Initializer<Flayre> {
    override fun create(context: Context): Flayre {
        Flayre.init(context)
        return Flayre
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}