package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
 class AlineacionStorageImplTest {
  @Mock
  private lateinit var html:AlineacionStorageHTML
 @Mock
 private lateinit var storagePDF:AlineacionStoragePDF
 @InjectMocks
 private lateinit var storageImpl: AlineacionStorageImpl

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
 private val listaPersonas= listOf(jugador1)
 @Test
 @DisplayName("Escribir en HTML")
 fun fileWriteHTML() {
  val htmlFile = File("resources", "test.html")
  whenever(html.fileWrite(alineacion, listaPersonas, htmlFile)).thenReturn(Ok(Unit))

  val result = storageImpl.fileWrite(alineacion, listaPersonas, htmlFile)

  assertAll(
   { assertTrue(result.isOk, "deberian ser true") },
   { assertEquals(result.value, Unit, "deberian ser iguales") },
  )

  verify(html, times(1)).fileWrite(alineacion, listaPersonas, htmlFile)
  verify(storagePDF, times(0)).fileWrite(alineacion, listaPersonas, htmlFile)
 }
 @Test
 @DisplayName("Escribir en HTML bad")
 fun fileWriteHTMLBad() {
  val htmlFile= File("resources", "test.html")
  whenever(html.fileWrite(alineacion, listaPersonas,htmlFile)) doReturn Err(AlineacionError.AlineacionStorageError("un error muy bonico"))
  val result= storageImpl.fileWrite(alineacion, listaPersonas,htmlFile)
  assertTrue(result.isErr,"deberia ser true")
  assertEquals(result.error.message,"un error muy bonico","deberian ser iguales")
  verify(storagePDF, times(0)).fileWrite(alineacion, listaPersonas,htmlFile)
  verify(html, times(1)).fileWrite(alineacion, listaPersonas,htmlFile)
 }

 @Test
 @DisplayName("Escribir en PDF")
 fun fileWritePDF() {
  val pdfFile= File("resources", "test.pdf")
  whenever(storagePDF.fileWrite(alineacion, listaPersonas,pdfFile)) doReturn Ok(Unit)
  val result= storageImpl.fileWrite(alineacion, listaPersonas,pdfFile)
  assertTrue(result.isOk,"deberia estar good")
  assertEquals(result.value,Unit,"deberian ser iguales")
  verify(html, times(0)).fileWrite(alineacion, listaPersonas, pdfFile)
  verify(storagePDF, times(1)).fileWrite(alineacion, listaPersonas, pdfFile)
 }
 @Test
 @DisplayName("Escribir en PDF bad")
 fun fileWritePDFBad() {
  val pdfFile= File("resources", "test.pdf")
  whenever(storagePDF.fileWrite(alineacion, listaPersonas,pdfFile)) doReturn Err(AlineacionError.AlineacionStorageError("un error muy bonico"))
  val result= storageImpl.fileWrite(alineacion, listaPersonas,pdfFile)
  assertTrue(result.isErr,"deberia estar good")
  assertEquals(result.error.message,"un error muy bonico","deberian ser iguales")
  verify(storagePDF, times(1)).fileWrite(alineacion, listaPersonas,pdfFile)
  verify(html, times(0)).fileWrite(alineacion, listaPersonas,pdfFile)
 }
 @Test
 @DisplayName("Extension equivocada")
 fun fileWriteBad(){
  val pdfFile= File("resources", "test.csv")
  val result= storageImpl.fileWrite(alineacion, listaPersonas,pdfFile)
  assertTrue(result.isErr,"deberia estar good")
  assertEquals(result.error.message,"Extensión inválida.","deberian ser iguales")
  verify(storagePDF, times(0)).fileWrite(alineacion, listaPersonas,pdfFile)
  verify(html, times(0)).fileWrite(alineacion, listaPersonas,pdfFile)

 }
}