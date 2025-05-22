package org.example.newteamultimateedition.personal.mapper

import org.example.newteamultimateedition.personal.dao.PersonaEntity
import org.example.newteamultimateedition.personal.models.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.*


class PersonaMapperTest {

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
        imagen = "jaskjndkjnas"
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
        imagen = "oijsdoiasjd"
    )

    @Test
    @DisplayName("Persona (jugador) de Modelo a Entidad")
    fun personaToEntity(){
        val expected = PersonaEntity(
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
            imagen = "jaskjndkjnas"
        )

        val result = (jugador as Persona).toEntity()

        assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
    }

    @Test
    @DisplayName("Persona (entrenador) de Modelo a Entidad")
    fun personaToEntity2(){
        val expected = PersonaEntity(
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
            imagen = "oijsdoiasjd"
        )

        val result = (entrenador as Persona).toEntity()

        assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
    }

    @Test
    @DisplayName("Jugador de Modelo a Entidad")
    fun jugadorToEntity(){
        val expected = PersonaEntity(
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
            imagen = "jaskjndkjnas"
        )

        val result = jugador.toEntity()

        assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
    }

    @Test
    @DisplayName("Entrenador de Modelo a Entidad")
    fun entrenadorToEntity(){
        val expected = PersonaEntity(
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
            imagen = "oijsdoiasjd"
        )

        val result = entrenador.toEntity()

        assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
    }

    @Test
    @DisplayName("Entrenador de Entidad a Modelo")
    fun entityToModelEntrenador(){
        val expected = entrenador

        val entity = PersonaEntity(
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
            imagen = "oijsdoiasjd"
        )

        val result = entity.toModel()

        assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
    }


}