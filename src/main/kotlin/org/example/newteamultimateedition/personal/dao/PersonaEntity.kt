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
    val especialidad: String?=null,
    val posicion: String?=null,
    val dorsal: Int?=null,
    val altura: Double?=null,
    val peso: Double?=null,
    val goles: Int?=null,
    val partidosJugados: Int?=null,
    val minutosJugados: Int?=null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val imagen: String
)
