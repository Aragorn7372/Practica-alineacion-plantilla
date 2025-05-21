package org.example.newteamultimateedition.personal.dao

import java.time.LocalDate
import java.time.LocalDateTime


data class PersonaEntity(
    val id: Long,
    val nombre: String,
    val apellidos: String,
    val fechaNacimiento: LocalDate,
    val fechaIncorporacion: LocalDate,
    val salario: Double,
    val pais: String,
    val rol: String,
    val especialidad: String?,
    val posicion: String?,
    val dorsal: Int?,
    val altura: Double?,
    val peso: Double?,
    val goles: Int?,
    val partidosJugados: Int?,
    val minutosJugados: Int?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val imagen: String
)
