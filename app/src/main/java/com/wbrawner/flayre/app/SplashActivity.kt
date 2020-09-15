package com.wbrawner.flayre.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.wbrawner.flayre.app.di.KEY_AUTH
import com.wbrawner.flayre.app.di.KEY_SERVER

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.contains(KEY_SERVER) && sharedPreferences.contains(KEY_AUTH)) {
            (application as FlayreApplication).resetAppComponent()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            setContentView(R.layout.activity_splash)
        }
    }
}