package org.example.newteamultimateedition.personal.mapper

import org.example.newteamultimateedition.personal.dao.PersonaEntity
import org.example.newteamultimateedition.personal.dto.IntegranteDTO
import org.example.newteamultimateedition.personal.dto.IntegranteXmlDTO
import org.example.newteamultimateedition.personal.extensions.copy
import org.example.newteamultimateedition.personal.models.*
import org.example.newteamultimateedition.personal.viewmodels.EquipoViewModel
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows


class PersonaMapperTest {

    class PersonaRemix(
        id: Long = 0L,
        nombre: String,
        apellidos: String,
        fechaNacimiento: LocalDate,
        fechaIncorporacion: LocalDate,
        salario: Double,
        pais: String,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
        imagen: String,
        ):Persona(id = id, nombre = nombre, apellidos = apellidos, fechaNacimiento = fechaNacimiento, fechaIncorporacion = fechaIncorporacion, salario = salario, pais = pais, createdAt = createdAt,updatedAt = updatedAt, imagen = imagen)
    private val personaRemix = PersonaRemix(
        id = 1,
        nombre = "Jugadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        imagen = "jaskjndkjnas"
    )
    private val jugador = Jugador(
        id = 1,
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
        createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
        updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
        partidosJugados = 10,
        minutosJugados = 100,
        imagen = "jaskjndkjnas",
        isDeleted = false
    )
    private val jugadorState = EquipoViewModel.PersonalState(
        id = 1L,
        nombre = "Jugadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        imagen = "jaskjndkjnas",
        especialidad ="",
        posicion = "DEFENSA",
        dorsal = 12,
        altura =100.0,
        peso = 100.0,
        goles = 10,
        partidosJugados = 10,
        minutosJugados = 100
    )

    private val entrenadorState = EquipoViewModel.PersonalState(
        id = 2,
        nombre = "Entrenadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        imagen = "oijsdoiasjd",
        especialidad = "ENTRENADOR_PRINCIPAL",
        posicion = "",
        dorsal = 0,
        altura = 0.0,
        peso = 0.0,
        goles = 0,
        partidosJugados = 0,
        minutosJugados = 0
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

    private val jugadorEntity = PersonaEntity(
        id = 1,
        nombre = "Jugadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        posicion = "DEFENSA",
        dorsal = 12,
        altura = 100.0,
        peso = 100.0,
        goles = 10,
        partidosJugados = 10,
        minutosJugados = 100,
        rol = "Jugador",
        createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
        updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
        imagen = "jaskjndkjnas",
        isDeleted = false
    )

    private val entrenadorEntity = PersonaEntity(
        id = 2,
        nombre = "Entrenadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        especialidad = "ENTRENADOR_PRINCIPAL",
        createdAt = LocalDateTime.of(2022, 5, 10, 14, 30),
        updatedAt = LocalDateTime.of(2022, 5, 10, 14, 30),
        rol = "Entrenador",
        imagen = "oijsdoiasjd",
        isDeleted = false
    )
    
    private val jugadorDTO = IntegranteDTO(
        id = 1,
        nombre = "Jugadora",
        apellidos = "hola",
        fechaNacimiento = "2020-01-01",
        fechaIncorporacion = "2020-01-02",
        salario = 3000.0,
        pais = "españa",
        rol = "Jugador",
        especialidad = null,
        posicion = "DEFENSA",
        dorsal = 12,
        altura = 100.0,
        peso = 100.0,
        goles = 10,
        partidosJugados = 10,
        minutosJugados = 100,
        imagen = "jaskjndkjnas"
    )
    private val any= Any()

    private val entrenadorDTO = IntegranteDTO(
        id = 2,
        nombre = "Entrenadora",
        apellidos = "hola",
        fechaNacimiento = "2020-01-01",
        fechaIncorporacion = "2020-01-02",
        salario = 3000.0,
        pais = "españa",
        rol = "Entrenador",
        especialidad = "ENTRENADOR_PRINCIPAL",
        posicion = null,
        dorsal = null,
        altura = null,
        peso = null,
        goles = null,
        partidosJugados = null,
        minutosJugados = null,
        imagen = "oijsdoiasjd"
    )

    private val jugadorXmlDTO = IntegranteXmlDTO(
        id = 1,
        nombre = "Jugadora",
        apellidos = "hola",
        fechaNacimiento = "2020-01-01",
        fechaIncorporacion = "2020-01-02",
        salario = 3000.0,
        pais = "españa",
        rol = "Jugador",
        especialidad = null,
        posicion = "DEFENSA",
        dorsal = "12",
        altura = "100.0",
        peso = "100.0",
        goles = "10",
        partidosJugados = "10",
        minutosJugados = "100",
        imagen = "jaskjndkjnas"
    )

    private val entrenadorXmlDTO = IntegranteXmlDTO(
        id = 2,
        nombre = "Entrenadora",
        apellidos = "hola",
        fechaNacimiento = "2020-01-01",
        fechaIncorporacion = "2020-01-02",
        salario = 3000.0,
        pais = "españa",
        rol = "Entrenador",
        especialidad = "ENTRENADOR_PRINCIPAL",
        posicion = null,
        dorsal = null,
        altura = null,
        peso = null,
        goles = null,
        partidosJugados = null,
        minutosJugados = null,
        imagen = "oijsdoiasjd"
    )

    /*
    val id: Long = 0L,
    val nombre: String = "",
    val apellidos: String = "",
    val fechaNacimiento: LocalDate = LocalDate.now(),
    val fechaIncorporacion: LocalDate = LocalDate.now(),
    val salario: Double = 0.0,
    val pais: String = "",
    val imagen: String = "resources/profile_picture.png",
    val especialidad: String = "",
    val posicion: String = "",
    val dorsal: Int = 0,
    val altura: Double = 0.0,
    val peso: Double = 0.0,
    val goles: Int = 0,
    val partidosJugados: Int = 0,
    val minutosJugados: Int = 0
     */

    @Nested
    @DisplayName("Tests correctos")
    inner class TestsCorrectos {
        @Test
        @DisplayName("Persona (jugador) de Modelo a Entidad")
        fun personaToEntity() {
            val expected = jugadorEntity

            val result = (jugador as Persona).toEntity()

            assertTrue(result is PersonaEntity)
            assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("Persona (entrenador) de Modelo a Entidad")
        fun personaToEntity2() {
            val expected = entrenadorEntity

            val result = (entrenador as Persona).toEntity()

            assertTrue(result is PersonaEntity)
            assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("probando con algo que no es una persona")
        fun personaToEntitySinPersona(){
            val result=assertThrows<IllegalArgumentException> { (personaRemix).toEntity() }
            assertTrue(result is Exception,"deberia ser un error")
            assertEquals(result.message,"No se trata de un jugador ni de un entrenador","deberian tener el mismo codigo de error")
        }

        @Test
        @DisplayName("Jugador de Modelo a Entidad")
        fun jugadorToEntity() {
            val expected = jugadorEntity

            val result = jugador.toEntity()

            assertTrue(result is PersonaEntity)
            assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("Entrenador de Modelo a Entidad")
        fun entrenadorToEntity() {
            val expected = entrenadorEntity

            val result = entrenador.toEntity()

            assertTrue(result is PersonaEntity)
            assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("Entrenador de Entidad a Modelo")
        fun entityToModelEntrenador() {
            val expected = entrenador

            val entity = entrenadorEntity

            val result = entity.toModel()

            assertTrue(result is Entrenador)
            assertEquals(
                expected.especialidad,
                (result as Entrenador).especialidad,
                "El resultado del mapeo debe coincidir con lo esperado."
            )
        }

        @Test
        @DisplayName("Jugador de Entidad a Modelo")
        fun entityToModelJugador() {
            val expected = jugador

            val entity = jugadorEntity

            val result = entity.toModel()

            assertTrue(result is Jugador)
            assertEquals(
                expected.posicion,
                (result as Jugador).posicion,
                "El resultado del mapeo debe coincidir con lo esperado."
            )
        }
        
        @Test
        @DisplayName("DTO a Modelo (jugador)")
        fun dtoToModelJugador() {
            val expected = jugador
            
            val result = jugadorDTO.toModel()

            assertTrue(result is Jugador)
            assertEquals(
                expected.posicion,
                (result as Jugador).posicion,
                "El resultado del mapeo debe coincidir con lo esperado."
            )
        }

        @Test
        @DisplayName("DTO a Modelo (entrenador)")
        fun dtoToModelEntrenador() {
            val expected = entrenador

            val result = entrenadorDTO.toModel()

            assertTrue(result is Entrenador)
            assertEquals(
                expected.especialidad,
                (result as Entrenador).especialidad,
                "El resultado del mapeo debe coincidir con lo esperado."
            )
        }

        @Test
        @DisplayName("XmlDTO a Modelo (jugador)")
        fun xmlDtoToModelJugador() {
            val expected = jugador

            val result = jugadorXmlDTO.toModel()

            assertTrue(result is Jugador)
            assertEquals(
                expected.posicion,
                (result as Jugador).posicion,
                "El resultado del mapeo debe coincidir con lo esperado."
            )
        }

        @Test
        @DisplayName("XmlDTO a Modelo (entrenador)")
        fun xmlDtoToModelEntrenador() {
            val expected = entrenador

            val result = entrenadorXmlDTO.toModel()

            assertTrue(result is Entrenador)
            assertEquals(
                expected.especialidad,
                (result as Entrenador).especialidad,
                "El resultado del mapeo debe coincidir con lo esperado."
            )
        }

        @Test
        @DisplayName("Entrenador de Modelo a XMLDTO")
        fun entrenadorToXmlDTO(){
            val expected = entrenadorXmlDTO

            val result = entrenador.toXmlDTO()

            assertTrue(result is IntegranteXmlDTO)
            assertEquals(expected.especialidad, result.especialidad, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("Jugador de Modelo a XMLDTO")
        fun jugadorToXmlDTO(){
            val expected = jugadorXmlDTO

            val result = jugador.toXmlDTO()

            assertTrue(result is IntegranteXmlDTO)
            assertEquals(expected.posicion, result.posicion, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("Entrenador de Modelo a DTO")
        fun entrenadorToDTO(){
            val expected = entrenadorDTO

            val result = entrenador.toDto()

            assertTrue(result is IntegranteDTO)
            assertEquals(expected.especialidad, result.especialidad, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("Jugador de Modelo a DTO")
        fun jugadorToDTO(){
            val expected = jugadorDTO

            val result = jugador.toDto()

            assertTrue(result is IntegranteDTO)
            assertEquals(expected.posicion, result.posicion, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("Persona State to Jugador")
        fun personaStateToJugador(){
            val result = jugadorState.toJugadorModel()
            assertAll(
                { assertTrue(result is Jugador) },
                { assertEquals(jugador.id, result.id) },
                { assertEquals(jugador.nombre, result.nombre) },
                { assertEquals(jugador.apellidos, result.apellidos) },
                { assertEquals(jugador.fechaNacimiento, result.fechaNacimiento) },
                { assertEquals(jugador.fechaIncorporacion, result.fechaIncorporacion) },
                { assertEquals(jugador.salario, result.salario) },
                { assertEquals(jugador.pais, result.pais) },
                { assertEquals(jugador.imagen, result.imagen) },
                { assertEquals(jugador.posicion, (result as Jugador).posicion) },
                { assertEquals(jugador.dorsal, (result as Jugador).dorsal) },
                { assertEquals(jugador.altura, (result as Jugador).altura) },
                { assertEquals(jugador.peso, (result as Jugador).peso) },
                { assertEquals(jugador.goles, (result as Jugador).goles) },
                { assertEquals(jugador.partidosJugados, (result as Jugador).partidosJugados) },
                { assertEquals(jugador.minutosJugados, (result as Jugador).minutosJugados) },
            )
        }
        @Test
        @DisplayName("Entrenador State to Entrenador")
        fun personaStateToEntrenador(){
            val result= entrenadorState.toEntrenadorModel()
            assertAll(
                { assertTrue(result is Entrenador) },
                { assertEquals(entrenador.id, result.id) },
                { assertEquals(entrenador.nombre, result.nombre) },
                { assertEquals(entrenador.apellidos, result.apellidos) },
                { assertEquals(entrenador.fechaNacimiento,result.fechaNacimiento) },
                { assertEquals(entrenador.fechaIncorporacion,result.fechaIncorporacion) },
                { assertEquals(entrenador.salario,result.salario) },
                { assertEquals(entrenador.pais,result.pais) },
                { assertEquals(entrenador.especialidad,(result as Entrenador).especialidad) },
            )
        }
    }
}