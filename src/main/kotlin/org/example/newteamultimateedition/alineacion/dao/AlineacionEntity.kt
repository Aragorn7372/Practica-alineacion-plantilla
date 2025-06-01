package org.example.newteamultimateedition.alineacion.dao

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Clase que representa a una Alineación en forma de Entidad, para la base de datos.
 * @property id Identificador del objeto
 * @property createdAt Fecha de creación de la alineación
 * @property updatedAt Fecha de actualización de la alineación
 * @property juegoDate Fecha del partido jugado
 * @property idEntrenador Identificador del entrenador asignado a esta alienación
 * @property descripcion Descripción de la alineación
 */
data class AlineacionEntity(
    val id: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val juegoDate: LocalDate,
    val idEntrenador: Long,
    val descripcion: String,
)