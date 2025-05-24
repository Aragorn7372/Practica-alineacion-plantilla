package org.example.newteamultimateedition.alineacion.model

import java.time.LocalDate
import java.time.LocalDateTime

import java.util.*

data class Alineacion(
    val id: Long,
    val personalList: List<Codigo>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val juegoDate: LocalDate
)
