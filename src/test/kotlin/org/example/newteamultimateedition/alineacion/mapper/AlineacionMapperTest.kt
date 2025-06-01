package org.example.newteamultimateedition.alineacion.mapper

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.LineaAlineacionEntity
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class AlineacionMapperTest {
  private val mapper = AlineacionMapper()
 private val entrenador = Entrenador(
  id = 1,
  nombre = "Entrenadora",
  apellidos = "hola",
  fechaNacimiento = LocalDate.parse("2020-01-01"),
  fechaIncorporacion = LocalDate.parse("2020-01-02"),
  salario = 3000.0,
  pais = "españa",
  especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  imagen = "oijsdoiasjd",
  isDeleted = false
 )
  private val alineacionEntity= AlineacionEntity(
   id= 1L,
   createdAt = LocalDateTime.now(),
   updatedAt = LocalDateTime.now(),
   juegoDate = LocalDate.now(),
   idEntrenador= 1L,
   descripcion = "aaa",
  )
 private val codigo= LineaAlineacion(
  id= UUID.fromString("7fa10f96-a0fc-4ccd-8aa5-5238a2642488"),
  idAlineacion = 1L,
  idPersona = 1L,
  posicion = 1
 )
 private val alineacion= Alineacion(
     1L,
     personalList = listOf(),
     juegoDate = alineacionEntity.juegoDate,
     updatedAt = alineacionEntity.updatedAt,
     createdAt = alineacionEntity.createdAt,
     entrenador = entrenador,
     descripcion = "aaa",
 )
 private val codigoEntity= LineaAlineacionEntity(
  id= "7fa10f96-a0fc-4ccd-8aa5-5238a2642488",
  personalId = 1L,
  alineacionId = 1L,
  posicion = 1
 )
@Test
 fun toDatabaseModel() {
  val result= mapper.toModel(alineacionEntity, listOf(),entrenador)
  assertTrue(result is Alineacion,"debe ser una alineacion")
  assertEquals(result,alineacion, "deberian ser iguales")
 }

@Test
 fun toEntity() {
  val result= mapper.toEntity(alineacion)
 assertTrue(result is AlineacionEntity,"debe ser una alineacionEntity")
 assertEquals(result,alineacionEntity, "deberian ser iguales")
 }

@Test
 fun testToEntity() {
  val result= mapper.toEntity(codigo)
 assertTrue(result is LineaAlineacionEntity,"debe ser un codigoAlineacionEntity")
 assertEquals(result,codigoEntity, "deberian ser iguales")
 }

@Test
 fun toModel() {
  val result= mapper.toModel(codigoEntity)
 assertTrue(result is LineaAlineacion,"debe ser un codigoAlineacionEntity")
 assertEquals(result,codigo, "deberian ser iguales")
 }
}