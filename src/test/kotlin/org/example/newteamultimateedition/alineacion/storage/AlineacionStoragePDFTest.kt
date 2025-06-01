package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
 class AlineacionStoragePDFTest {
  @Mock
  lateinit var html:AlineacionStorageHTML
  @InjectMocks
  lateinit var storagePDF: AlineacionStoragePDF


 private val jugador1 = Jugador(
  id = 101L,
  nombre = "Oliver",
  apellidos = "Atom",
  fechaNacimiento = LocalDate.of(1983, 4, 10),
  fechaIncorporacion = LocalDate.of(2001, 5, 15) ,
  salario = 35000.0,
  pais = "España",
  imagen = "media/jugador1.png",
  posicion = Posicion.DELANTERO,
  dorsal = 10,
  altura = 1.75,
  peso = 65.0,
  goles = 70,
  partidosJugados = 150,
  minutosJugados = 13245,
  isDeleted = false
 )

 private val jugador2 = Jugador(
  id = 102L,
  nombre = "Jugadora",
  apellidos = "hola",
  fechaNacimiento = LocalDate.parse("2020-01-01"),
  fechaIncorporacion = LocalDate.parse("2020-01-02"),
  salario = 3000.0,
  pais = "españa",
  posicion = Posicion.DEFENSA,
  dorsal = 12,
  altura = 100.0,
  peso = 100.0,
  goles = 10,
  partidosJugados = 10,
  minutosJugados = 100,
  imagen = "jaskjndkjnas",
  isDeleted = false
 )

 private val entrenador = Entrenador(
  id = 103L,
  nombre = "Pepito",
  apellidos = "Grillo",
  fechaNacimiento = LocalDate.of(1942, 1, 1),
  fechaIncorporacion = LocalDate.of(2025, 2, 2),
  salario = 1234.0,
  pais = "Italia",
  createdAt = LocalDateTime.of(2025, 5, 31, 14, 30),
  updatedAt = LocalDateTime.of(2025, 5, 31, 14, 30),
  imagen = "pepito-grillo.png",
  especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
  isDeleted = false
 )

 private val lineaAlineacion1 = LineaAlineacion(
  id = UUID.randomUUID(),
  idAlineacion = 1L,
  idPersona = 101L,
  posicion = 1
 )

 private val lineaAlineacion2 = LineaAlineacion(
  id = UUID.randomUUID(),
  idAlineacion = 1L,
  idPersona = 102L,
  posicion = 2
 )

 private val alineacion = Alineacion(
  id = 1L,
  personalList = listOf(lineaAlineacion1, lineaAlineacion2),
  createdAt = LocalDateTime.of(2025, 5, 31, 14, 30),
  updatedAt = LocalDateTime.of(2025, 5, 31, 14, 30),
  juegoDate = LocalDate.of(2025, 5, 31),
  entrenador = entrenador,
  descripcion = "El dream team"
 )

 @Test
 fun fileWrite() {
  val tempFile = File.createTempFile("test", ".pdf").apply {
   deleteOnExit()
  }
  val htmlContent = "<html><body><p>Test</p></body></html>"
  whenever(html.createHtml(alineacion, listOf(jugador1))).thenReturn(htmlContent)
  val result = storagePDF.fileWrite(alineacion, listOf(jugador1), tempFile)
  assertTrue(result.isOk, "no deberia ser un error")
  assertEquals(Unit, result.value,"deberian ser iguales")
  verify(html, times(1)).createHtml(alineacion, listOf(jugador1))
 }
 @Test
 fun fileWriteNotOk() {
  val file = File( "media/")
  val result=storagePDF.fileWrite(alineacion, listOf(jugador1, jugador2), file)

  assertTrue(result.isErr, "deberia ser un error")
  assertEquals(AlineacionError.AlineacionStorageError("El directorio padre del fichero no existe").message, result.error.message,"deberian ser iguales")
 }

}