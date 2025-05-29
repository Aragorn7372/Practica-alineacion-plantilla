package org.example.newteamultimateedition.personal.dao

import org.example.newteamultimateedition.common.database.DatabaseManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonaDaoTest {
    private lateinit var dao: PersonaDao

    val personaEntity= PersonaEntity(
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
        isDeleted = false
        )

    @BeforeAll
    fun setUp() {
        val jdbi = DatabaseManager(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            initData = true,
            initTables = true,
        ).jdbi
        dao = getPersonasDao(jdbi)
    }
    @AfterEach
    fun tearDown() {
        dao.deleteAll()
    }
    @Nested
    @DisplayName("casos correctos")
    inner class DaoTestCorrectos {
        @Test
        @DisplayName("insertar una persona")
        fun savePersona() {
            val id = dao.save(personaEntity)
            val persona = dao.getById(id.toLong())
            assertNotNull(persona, "no deberia ser nulo")
            assertEquals(persona!!.nombre, personaEntity.nombre, "deberian ser iguales")
        }

        @Test
        @DisplayName("eliminar una persona")
        fun eliminarPersona() {
            val id = dao.save(personaEntity)
            val result = dao.deleteById(id.toLong())
            val result2 = dao.getById(id.toLong())
            assertNull(result2, "no deberia estar")
            assertEquals(result, 1, "deberia haber cambiado a una persona")
        }

        @Test
        @DisplayName("obtener a todas las personas")
        fun obtenerPersonas() {
            dao.save(personaEntity)
            dao.save(personaEntity.copy(nombre = "Carlos"))
            dao.save(personaEntity.copy(peso = 200.0))
            val todos = dao.getAll()
            todos.forEach { println(it) }
            val result = dao.getAll()
            assertEquals(result.size, 3, "deberian haber tres jugadores")
            assertTrue(result.any { it.nombre == "Jugadora" }, "deberia haber un jugador con ese nombre")
            assertTrue(result.any { it.nombre == "Carlos" }, "Deberia haber un jugador con ese nombre")
            assertTrue(result.any { it.peso == 200.0 }, "deberia haber alguien con ese peso")
        }

        @Test
        @DisplayName("actualizar correctamente")
        fun actualizarPersona() {
            val id = dao.save(personaEntity)
            val result = dao.getById(id.toLong())
            val result2 = dao.update(personaEntity.copy(nombre = "Carlos"),id.toLong())
            val result3 = dao.getById(id.toLong())
            assertNotEquals(result!!.nombre, result3!!.nombre,"No deberian ser iguales")
            assertTrue(result2==1)

        }
        @Test
        @DisplayName("obtener por id estando")
        fun obtenerPersona() {
            val id=dao.save(personaEntity)
            val result=dao.getById(id.toLong())
            assertNotNull(result,"No deberia ser nulo")
            assertEquals(result!!.nombre, personaEntity.nombre, "deberia tener el mismo nombre")

        }
        @Test
        @DisplayName("eliminar a todos las personas")
        fun eliminarPersonas() {
            dao.save(personaEntity)
            dao.save(personaEntity.copy(peso = 200.0))
            val result = dao.deleteAll()
            val result2 = dao.getAll()
            assertTrue(result2.isEmpty(), "deberia ser nulo")
            assertEquals(result,2,"deberian haberse modificado solo dos lineas")
        }
    }
    @Nested
    @DisplayName("casos incorrectos")
    inner class DaoIncorrectos {
        @Test
        @DisplayName("get all vacio")
        fun getVacio(){
            val result = dao.getAll()
            assertTrue(result.isEmpty(), "deberia estar vacio")
            assertEquals(result.size,0,"deberia ser cero")
        }
    }
    @Test
    @DisplayName("obtener id no estando")
    fun obtenerIdNoEstando(){
        dao.save(personaEntity)
        val result = dao.getById(565L)
        assertNull(result, "deberia ser nulo")
    }
    @Test
    @DisplayName("actualizar por id no estando")
    fun actualizarPorIdNoEstando(){
        dao.save(personaEntity)
        val result = dao.update(personaEntity.copy(peso = 200.0),656L)
        assertTrue(result==0,"no deberia haber modificado alguna fila")
    }
    @Test
    @DisplayName("eliminar no estando")
    fun eliminarNoEstando(){
        dao.save(personaEntity)
        val result = dao.deleteById(565L)
        assertTrue(result==0,"no deberia haber modificado alguna fila")
    }

}