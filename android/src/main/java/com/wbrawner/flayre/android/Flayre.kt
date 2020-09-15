package com.wbrawner.flayre.android

import android.content.Context
import android.content.pm.PackageManager.GET_META_DATA
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.wbrawner.flayre.Event
import com.wbrawner.flayre.EventRequest
import com.wbrawner.flayre.Utils.randomId
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*

object Flayre {
    private val okHttpClient: OkHttpClient = OkHttpClient()
    private lateinit var appId: String
    private lateinit var serverUrl: String
    private val ioHandler = HandlerThread("Flayre").run {
        start()
        Handler(looper)
    }
    private val sessionId = randomId(32)

    fun init(context: Context) {
        context.packageManager.getApplicationInfo(context.packageName, GET_META_DATA)
            .metaData
            .apply {
                appId = getString("com.wbrawner.flayre.app_id", "")
                serverUrl = getString("com.wbrawner.flayre.server_url", "")
            }
    }

    fun trackView(page: String) = trackEvent(interaction(page, Event.InteractionType.VIEW))

    fun trackClick(element: String) = trackEvent(interaction(element, Event.InteractionType.CLICK))

    // TODO: Batch requests to send with fewer network requests
    private fun trackEvent(event: EventRequest) {
        ioHandler.post {
            try {
                okHttpClient.newCall(Request.Builder()
                    .post(event.toRequestBody())
                    .url(serverUrl)
                    .build()
                ).execute()
            } catch (e: Exception) {
                Log.e("Flayre", "Failed to send event", e)
            }
        }
    }

    private fun interaction(data: String, type: Event.InteractionType) = EventRequest(
        appId,
        Date(),
        type,
        "Android",
        Build.MANUFACTURER,
        Build.MODEL,
        Build.VERSION.RELEASE,
        Locale.getDefault().toLanguageTag(),
        sessionId,
        data
    )
}

fun EventRequest.toRequestBody(): RequestBody = this.toJson().toRequestBody("application/json".toMediaType())

fun EventRequest.toJson(): String = """
    {
        "appId": "$appId",
        "date": "$date",
        "type": "$type",
        "platform": "$platform",
        "manufacturer": "$manufacturer",
        "model": "$model",
        "version": "$version",
        "locale": "$locale",
        "sessionId": "$sessionId",
        "data": "$data"
    }
""".trimIndent().replace("\n", "")
