package org.example.newteamultimateedition.alineacion.dao

import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.common.database.DatabaseManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.time.LocalDateTime


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlineacionDaoTest {
        private lateinit var dao: AlineacionDao

        val alineacionEntity= AlineacionEntity(
            id= 1L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            juegoDate = LocalDate.now(),
            idEntrenador = 1L
        )


        @BeforeAll
        fun setUp() {
            val jdbi = DatabaseManager(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                initData = true,
                initTables = true,
            ).jdbi
            dao = provideAlineacionDao(jdbi)
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
                val id = dao.save(alineacionEntity)
                val codigo = dao.getById(id.toLong())
                assertNotNull(codigo, "no deberia ser nulo")
                assertEquals(codigo!!.createdAt.second, alineacionEntity.createdAt.second, "deberian ser iguales")
            }

            @Test
            @DisplayName("eliminar una Alineacion")
            fun eliminarPersona() {
                val id = dao.save(alineacionEntity)
                val result = dao.deleteById(id.toLong())
                val result2 = dao.getById(id.toLong())
                assertNull(result2, "no deberia estar")
                assertEquals(result, 1, "deberia haber eliminado una alineacion")
            }

            @Test
            @DisplayName("obtener todas las alineaciones")
            fun obtenerPersonas() {
                val id1=dao.save(alineacionEntity)
                val id2=dao.save(alineacionEntity.copy(updatedAt = LocalDateTime.now()))
                val id3=dao.save(alineacionEntity.copy(updatedAt = LocalDateTime.now()))
                val todos = dao.getAll()
                todos.forEach { println(it) }
                val result = dao.getAll()
                assertEquals(result.size, 3, "deberian haber tres Alineaciones")
                assertTrue(result.any { it.id == id1.toLong() }, "Deberian tener ese codigo")
                assertTrue(result.any { it.id == id2.toLong() }, "Deberian tener ese codigo")
                assertTrue(result.any { it.id == id3.toLong() }, "Deberian tener ese codigo")
            }

            @Test
            @DisplayName("actualizar correctamente")
            fun actualizarPersona() {
                val id = dao.save(alineacionEntity)
                val result = dao.getById(id.toLong())
                val result2 = dao.updateById(alineacionEntity.copy(updatedAt = LocalDateTime.now()),id.toLong())
                val result3 = dao.getById(id.toLong())
                assertNotEquals(result!!.updatedAt, result3!!.updatedAt,"No deberian ser iguales")
                assertEquals(result.id, result3.id,"deberian ser iguales")
                assertTrue(result2==1)
            }
            @Test
            @DisplayName("obtener por id estando")
            fun obtenerPersona() {
                val id=dao.save(alineacionEntity)
                val result=dao.getById(id.toLong())
                assertNotNull(result,"No deberia ser nulo")
                assertEquals(result!!.juegoDate, alineacionEntity.juegoDate, "deberia tener el mismo id")

            }
            @Test
            @DisplayName("eliminar todos los codigos")
            fun eliminarAlineaciones() {
                dao.save(alineacionEntity)
                dao.save(alineacionEntity.copy(updatedAt = LocalDateTime.now()))
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
            dao.save(alineacionEntity)
            val result = dao.getById(2)
            assertNull(result, "deberia ser nulo")
        }
        @Test
        @DisplayName("actualizar por id no estando")
        fun actualizarPorIdNoEstando(){
            dao.save(alineacionEntity)
            val result = dao.updateById(alineacionEntity.copy(updatedAt = LocalDateTime.now()),600)
            assertTrue(result==0,"no deberia haber modificado alguna fila")
        }
        @Test
        @DisplayName("eliminar no estando")
        fun eliminarNoEstando(){
            dao.save(alineacionEntity)
            val result = dao.deleteById(2)
            assertTrue(result==0,"no deberia haber modificado alguna fila")
        }

    }