package com.wbrawner.flayre

import kotlin.random.Random

private val characters = ('a'..'z') + ('A'..'Z') + (0..9)
fun randomId(length: Int): String {
    val id = StringBuilder("")
    while (id.length < length) {
        id.append(characters[Random.nextInt(0, characters.size)])
    }
    return id.toString()
}