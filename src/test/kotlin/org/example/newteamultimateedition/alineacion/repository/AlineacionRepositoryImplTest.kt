package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.CodigoAlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.CodigoDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.CodigoAlineacion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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
  @DisplayName("Buscar por id")
  fun getByIdOk(){
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(codigoDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))).thenReturn(alineacionModel1)

   val expected = alineacionModel1
   val actual = repository.getById(alineacionEntity1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getById(alineacionModel1.id)
   verify(codigoDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(1)).toModel(codigoEntity1)
   verify(mapper, times(1)).toModel(codigoEntity2)
   verify(mapper, times(1)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))
  }

  @Test
  @DisplayName("Buscar por fecha")
  fun getByDateOk(){
   whenever(alineacionDao.getByFechaJuego(alineacionModel1.juegoDate)).thenReturn(alineacionEntity1)
   whenever(codigoDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))).thenReturn(alineacionModel1)

   val expected = alineacionModel1
   val actual = repository.getByDate(alineacionModel1.juegoDate)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getByFechaJuego(alineacionModel1.juegoDate)
   verify(codigoDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(1)).toModel(codigoEntity1)
   verify(mapper, times(1)).toModel(codigoEntity2)
   verify(mapper, times(1)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))
  }

  @Test
  @DisplayName("Borrar por id")
  fun deleteByIdOk(){
   //Funciones que lleva dentro getById()
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(codigoDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))).thenReturn(alineacionModel1)

   //Funciones propias de delete()
   whenever(alineacionDao.deleteById(alineacionModel1.id)).thenReturn(1)
   whenever(codigoDao.deleteByAlinecionId(alineacionModel1.id)).thenReturn(18)

   val expected = alineacionModel1
   val actual = repository.delete(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).deleteById(alineacionModel1.id)
   verify(codigoDao, times(1)).deleteByAlinecionId(alineacionModel1.id)
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

  @Test
  @DisplayName("Buscar por id (no existe)")
  fun getByIdNotExists(){
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(null)

   val expected = null
   val actual = repository.getById(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getById(alineacionModel1.id)
   verify(codigoDao, times(0)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))

  }

  @Test
  @DisplayName("Buscar por id (lista de códigos de alineacion vacía)")
  fun getByIdEmptyListOfCodes(){
   whenever(alineacionDao.getById(alineacionModelEmptyPersonalList.id)).thenReturn(alineacionEntityEmptyPersonalList)
   whenever(codigoDao.getByAlineacionId(alineacionEntityEmptyPersonalList.id)).thenReturn(emptyListCodigosAlineacionEntity)

   val expected = null
   val actual = repository.getById(alineacionModelEmptyPersonalList.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getById(alineacionModelEmptyPersonalList.id)
   verify(codigoDao, times(1)).getByAlineacionId(alineacionEntityEmptyPersonalList.id)
   verify(mapper, times(0)).toModel(alineacionEntityEmptyPersonalList, emptyListCodigosAlineacionModel)

  }

  @Test
  @DisplayName("Borrar por id (DAO devuelve 0 líneas borradas)")
  fun deleteByIdDAOFailure(){
   //Funciones que lleva dentro getById()
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(codigoDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))).thenReturn(alineacionModel1)

   //Funciones propias de delete()
   whenever(alineacionDao.deleteById(alineacionModel1.id)).thenReturn(0)

   val expected = null
   val actual = repository.delete(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).deleteById(alineacionModel1.id)
   verify(codigoDao, times(0)).deleteByAlinecionId(alineacionModel1.id)
  }

  @Test
  @DisplayName("Borrar por id (nº incorrecto de códigos de alineación)")
  fun deleteByIdNotEnoughCodigosAlineacion(){
   //Funciones que lleva dentro getById()
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(codigoDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))).thenReturn(alineacionModel1)

   //Funciones propias de delete()
   whenever(alineacionDao.deleteById(alineacionModel1.id)).thenReturn(1)
   whenever(codigoDao.deleteByAlinecionId(alineacionModel1.id)).thenReturn(9)

   val expected = null
   val actual = repository.delete(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).deleteById(alineacionModel1.id)
   verify(codigoDao, times(1)).deleteByAlinecionId(alineacionModel1.id)
  }

  @Test
  @DisplayName("Borrar por id (no se encuentra en el repositorio)")
  fun deleteByIdNotFound(){
   //Funciones que lleva dentro getById()
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(null)

   val expected = null
   val actual = repository.delete(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(0)).deleteById(alineacionModel1.id)
   verify(codigoDao, times(0)).deleteByAlinecionId(alineacionModel1.id)
  }

  @Test
  @DisplayName("Buscar por fecha (no existe)")
  fun getByDateNotExists(){
   whenever(alineacionDao.getByFechaJuego(alineacionModel1.juegoDate)).thenReturn(null)

   val expected = null
   val actual = repository.getByDate(alineacionModel1.juegoDate)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getByFechaJuego(alineacionModel1.juegoDate)
   verify(codigoDao, times(0)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2))
  }

  @Test
  @DisplayName("Buscar por fecha (lista de códigos de alineacion vacía)")
  fun getByDateEmptyListOfCodes(){
   whenever(alineacionDao.getByFechaJuego(alineacionModelEmptyPersonalList.juegoDate)).thenReturn(alineacionEntityEmptyPersonalList)
   whenever(codigoDao.getByAlineacionId(alineacionEntityEmptyPersonalList.id)).thenReturn(emptyListCodigosAlineacionEntity)

   val expected = null
   val actual = repository.getByDate(alineacionModelEmptyPersonalList.juegoDate)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getByFechaJuego(alineacionModelEmptyPersonalList.juegoDate)
   verify(codigoDao, times(1)).getByAlineacionId(alineacionEntityEmptyPersonalList.id)
   verify(mapper, times(0)).toModel(alineacionEntityEmptyPersonalList, emptyListCodigosAlineacionModel)

  }

 }

}