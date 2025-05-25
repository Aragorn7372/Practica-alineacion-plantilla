package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.CodigoAlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.CodigoDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.CodigoAlineacion
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class AlineacionRepositoryImplTest {

  private lateinit var mapper: AlineacionMapper
  private lateinit var alineacionDao: AlineacionDao
  private lateinit var codigoDao: CodigoDao
  private lateinit var repository: AlineacionRepositoryImpl

 private val codigoEntity1 = CodigoAlineacionEntity(
  id = "fccfcf50-e184-4aaa-acde-e7388fe623cf",
  personalId = 101L,
  alineacionId = 1L,
  posicion = 1
 )
 
 private val codigoModel1 = CodigoAlineacion(
     id = UUID.fromString("fccfcf50-e184-4aaa-acde-e7388fe623cf"),
     idAlineacion = 1L,
     idPersona = 101L,
     posicion = 1
 )
 
 private val codigoEntity2 = CodigoAlineacionEntity(
     id = "2c2ac9d7-1bd0-4ebf-8b61-cf1e019272e0",
     personalId = 102L,
     alineacionId = 1L,
     posicion = 2
 )

 private val codigoModel2 = CodigoAlineacion(
  id = UUID.fromString("2c2ac9d7-1bd0-4ebf-8b61-cf1e019272e0"),
  idAlineacion = 1L,
  idPersona = 102L,
  posicion = 2
 )

 private val codigoEntity3 = CodigoAlineacionEntity(
  id = "bcfd589a-429e-42f2-a543-2b0fa4fe8477",
  personalId = 201L,
  alineacionId = 2L,
  posicion = 1
 )

 private val codigoModel3 = CodigoAlineacion(
  id = UUID.fromString("bcfd589a-429e-42f2-a543-2b0fa4fe8477"),
  idAlineacion = 2L,
  idPersona = 201L,
  posicion = 1
 )
  
 private val alineacionEntity1 = AlineacionEntity(
  id = 1L,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2003,8,20),
 )

 private val alineacionModel1 = Alineacion(
     id = 1L,
     personalList = listOf(codigoModel1, codigoModel2),
     createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
     updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
     juegoDate = LocalDate.of(2003,8,20)
 )

 private val alineacionEntity2 = AlineacionEntity(
  id = 2L,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20)
 )

 private val alineacionModel2 = Alineacion(
  id = 2L,
  personalList = listOf(codigoModel3),
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20)
 )

 private val alineacionEntityEmptyPersonalList = AlineacionEntity(
  id = 3L,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20)
 )

 private val emptyListCodigosAlineacionEntity: List<CodigoAlineacionEntity> = listOf()

 private val emptyListCodigosAlineacionModel: List<CodigoAlineacion> = listOf()

 private val alineacionModelEmptyPersonalList = Alineacion(
  id = 3L,
  personalList = emptyListCodigosAlineacionModel,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20)
 )

  @BeforeEach
  fun setUp(){
   mapper = mock()
   alineacionDao = mock()
   codigoDao = mock()
   repository = AlineacionRepositoryImpl(alineacionDao, codigoDao, mapper)
  }

 @Nested
 @DisplayName("Tests correctos")
 inner class TestsCorrectos {

  @Test
  @DisplayName("Obtener todas")
  fun getAllOk(){
   whenever(alineacionDao.getAll()).thenReturn(listOf(alineacionEntity1, alineacionEntity2))

   whenever(codigoDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(codigoDao.getByAlineacionId(alineacionEntity2.id)).thenReturn(listOf(codigoEntity3))

   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(codigoEntity3)).thenReturn(codigoModel3)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))).thenReturn(alineacionModel1)
   whenever(mapper.toModel(alineacionEntity2, listOf(codigoModel3))).thenReturn(alineacionModel2)

   val expected = listOf(alineacionModel1, alineacionModel2)
   val actual = repository.getAll()

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getAll()

   verify(codigoDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(codigoDao, times(1)).getByAlineacionId(alineacionEntity2.id)

   verify(mapper, times(1)).toModel(codigoEntity1)
   verify(mapper, times(1)).toModel(codigoEntity2)
   verify(mapper, times(1)).toModel(codigoEntity3)
   verify(mapper, times(1)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))
   verify(mapper, times(1)).toModel(alineacionEntity2, listOf(codigoModel3))
  }
  @Test
  @DisplayName("Save")
  fun saveTest(){
   whenever(alineacionDao.save(alineacionEntity1)).thenReturn(1)
   whenever(mapper.toEntity(alineacionModel1)).thenReturn(alineacionEntity1)
   whenever(mapper.toEntity(codigoModel1)).thenReturn(codigoEntity1)
   whenever(mapper.toEntity(codigoModel2)).thenReturn(codigoEntity2)

   val result = repository.save(alineacionModel1)

   assertAll(
    { assertEquals(result, alineacionModel1) },
    { assertEquals(result.id, 1L) }
   )
   verify(alineacionDao, times(1)).save(alineacionEntity1)
   verify(mapper, times(1)).toEntity(codigoModel1)
   verify(mapper, times(1)).toEntity(codigoModel2)
   verify(codigoDao, times(1)).save(codigoEntity1)
   verify(codigoDao, times(1)).save(codigoEntity2)
  }

 }
 @Nested
 @DisplayName("Tests incorrectos")
 inner class TestsIncorrectos {

  @Test
  @DisplayName("getAll() devuelve lista de alineaciones vacía")
  fun daoReturnsEmptyList(){
   whenever(alineacionDao.getAll()).thenReturn(listOf())

   val expected: List<AlineacionEntity> = listOf()

   val actual = repository.getAll()

   assertEquals(expected, actual, "Debe devolver una lista vacía")

   verify(alineacionDao, times(1)).getAll()

   verify(codigoDao, times(0)).getByAlineacionId(alineacionEntity1.id)
   verify(codigoDao, times(0)).getByAlineacionId(alineacionEntity2.id)

   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(codigoEntity3)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))
   verify(mapper, times(0)).toModel(alineacionEntity2, listOf(codigoModel3))

  }

 }

}