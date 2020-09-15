package com.wbrawner.flayre.app.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wbrawner.flayre.app.network.FlayreRepository
import com.wbrawner.flayre.app.network.FlayreService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

const val KEY_SERVER = "com.wbrawner.flayre.app.SERVER"
const val KEY_AUTH = "com.wbrawner.flayre.app.AUTH"

@Module
class AppModule {
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Basic ${sharedPreferences.getString(KEY_AUTH, "")}"
                        )
                        .build()
                )
            }
            .build()

    @Singleton
    @Provides
    fun provideFlayreService(sharedPreferences: SharedPreferences, moshi: Moshi, okHttpClient: OkHttpClient): FlayreService =
        Retrofit.Builder()
            .baseUrl(sharedPreferences.getString(KEY_SERVER, null)?: "")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(FlayreService::class.java)

    @Singleton
    @Provides
    fun provideAppsRepository(flayreService: FlayreService) = FlayreRepository(flayreService)
}