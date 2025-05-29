package org.example.newteamultimateedition.alineacion.validador

import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class AlineacionValidateTest{
  private val validator= AlineacionValidate()
  private val codigo= LineaAlineacion(
   idAlineacion = 1L,
   idPersona = 1L,
   posicion = 1
  )
 private val entrenador = Entrenador(
  id = 2,
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

 private fun createList(maxRows: Int): List<LineaAlineacion> {
  val list: MutableList<LineaAlineacion> = mutableListOf()
   for (i in 1..maxRows) {
    list.add(codigo)
   }
  return list.toList()
 }
@Nested
@DisplayName("casos correctos")
inner class CasosCorrectos {
  @Test
  @DisplayName("validate correcto")
  fun validar(){
  val alineacion=Alineacion(
   1L,
   personalList = createList(18),
   juegoDate = LocalDate.now(),
   updatedAt = LocalDateTime.now(),
   createdAt = LocalDateTime.now(),
   entrenador = entrenador

  )
   val result=validator.validator(alineacion)
   assertTrue(result.isOk,"deberia esta bien")
   assertEquals(result.value,alineacion,"deberian ser la misma")
  }
}
 @Nested
 @DisplayName("casos incorrectos")
 inner class CasosIncorrectos {

  @Test
  @DisplayName("validate incorrecto fecha")
  fun validarInvalidoFecha(){
   val alineacion=Alineacion(
    1L,
    personalList = createList(18),
    juegoDate = LocalDate.parse("2025-05-23"),
    updatedAt = LocalDateTime.now(),
    createdAt = LocalDateTime.now(),
    entrenador = entrenador
   )
   val result=validator.validator(alineacion)
   assertTrue(result.isErr,"deberia devolver un error")
   assertEquals(result.error.message,"Alineacion no válida: no puedes asignar una alineacion para una fecha anterior","deberian ser la misma")
 }
  @Test
  @DisplayName("validate incorrecto numero menor de codigos")
  fun validarInvalidoMenorCodigos(){
   val alineacion=Alineacion(
    1L,
    personalList = createList(17),
    juegoDate = LocalDate.now(),
    updatedAt = LocalDateTime.now(),
    createdAt = LocalDateTime.now(),
    entrenador = entrenador
   )
   val result=validator.validator(alineacion)
   assertTrue(result.isErr,"deberia devolver un error")
   assertEquals(result.error.message,"Alineacion no válida: no pueden ser menos o mas de 18 personas","deberian ser la misma")
  }
  @Test
  @DisplayName("validate incorrecto numero mayor de codigos")
  fun validarInvalidoMayorCodigos(){
   val alineacion=Alineacion(
    1L,
    personalList = createList(19),
    juegoDate = LocalDate.now(),
    updatedAt = LocalDateTime.now(),
    createdAt = LocalDateTime.now(),
    entrenador = entrenador
   )
   val result=validator.validator(alineacion)
   assertTrue(result.isErr,"deberia devolver un error")
   assertEquals(result.error.message,"Alineacion no válida: no pueden ser menos o mas de 18 personas","deberian ser la misma")
  }

 }
}