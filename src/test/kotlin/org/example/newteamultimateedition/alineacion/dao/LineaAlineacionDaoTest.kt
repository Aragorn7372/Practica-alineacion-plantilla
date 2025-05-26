package org.example.newteamultimateedition.alineacion.dao

import org.example.newteamultimateedition.common.database.DatabaseManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LineaAlineacionDaoTest {
    private lateinit var dao: LineaAlineacionDao

    private val codigoEntity= LineaAlineacionEntity(
        id= "ash",
        personalId = 1,
        alineacionId = 1,
        posicion = 1
    )

    @BeforeAll
    fun setUp() {
        val jdbi = DatabaseManager(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            initData = true,
            initTables = true,
        ).jdbi
        dao = provideCodigoDao(jdbi)
    }
    @AfterEach
    fun tearDown() {
        dao.deleteAll()
    }
    @Nested
    @DisplayName("casos correctos")
    inner class DaoTestCorrectos {
        @Test
        @DisplayName("insertar una codigo")
        fun savePersona() {
            dao.save(codigoEntity)
            val codigo = dao.getById("ash")
            assertNotNull(codigo, "no deberia ser nulo")
            assertEquals(codigo!!.personalId, codigoEntity.personalId, "deberian ser iguales")
        }

        @Test
        @DisplayName("eliminar un codigo")
        fun eliminarPersona() {
            dao.save(codigoEntity)
            val result = dao.deleteById("ash")
            val result2 = dao.getById("ash")
            assertNull(result2, "no deberia estar")
            assertEquals(result, 1, "deberia haber eliminado a una persona")
        }

        @Test
        @DisplayName("obtener todos los codigos")
        fun obtenerPersonas() {
            dao.save(codigoEntity)
            dao.save(codigoEntity.copy("sudo"))
            dao.save(codigoEntity.copy("sudo2"))
            val todos = dao.getAll()
            todos.forEach { println(it) }
            val result = dao.getAll()
            assertEquals(result.size, 3, "deberian haber tres jugadores")
            assertTrue(result.any { it.id == "sudo2" }, "deberia haber un codigo con ese nombre")
            assertTrue(result.any { it.id == "sudo" }, "Deberia haber un codigo con ese nombre")
            assertTrue(result.any { it.id == "ash" }, "Deberia haber un codigo con ese nombre")
        }

        @Test
        @DisplayName("actualizar correctamente")
        fun actualizarPersona() {
            dao.save(codigoEntity)
            val result = dao.getById("ash")
            val result2 = dao.updateById(codigoEntity.copy(posicion = 3),"ash")
            val result3 = dao.getById("ash")
            assertNotEquals(result!!.posicion, result3!!.posicion,"No deberian ser iguales")
            assertEquals(result.id, result3.id,"deberian ser iguales")
            assertTrue(result2==1)
        }
        @Test
        @DisplayName("obtener por id estando")
        fun obtenerPersona() {
            dao.save(codigoEntity)
            val result=dao.getById("ash")
            assertNotNull(result,"No deberia ser nulo")
            assertEquals(result!!.id, codigoEntity.id, "deberia tener el mismo nombre")

        }
        @Test
        @DisplayName("eliminar todos los codigos")
        fun eliminarPersonas() {
            dao.save(codigoEntity)
            dao.save(codigoEntity.copy(id = "sudo"))
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
        dao.save(codigoEntity)
        val result = dao.getById("sudo")
        assertNull(result, "deberia ser nulo")
    }
    @Test
    @DisplayName("actualizar por id no estando")
    fun actualizarPorIdNoEstando(){
        dao.save(codigoEntity)
        val result = dao.updateById(codigoEntity.copy(personalId = 2),"sudo")
        assertTrue(result==0,"no deberia haber modificado alguna fila")
    }
    @Test
    @DisplayName("eliminar no estando")
    fun eliminarNoEstando(){
        dao.save(codigoEntity)
        val result = dao.deleteById("sudo")
        assertTrue(result==0,"no deberia haber modificado alguna fila")
    }

}