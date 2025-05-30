package org.example.newteamultimateedition.alineacion.model

import org.example.newteamultimateedition.personal.models.Entrenador
import java.time.LocalDate
import java.time.LocalDateTime

data class Alineacion(
    val id: Long,
    val personalList: List<LineaAlineacion>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val juegoDate: LocalDate,
    val entrenador: Entrenador,
    val descripcion: String,
)
