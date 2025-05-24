package org.example.newteamultimateedition.personal.validator

import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime


class PersonaValidationTest {
    private val validator = PersonaValidation()
    @Test
    @DisplayName("Check entrenador validation")
    fun validarEntrenador() {
        val entrenador = Entrenador(
            id = 1,
            nombre = "Entrenador",
            apellidos = "hola",
            salario = 12000.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            especialidad = Especialidad.ENTRENADOR_PRINCIPAL
        )
        val result= validator.validator(entrenador)
        assertTrue(result.isOk)
        assertEquals(result.value,entrenador,"deberian ser iguales")
    }

    @Test
    @DisplayName("Test de pasar el validador correctamente.")
    fun validarJugadorValido(){



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 12000.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isOk, "El validador debe devolver Ok")
        assertEquals(jugador, result.value, "El validador debe devolver el mismo jugador")
    }

    @Test
    @DisplayName("Test de nombre en blanco.")
    fun validarNombreVacio() {



        val jugador = Jugador (
            id = 1,
            nombre = "",
            apellidos = "Ortega",
            salario = 12000.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Nombre inválido, este campo no puede estar vacío.", result.error.message)
    }

    @Test
    @DisplayName("Test de nombre corto.")
    fun validarNombreCorto() {



        val jugador = Jugador (
            id = 1,
            nombre = "C",
            apellidos = "Ortega",
            salario = 12000.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )
        val result = validator.validator(jugador)
        assertTrue(result.isErr)
        assertEquals("Persona no válida: Nombre inválido, el nombre es demasiado corto.", result.error.message)
    }

    @Test
    @DisplayName("Test de apellidos vacíos.")
    fun validarApellidosVacios() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "",
            salario = 12000.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Apellidos inválidos, este campo no puede estar vacío.", result.error.message)
    }

    @Test
    @DisplayName("Test de apellidos cortos.")
    fun validarApellidosCortos() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "O",
            salario = 12000.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Apellidos inválidos, los apellidos son demasiado cortos.", result.error.message)
    }

    @Test
    @DisplayName("Test de salarios negativos o nulos.")
    fun validarSalarioBajo() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = -50.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Salario inválido, el salario no puede ser igual o menor a 0.", result.error.message)
    }

    @Test
    @DisplayName("Test de pais vacío.")
    fun validarPaisVacio() {



        val jugador = Jugador(
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: País inválido, este campo no puede estar vacío.", result.error.message)
    }

    @Test
    @DisplayName("Test de pais corto.")
    fun validarPaisCorto() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "E",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: País inválido, el país es demasiado corto.", result.error.message)
    }

    @Test
    @DisplayName("Test de dorsal negativo o 0.")
    fun validarDorsalBajo() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = -3,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Dorsal inválido, el dorsal no puede ser igual o inferior a 0.", result.error.message)
    }

    @Test
    @DisplayName("Test de dorsal superior a 99.")
    fun validarDorsalAlto() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 333,
            altura = 1.75,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Dorsal inválido, el dorsal no puede ser mayor a 99.", result.error.message)
    }

    @Test
    @DisplayName("Test de altura demasiado baja.")
    fun validarAlturaBaja() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 0.7,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Altura inválida, el jugador no puede ser tan bajo.", result.error.message)
    }

    @Test
    @DisplayName("Test de altura demasiado alta.")
    fun validarAlturaAlta() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 5.0,
            peso = 60.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Altura inválida, el jugador no puede ser tan alto.", result.error.message)
    }

    @Test
    @DisplayName("Test de peso insuficiente.")
    fun validarPesoBajo() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.6,
            peso = 15.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Peso inválido, necesita comer más.", result.error.message)
    }

    @Test
    @DisplayName("Test de peso excesivo.")
    fun validarPesoAlto() {



        val jugador = Jugador(
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.6,
            peso = 333.0,
            goles = 35,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Peso inválido, necesita comer menos.", result.error.message)
    }

    @Test
    @DisplayName("Test de goles negativos.")
    fun validarGolesNegativos() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.6,
            peso = 60.0,
            goles = -5,
            partidosJugados = 30,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Goles inválido, no puede tener goles negativos.", result.error.message)
    }

    @Test
    @DisplayName("Test de partidos jugados negativos.")
    fun validarPartidosJugadosNegativos() {



        val jugador = Jugador (
            id = 1,
            nombre = "Cristian",
            apellidos = "Ortega",
            salario = 1200.0,
            pais = "España",
            fechaNacimiento = LocalDate.of(2003,8,20),
            fechaIncorporacion = LocalDate.of(2025, 5,16),
            imagen = "https://www.hdwallpapers.in/download/cristiano_ronaldo_cr7_with_cup_in_blur_stadium_background_is_wearing_white_sports_dress_hd_cristiano_ronaldo-HD.jpg",
            posicion = Posicion.CENTROCAMPISTA,
            dorsal = 8,
            altura = 1.6,
            peso = 60.0,
            goles = 33,
            partidosJugados = -4,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            minutosJugados = 100
        )

        val result = validator.validator(jugador)

        assertTrue(result.isErr)
        assertEquals("Persona no válida: Partidos jugados inválidos, no puede jugar partidos negativos.", result.error.message)
    }
}