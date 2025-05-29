package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.LineaAlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.LineaAlineacionDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
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
  private lateinit var lineaAlineacionDao: LineaAlineacionDao
  private lateinit var personasRepository: PersonasRepositoryImplementation
  private lateinit var repository: AlineacionRepositoryImpl
 private val entrenador = Entrenador(
  id = 1L,
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
 private val codigoEntity1 = LineaAlineacionEntity(
  id = "fccfcf50-e184-4aaa-acde-e7388fe623cf",
  personalId = 101L,
  alineacionId = 1L,
  posicion = 1
 )
 
 private val codigoModel1 = LineaAlineacion(
     id = UUID.fromString("fccfcf50-e184-4aaa-acde-e7388fe623cf"),
     idAlineacion = 1L,
     idPersona = 101L,
     posicion = 1
 )
 
 private val codigoEntity2 = LineaAlineacionEntity(
     id = "2c2ac9d7-1bd0-4ebf-8b61-cf1e019272e0",
     personalId = 102L,
     alineacionId = 1L,
     posicion = 2
 )

 private val codigoModel2 = LineaAlineacion(
  id = UUID.fromString("2c2ac9d7-1bd0-4ebf-8b61-cf1e019272e0"),
  idAlineacion = 1L,
  idPersona = 102L,
  posicion = 2
 )

 private val codigoEntity3 = LineaAlineacionEntity(
  id = "bcfd589a-429e-42f2-a543-2b0fa4fe8477",
  personalId = 201L,
  alineacionId = 2L,
  posicion = 1
 )

 private val codigoModel3 = LineaAlineacion(
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
  idEntrenador = 1L
 )

 private val alineacionModel1 = Alineacion(
     id = 1L,
     personalList = listOf(codigoModel1, codigoModel2),
     createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
     updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
     juegoDate = LocalDate.of(2003,8,20),
     entrenador = entrenador
 )

 private val alineacionEntity2 = AlineacionEntity(
  id = 2L,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20),
  idEntrenador = 1L
 )

 private val alineacionModel2 = Alineacion(
  id = 2L,
  personalList = listOf(codigoModel3),
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20),
  entrenador = entrenador
 )

 private val alineacionEntityEmptyPersonalList = AlineacionEntity(
  id = 3L,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20),
  idEntrenador = 1L
 )

 private val emptyListCodigosAlineacionEntity: List<LineaAlineacionEntity> = listOf()

 private val emptyListCodigosAlineacionModel: List<LineaAlineacion> = listOf()

 private val alineacionModelEmptyPersonalList = Alineacion(
  id = 3L,
  personalList = emptyListCodigosAlineacionModel,
  createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
  juegoDate = LocalDate.of(2000,8,20),
  entrenador = entrenador
 )

  @BeforeEach
  fun setUp(){
   mapper = mock()
   alineacionDao = mock()
   lineaAlineacionDao = mock()
   personasRepository = mock()
   repository = AlineacionRepositoryImpl(alineacionDao, lineaAlineacionDao, mapper, personasRepository)
  }

 @Nested
 @DisplayName("Tests correctos")
 inner class TestsCorrectos {

  @Test
  @DisplayName("Obtener todas")
  fun getAllOk(){
   whenever(alineacionDao.getAll()).thenReturn(listOf(alineacionEntity1, alineacionEntity2))

   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity2.id)).thenReturn(listOf(codigoEntity3))
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(codigoEntity3)).thenReturn(codigoModel3)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)).thenReturn(alineacionModel1)
   whenever(mapper.toModel(alineacionEntity2, listOf(codigoModel3), entrenador)).thenReturn(alineacionModel2)


   val expected = listOf(alineacionModel1, alineacionModel2)
   val actual = repository.getAll()

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getAll()

   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity2.id)

   verify(mapper, times(1)).toModel(codigoEntity1)
   verify(mapper, times(1)).toModel(codigoEntity2)
   verify(mapper, times(1)).toModel(codigoEntity3)
   verify(mapper, times(1)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
   verify(mapper, times(1)).toModel(alineacionEntity2, listOf(codigoModel3), entrenador)
   verify(personasRepository, times(2)).getById(entrenador.id)
  }

  @Test
  @DisplayName("Buscar por id")
  fun getByIdOk(){
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)).thenReturn(alineacionModel1)
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)

   val expected = alineacionModel1
   val actual = repository.getById(alineacionEntity1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getById(alineacionModel1.id)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(1)).toModel(codigoEntity1)
   verify(mapper, times(1)).toModel(codigoEntity2)
   verify(mapper, times(1)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
  }

  @Test
  @DisplayName("Buscar por fecha")
  fun getByDateOk(){
   whenever(alineacionDao.getByFechaJuego(alineacionModel1.juegoDate)).thenReturn(alineacionEntity1)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)).thenReturn(alineacionModel1)
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)

   val expected = alineacionModel1
   val actual = repository.getByDate(alineacionModel1.juegoDate)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getByFechaJuego(alineacionModel1.juegoDate)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(personasRepository, times(1)).getById(entrenador.id)
   verify(mapper, times(1)).toModel(codigoEntity1)
   verify(mapper, times(1)).toModel(codigoEntity2)
   verify(mapper, times(1)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
  }

  @Test
  @DisplayName("Borrar por id")
  fun deleteByIdOk(){
   //Funciones que lleva dentro getById()
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)).thenReturn(alineacionModel1)
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)

   //Funciones propias de delete()
   whenever(alineacionDao.deleteById(alineacionModel1.id)).thenReturn(1)
   whenever(lineaAlineacionDao.deleteByAlinecionId(alineacionModel1.id)).thenReturn(18)

   val expected = alineacionModel1
   val actual = repository.delete(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).deleteById(alineacionModel1.id)
   verify(lineaAlineacionDao, times(1)).deleteByAlinecionId(alineacionModel1.id)
  }

  @Test
  @DisplayName("update good alineacion")
  fun updateGoodAlineacion() {
   whenever(alineacionDao.getById(alineacionEntity1.id)).thenReturn(alineacionEntity1)
   whenever(alineacionDao.updateById(alineacionEntity1, alineacionEntity1.id)).thenReturn(1)
   whenever(lineaAlineacionDao.deleteByAlinecionId(alineacionEntity1.id)).thenReturn(1)
   whenever(lineaAlineacionDao.save(codigoEntity2)).thenReturn(1)
   whenever(lineaAlineacionDao.save(codigoEntity1)).thenReturn(1)

   // Mapear correctamente modelos a entidades
   whenever(mapper.toEntity(codigoModel1)).thenReturn(codigoEntity1)
   whenever(mapper.toEntity(codigoModel2)).thenReturn(codigoEntity2)

   // Mapear alineación
   whenever(mapper.toEntity(alineacionModel1)).thenReturn(alineacionEntity1)
   whenever(mapper.toModel(alineacionEntity1, alineacionModel1.personalList, entrenador)).thenReturn(alineacionModel1)
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)

   // Ejecutar
   val result = repository.update(alineacionModel1, alineacionEntity1.id)

   // Afirmación
   assertEquals(alineacionModel1, result, "deberían ser iguales")

   // Verificaciones
   verify(alineacionDao, times(1)).getById(alineacionEntity1.id)
   verify(alineacionDao, times(1)).updateById(alineacionEntity1, alineacionEntity1.id)
   verify(lineaAlineacionDao, times(1)).deleteByAlinecionId(alineacionEntity1.id)
   verify(lineaAlineacionDao, times(1)).save(codigoEntity2)
   verify(lineaAlineacionDao, times(1)).save(codigoEntity1)
   verify(mapper, times(1)).toEntity(codigoModel2)
   verify(mapper, times(1)).toEntity(codigoModel1)
   verify(mapper, times(1)).toEntity(alineacionModel1)
   verify(mapper, times(1)).toModel(alineacionEntity1, alineacionModel1.personalList, entrenador)

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
   verify(lineaAlineacionDao, times(1)).save(codigoEntity1)
   verify(lineaAlineacionDao, times(1)).save(codigoEntity2)
  }

 }
 @Nested
 @DisplayName("Tests incorrectos")
 inner class TestsIncorrectos {

  @Test
  @DisplayName("Buscar por id (Repositorio no encuentra entrenador)")
  fun getByIdEntrenadorNotFound(){
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(personasRepository.getById(1L)).thenReturn(null)

   val expected = null
   val actual = repository.getById(alineacionEntity1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getById(alineacionModel1.id)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
  }

  @Test
  @DisplayName("getAll() devuelve lista de alineaciones vacía")
  fun daoReturnsEmptyList(){
   whenever(alineacionDao.getAll()).thenReturn(listOf())

   val expected: List<AlineacionEntity> = listOf()

   val actual = repository.getAll()

   assertEquals(expected, actual, "Debe devolver una lista vacía")

   verify(alineacionDao, times(1)).getAll()

   verify(lineaAlineacionDao, times(0)).getByAlineacionId(alineacionEntity1.id)
   verify(lineaAlineacionDao, times(0)).getByAlineacionId(alineacionEntity2.id)

   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(codigoEntity3)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2),entrenador)
   verify(mapper, times(0)).toModel(alineacionEntity2, listOf(codigoModel3),entrenador)
   verify(personasRepository, times(0)).getById(1L)

  }

  @Test
  @DisplayName("Buscar por id (no existe)")
  fun getByIdNotExists(){
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(null)

   val expected = null
   val actual = repository.getById(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getById(alineacionModel1.id)
   verify(lineaAlineacionDao, times(0)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2),entrenador)
   verify(personasRepository, times(0)).getById(1L)

  }

  @Test
  @DisplayName("Buscar por id (lista de códigos de alineacion vacía)")
  fun getByIdEmptyListOfCodes(){
   whenever(alineacionDao.getById(alineacionModelEmptyPersonalList.id)).thenReturn(alineacionEntityEmptyPersonalList)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntityEmptyPersonalList.id)).thenReturn(emptyListCodigosAlineacionEntity)
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)

   val expected = null
   val actual = repository.getById(alineacionModelEmptyPersonalList.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getById(alineacionModelEmptyPersonalList.id)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntityEmptyPersonalList.id)
   verify(mapper, times(0)).toModel(alineacionEntityEmptyPersonalList, emptyListCodigosAlineacionModel, entrenador)

  }

  @Test
  @DisplayName("Borrar por id (DAO devuelve 0 líneas borradas)")
  fun deleteByIdDAOFailure(){
   //Funciones que lleva dentro getById()
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)).thenReturn(alineacionModel1)

   //Funciones propias de delete()
   whenever(alineacionDao.deleteById(alineacionModel1.id)).thenReturn(0)

   val expected = null
   val actual = repository.delete(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).deleteById(alineacionModel1.id)
   verify(lineaAlineacionDao, times(0)).deleteByAlinecionId(alineacionModel1.id)
  }

  @Test
  @DisplayName("Borrar por id (nº incorrecto de códigos de alineación)")
  fun deleteByIdNotEnoughCodigosAlineacion(){
   //Funciones que lleva dentro getById()
   whenever(personasRepository.getById(alineacionModel1.id)).thenReturn(entrenador)
   whenever(alineacionDao.getById(alineacionModel1.id)).thenReturn(alineacionEntity1)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)).thenReturn(alineacionModel1)

   //Funciones propias de delete()
   whenever(alineacionDao.deleteById(alineacionModel1.id)).thenReturn(1)
   whenever(lineaAlineacionDao.deleteByAlinecionId(alineacionModel1.id)).thenReturn(9)

   val expected = null
   val actual = repository.delete(alineacionModel1.id)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).deleteById(alineacionModel1.id)
   verify(lineaAlineacionDao, times(1)).deleteByAlinecionId(alineacionModel1.id)
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
   verify(lineaAlineacionDao, times(0)).deleteByAlinecionId(alineacionModel1.id)
  }

  @Test
  @DisplayName("Buscar por fecha (no existe)")
  fun getByDateNotExists(){
   whenever(alineacionDao.getByFechaJuego(alineacionModel1.juegoDate)).thenReturn(null)

   val expected = null
   val actual = repository.getByDate(alineacionModel1.juegoDate)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getByFechaJuego(alineacionModel1.juegoDate)
   verify(lineaAlineacionDao, times(0)).getByAlineacionId(alineacionEntity1.id)
   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
  }

  @Test
  @DisplayName("Buscar por fecha (Repositorio no encuentra entrenador)")
  fun getByDateEntrenadorNotFound(){
   whenever(alineacionDao.getByFechaJuego(alineacionModel1.juegoDate)).thenReturn(alineacionEntity1)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   whenever(personasRepository.getById(entrenador.id)).thenReturn(null)

   val expected = null
   val actual = repository.getByDate(alineacionModel1.juegoDate)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getByFechaJuego(alineacionModel1.juegoDate)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(personasRepository, times(1)).getById(entrenador.id)
   verify(mapper, times(0)).toModel(codigoEntity1)
   verify(mapper, times(0)).toModel(codigoEntity2)
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
  }

  @Test
  @DisplayName("Buscar por fecha (lista de códigos de alineacion vacía)")
  fun getByDateEmptyListOfCodes(){
   whenever(alineacionDao.getByFechaJuego(alineacionModelEmptyPersonalList.juegoDate)).thenReturn(alineacionEntityEmptyPersonalList)
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntityEmptyPersonalList.id)).thenReturn(emptyListCodigosAlineacionEntity)
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)
   val expected = null
   val actual = repository.getByDate(alineacionModelEmptyPersonalList.juegoDate)

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getByFechaJuego(alineacionModelEmptyPersonalList.juegoDate)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntityEmptyPersonalList.id)
   verify(mapper, times(0)).toModel(alineacionEntityEmptyPersonalList, emptyListCodigosAlineacionModel, entrenador)

  }

  @Test
  @DisplayName("update bad NotFound")
  fun updateNodFoundAlineacion() {
   whenever(alineacionDao.getById(alineacionEntity1.id)).thenReturn(null)





   // Ejecutar
   val result = repository.update(alineacionModel1, alineacionEntity1.id)

   // Afirmación
   assertNull(result,"deberia ser nulo")

   // Verificaciones
   verify(alineacionDao, times(1)).getById(alineacionEntity1.id)
   verify(alineacionDao, times(0)).updateById(alineacionEntity1, alineacionEntity1.id)
   verify(lineaAlineacionDao, times(0)).deleteByAlinecionId(alineacionEntity1.id)
   verify(lineaAlineacionDao, times(0)).save(codigoEntity2)
   verify(lineaAlineacionDao, times(0)).save(codigoEntity1)
   verify(mapper, times(0)).toEntity(codigoModel2)
   verify(mapper, times(0)).toEntity(codigoModel1)
   verify(mapper, times(0)).toEntity(alineacionModel1)
   verify(mapper, times(0)).toModel(alineacionEntity1, alineacionModel1.personalList, entrenador)
  }

  @Test
  @DisplayName("getAll() el repositorio no encuentra el entrenador")
  fun getAllEntrenadorNotFound() {
   // Solo una alineación, cuyo entrenador no existe
   whenever(alineacionDao.getAll()).thenReturn(listOf(alineacionEntity1))

   // La alineación tiene líneas asociadas
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id))
    .thenReturn(listOf(codigoEntity1, codigoEntity2))

   // Pero no se encuentra el entrenador
   whenever(personasRepository.getById(1L)).thenReturn(null)

   val expected = emptyList<Alineacion>()
   val actual = repository.getAll()

   assertEquals(expected, actual)

   verify(alineacionDao, times(1)).getAll()
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(personasRepository, times(1)).getById(1L)

   // No se deben llamar los mappers, ya que no hay entrenador
   verify(mapper, times(0)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
  }

  @Test
  @DisplayName("update bad update failed")
  fun updateBadUpdateAlineacion() {
   whenever(alineacionDao.getById(alineacionEntity1.id)).thenReturn(alineacionEntity1)
   whenever(alineacionDao.updateById(alineacionEntity1, alineacionEntity1.id)).thenReturn(0)
   whenever(mapper.toEntity(alineacionModel1)).thenReturn(alineacionEntity1)
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)
   // Ejecutar
   val result = repository.update(alineacionModel1, alineacionEntity1.id)

   // Afirmación
   assertNull(result,"deberia ser nulo")

   // Verificaciones
   verify(alineacionDao, times(1)).getById(alineacionEntity1.id)
   verify(alineacionDao, times(1)).updateById(alineacionEntity1, alineacionEntity1.id)
   verify(lineaAlineacionDao, times(0)).deleteByAlinecionId(alineacionEntity1.id)
   verify(lineaAlineacionDao, times(0)).save(codigoEntity2)
   verify(lineaAlineacionDao, times(0)).save(codigoEntity1)
   verify(mapper, times(0)).toEntity(codigoModel2)
   verify(mapper, times(0)).toEntity(codigoModel1)
   verify(mapper, times(1)).toEntity(alineacionModel1)
   verify(mapper, times(0)).toModel(alineacionEntity1, alineacionModel1.personalList, entrenador)
  }


  @Test
  @DisplayName("getAll filtra alineaciones con lista de códigos vacía")
  fun getAllEmptyCodes() {
   // Simulamos que hay dos alineaciones en la base de datos
   whenever(alineacionDao.getAll()).thenReturn(listOf(alineacionEntity1, alineacionEntityEmptyPersonalList))
   whenever(personasRepository.getById(1L)).thenReturn(entrenador)
   // La primera tiene 2 códigos asociados
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntity1.id)).thenReturn(listOf(codigoEntity1, codigoEntity2))
   // La segunda tiene 0 códigos → será filtrada
   whenever(lineaAlineacionDao.getByAlineacionId(alineacionEntityEmptyPersonalList.id)).thenReturn(emptyListCodigosAlineacionEntity)

   // También simulamos que el mapper transforma correctamente los códigos
   whenever(mapper.toModel(codigoEntity1)).thenReturn(codigoModel1)
   whenever(mapper.toModel(codigoEntity2)).thenReturn(codigoModel2)

   // Y la transformación final a modelo de alineación
   whenever(mapper.toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)).thenReturn(alineacionModel1)
   // se llama a al mapper aunque este vacia debido que se filtra mas adelante
   whenever(mapper.toModel(alineacionEntityEmptyPersonalList, emptyListCodigosAlineacionModel, entrenador)).thenReturn(alineacionModelEmptyPersonalList)

   val expected = listOf(alineacionModel1)
   val actual = repository.getAll()

   assertEquals(expected, actual)

   // Verificaciones
   verify(alineacionDao, times(1)).getAll()
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntity1.id)
   verify(lineaAlineacionDao, times(1)).getByAlineacionId(alineacionEntityEmptyPersonalList.id)
   verify(mapper, times(1)).toModel(alineacionEntity1, listOf(codigoModel1, codigoModel2), entrenador)
   verify(mapper, times(1)).toModel(alineacionEntityEmptyPersonalList, emptyList(), entrenador)
  }
 }

}