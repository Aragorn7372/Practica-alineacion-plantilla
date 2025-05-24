package org.example.newteamultimateedition.alineacion.model

import java.time.LocalDate
import java.time.LocalDateTime

import java.util.*

data class Alineacion(
    val id: Long,
    val personalList: List<UUID>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val juegoDate: LocalDate
)
