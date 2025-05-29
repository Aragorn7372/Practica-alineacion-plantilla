package org.example.newteamultimateedition.users.dao

import org.example.newteamultimateedition.common.database.DatabaseManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersDaoTest {
    private lateinit var dao: UsersDao

    val usersEntity= UsersEntity(
        name = "admin",
        password = "admin",
        admin = true
    )

    @BeforeAll
    fun setUp() {
        val jdbi = DatabaseManager(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            initData = true,
            initTables = true,
        ).jdbi
        dao = provideUsersDao(jdbi)
        dao.deleteAll()
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
            dao.save(usersEntity)
            val users = dao.getByName("admin")
            assertNotNull(users, "no deberia ser nulo")
            assertEquals(users!!.name, usersEntity.name, "deberian ser iguales")
        }

        @Test
        @DisplayName("eliminar un usuario")
        fun eliminarPersona() {
            dao.save(usersEntity)
            val result = dao.delete("admin")
            val result2 = dao.getByName("admin")
            assertNull(result2, "no deberia estar")
            assertEquals(result, 1, "deberia haber cambiado un usuario")
        }

        @Test
        @DisplayName("obtener a todos los usuarios")
        fun obtenerPersonas() {
            dao.save(usersEntity)
            dao.save(usersEntity.copy(name = "Carlos"))
            dao.save(usersEntity.copy(name = "samu", password = "admin"))
            val todos = dao.getAll()
            todos.forEach { println(it) }
            val result = dao.getAll()
            assertEquals(result.size, 3, "deberian haber tres jugadores")
            assertTrue(result.any { it.name == "admin" }, "deberia haber un usuario con ese nombre")
            assertTrue(result.any { it.name == "Carlos" }, "Deberia haber un usuario con ese nombre")
            assertTrue(result.any { it.name == "samu" }, "deberia haber alguien no admin")
        }

        @Test
        @DisplayName("actualizar correctamente")
        fun actualizarPersona() {
            dao.save(usersEntity)
            val result = dao.getByName("admin")
            val result2 = dao.update(usersEntity.copy(password = "Carlos"),"admin")
            val result3 = dao.getByName("admin")
            assertNotEquals(result!!.password, result3!!.password,"No deberian ser iguales")
            assertTrue(result2==1)

        }
        @Test
        @DisplayName("obtener por id estando")
        fun obtenerPersona() {
            dao.save(usersEntity)
            val result=dao.getByName("admin")
            assertNotNull(result,"No deberia ser nulo")
            assertEquals(result!!.name, usersEntity.name, "deberia tener el mismo name")

        }
        @Test
        @DisplayName("eliminar a todos los usuarios")
        fun eliminarPersonas() {
            dao.save(usersEntity)
            dao.save(usersEntity.copy(name="carlos"))
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
        dao.save(usersEntity)
        val result = dao.getByName("mariano")
        assertNull(result, "deberia ser nulo")
    }
    @Test
    @DisplayName("actualizar por id no estando")
    fun actualizarPorIdNoEstando(){
        dao.save(usersEntity)
        val result = dao.update(usersEntity.copy(admin = false),"alfonso")
        assertTrue(result==0,"no deberia haber modificado alguna fila")
    }
    @Test
    @DisplayName("eliminar no estando")
    fun eliminarNoEstando(){
        dao.save(usersEntity)
        val result = dao.delete("alfonso")
        assertTrue(result==0,"no deberia haber modificado alguna fila")
    }

}