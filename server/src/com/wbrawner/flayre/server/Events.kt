package com.wbrawner.flayre.server

import com.wbrawner.flayre.Event
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

object Events : Table() {
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id)
    val id = varchar("id", 32)
    val appId = varchar("app_id", 32) references Apps.id
    val date = datetime("date")
    val userAgent = varchar("user_agent", 256).nullable()
    val platform = varchar("platform", 256).nullable()
    val manufacturer = varchar("manufacturer", 256).nullable()
    val model = varchar("model", 256).nullable()
    val version = varchar("version", 256).nullable()
    val locale = varchar("locale", 256)
    val sessionId = varchar("session_id", 32)
    val data = text("data")
    val type = varchar("type", 256)
}

fun ResultRow.toEvent() = Event(
    get(Events.id),
    get(Events.appId),
    Date(get(Events.date).toInstant(ZoneOffset.UTC).toEpochMilli()),
    Event.InteractionType.valueOf(get(Events.type)),
    get(Events.userAgent),
    get(Events.platform),
    get(Events.manufacturer),
    get(Events.model),
    get(Events.version),
    get(Events.locale),
    get(Events.sessionId),
    get(Events.data)
)

fun Route.authenticatedEventsRoutes() {
    get("/events") {
        val appId = call.parameters["appId"]
        if (appId.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid app id")
            return@get
        }
//        val from = call.parameters["from"]?.let {
//            Instant.parse(it)
//        }?: Calendar.getInstance().apply {
//            add(Calendar.DATE, -90)
//        }.toInstant()
//        val to = call.parameters["to"]?.let {
//            Instant.parse(it)
//        }?: Calendar.getInstance().apply {
//            add(Calendar.DATE, -90)
//        }.toInstant()

        call.respond(transaction {
            Events.select { Events.appId.eq(appId) }
                .map { it.toEvent() }
        })
    }
}

fun Route.unauthenticatedEventsRoutes() {
    post("/events") {

    }
}
