package org.example.newteamultimateedition.alineacion.mapper

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.CodigoAlineacionEntity
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.CodigoAlineacion
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class AlineacionMapperTest {
  private val mapper = AlineacionMapper()
  private val alineacionEntity= AlineacionEntity(
   id= 1L,
   createdAt = LocalDateTime.now(),
   updatedAt = LocalDateTime.now(),
   juegoDate = LocalDate.now(),
  )
 private val codigo= CodigoAlineacion(
  id= UUID.fromString("7fa10f96-a0fc-4ccd-8aa5-5238a2642488"),
  idAlineacion = 1L,
  idPersona = 1L,
  posicion = 1
 )
 private val alineacion= Alineacion(
  1L,
  personalList = listOf(),
  juegoDate =alineacionEntity.juegoDate,
  updatedAt = alineacionEntity.updatedAt,
  createdAt = alineacionEntity.createdAt
 )
 private val codigoEntity= CodigoAlineacionEntity(
  id= "7fa10f96-a0fc-4ccd-8aa5-5238a2642488",
  personalId = 1L,
  alineacionId = 1L,
  posicion = 1
 )
@Test
 fun toDatabaseModel() {
  val result= mapper.toDatabaseModel(alineacionEntity, listOf())
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
 assertTrue(result is CodigoAlineacionEntity,"debe ser un codigoAlineacionEntity")
 assertEquals(result,codigoEntity, "deberian ser iguales")
 }

@Test
 fun toModel() {
  val result= mapper.toModel(codigoEntity)
 assertTrue(result is CodigoAlineacion,"debe ser un codigoAlineacionEntity")
 assertEquals(result,codigo, "deberian ser iguales")
 }
}