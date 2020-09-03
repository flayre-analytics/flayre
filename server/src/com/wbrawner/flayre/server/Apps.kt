package com.wbrawner.flayre.server

import com.wbrawner.flayre.App
import com.wbrawner.flayre.randomId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Apps : Table() {
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id)
    val id = varchar("id", 32)
    val name = varchar("name", 256)
}

fun ResultRow.toApp() = App(
    get(Apps.id),
    get(Apps.name)
)

data class AppRequest(val name: String)

fun Route.appsRoutes() {
    route("/apps") {
        get {
            call.respond(transaction {
                Apps.selectAll().map { it.toApp() }
            })
        }

        post {
            val app = call.receive<AppRequest>()
            call.respond(
                transaction {
                    Apps.insert {
                        it[id] = randomId(32)
                        it[name] = app.name
                    }
                        .resultedValues
                        ?.firstOrNull()
                        ?.toApp()
                        ?: HttpStatusCode.InternalServerError
                }
            )
        }

        get("{appId}") {
            Apps.select { Apps.id.eq(call.parameters["appId"]!!) }
                .singleOrNull()
                ?.let {
                    call.respond(it)
                }
                ?: call.respond(HttpStatusCode.NotFound)
        }

        patch("{appId}") {
            val appUpdate = call.receive<AppRequest>()
            val appId = call.parameters["appId"]!!
            val updated = transaction {
                Apps.update({ Apps.id.eq(appId) }) {
                    it[name] = appUpdate.name
                }
            }
            val status = if (updated > 0) {
                HttpStatusCode.NoContent
            } else {
                HttpStatusCode.NotFound
            }
            call.respond(status)
        }

        delete("{appId}") {
            val deleted = transaction {
                Apps.deleteWhere { Apps.id.eq(call.parameters["appId"]!!) }
            }
            val status = if (deleted > 0) {
                HttpStatusCode.NoContent
            } else {
                HttpStatusCode.NotFound
            }
            call.respond(status)
        }
    }
}
