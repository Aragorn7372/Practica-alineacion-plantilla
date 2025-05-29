package org.example.newteamultimateedition.alineacion.dao

import java.time.LocalDate
import java.time.LocalDateTime

data class AlineacionEntity(
    val id: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val juegoDate: LocalDate,
    val idEntrenador: Long
) {
}
