package com.wbrawner.flayre.server

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.transactions.transaction


object FlayreDatabase {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())

    private val config = HikariConfig().apply {
        jdbcUrl         = "jdbc:mysql://${appConfig.property("flayre.database.host").getString()}/${appConfig.property("flayre.database.name").getString()}"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username        = "${appConfig.property("flayre.database.user").getString()}"
        password        = "${appConfig.property("flayre.database.password").getString()}"
        maximumPoolSize = 10
    }
    fun init() {
        Database.connect(HikariDataSource(config))
        transaction {
            SchemaUtils.create (Apps, Events)
        }
    }
}
