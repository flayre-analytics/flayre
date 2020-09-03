package com.wbrawner.flayre

import java.util.*

class Event(
    val id: String = randomId(32),
    val appId: String,
    val date: Date,
    val type: InteractionType,
    val userAgent: String? = null, // For web use only
    val platform: String? = null,
    val manufacturer: String? = null,
    val model: String? = null,
    val version: String? = null,
    val locale: String? = null,
    val sessionId: String? = null,
    val data: String? = null
) {
    enum class InteractionType {
        VIEW,
        CLICK,
        ERROR,
        CRASH
    }
}

