package org.example.newteamultimateedition.personal.dao

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Clase que representa a una Persona en forma de Entidad, para la base de datos.
 * @property id Identificador del objeto
 * @property nombre Nombre de la persona
 * @property apellidos Apellidos de la persona
 * @property fechaNacimiento Fecha de nacimiento de la persona
 * @property fechaIncorporacion Fecha de incorporacion al equipo de la persona
 * @property pais Pais de origen de la persona
 * @property especialidad Especializacion de la persona
 * @property salario Salario de la persona
 * @property isDeleted Campo de borrado logico de la persona
 * @property posicion Posicion de la que juega la persona
 * @property dorsal Dorsal de la persona
 * @property altura Altura de la persona
 * @property peso Peso de la persona
 * @property goles Número de goles que ha marcado la persona
 * @property partidosJugados Número de partidos jugados por la persona
 * @property minutosJugados Número de partidos jugados por la persona
 * @property createdAt [LocalDateTime] que representa la fecha de creación de la persona
 * @property updatedAt [LocalDateTime] que representa la fecha de actualización de la persona
 * @property isDeleted Propiedad que denota si está o no borrada la persona de la base de datos
 */
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
    val imagen: String,
    @get:JvmName("getIsDeleted")
    val isDeleted: Boolean
)
