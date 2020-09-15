package com.wbrawner.flayre.app.network

import com.wbrawner.flayre.App
import com.wbrawner.flayre.Event
import retrofit2.http.*

interface FlayreService {
    @GET("/api/apps")
    suspend fun getApps(): List<App>

    @GET("/api/apps/{id}")
    suspend fun getApp(@Path("id") id: String): App

    @POST("/api/apps")
    suspend fun newApp(@Body name: String): App

    @PATCH("/api/apps/{id}")
    suspend fun updateApp(@Path("id") id: String, @Body name: String)

    @DELETE("/api/apps/{id}")
    suspend fun deleteApp(@Path("id") id: String)

    @GET("/api/events")
    suspend fun getEvents(): List<Event>
}