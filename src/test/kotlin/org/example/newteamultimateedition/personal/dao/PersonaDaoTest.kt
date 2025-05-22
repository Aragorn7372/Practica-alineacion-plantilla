package org.example.newteamultimateedition.personal.dao
import org.example.newteamultimateedition.common.database.DatabaseManager
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.models.Posicion
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertAll
import java.time.LocalDate
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonaDaoTest {
    private lateinit var personaDao: PersonaDao
    private lateinit var dao: PersonaDao

    val persona= PersonaEntity(
        id = 1L,
        nombre = "Jugadora",
        rol = "Jugador",
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
        imagen = "jaskjndkjnas",
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        )

    @BeforeAll
    fun setUp() {
        val jdbi = DatabaseManager(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            true,
            true
        ).jdbi
        dao = getPersonasDao(jdbi)
    }
    @Test
    @DisplayName("insertar")
}