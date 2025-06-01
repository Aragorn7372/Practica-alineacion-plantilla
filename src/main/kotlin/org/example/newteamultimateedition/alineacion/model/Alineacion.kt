package org.example.newteamultimateedition.alineacion.model

import org.example.newteamultimateedition.personal.models.Entrenador
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Clase que representa una alineación
 * @property id identificador de la alineación
 * @property personalList Lista de [LineaAlineacion]
 * @property createdAt Fecha de creación de la alineación
 * @property updatedAt Fecha de actualización de la alineación
 * @property juegoDate Fecha de la convocatoria asignada a la alineación
 * @param entrenador Entrenador asignado a dicha alineación
 * @param descripcion Descripción de la alineación
 */
data class Alineacion(
    val id: Long,
    val personalList: List<LineaAlineacion>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val juegoDate: LocalDate,
    val entrenador: Entrenador,
    val descripcion: String,
)
