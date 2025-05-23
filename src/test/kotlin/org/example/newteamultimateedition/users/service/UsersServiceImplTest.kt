package org.example.newteamultimateedition.users.service

import org.example.newteamultimateedition.users.exception.UsersException
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.users.repository.UsersRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.mockito.kotlin.times

@ExtendWith(MockitoExtension::class)
class UsersServiceImplTest {

    @Mock
    private lateinit var repository: UsersRepository

    @InjectMocks
    private lateinit var service: UsersServiceImpl

    private val user = User(
        name = "admin",
        password = "1234",
        isAdmin = true
    )

    private val userId = "admin"
    private val users = listOf(user)

    @Test
    @DisplayName("getAll devuelve lista de usuarios correctamente")
    fun getAllCorrecto() {
        whenever(repository.getAll()) doReturn users

        val result = service.getAll()

        assertTrue(result.isOk)
        assertEquals(1, result.value.size)
        assertEquals(user, result.value.first())
        verify(repository, times(1)).getAll()
    }

    @Test
    @DisplayName("getAll devuelve error si hay una excepción en la base de datos")
    fun getAllError() {
        val exceptionMessage = "Error de base de datos"
        whenever(repository.getAll()).thenThrow(RuntimeException(exceptionMessage))

        val result = service.getAll()

        assertTrue(result.isErr)
        assertTrue(result.error is UsersException.DatabaseException)
        assertEquals(exceptionMessage, (result.error as UsersException.DatabaseException).messager)
        verify(repository, times(1)).getAll()
    }

    @Test
    @DisplayName("getByID devuelve un usuario correctamente")
    fun getByIdCorrecto() {
        whenever(repository.getById(userId)) doReturn user

        val result = service.getByID(userId)

        assertTrue(result.isOk)
        assertEquals(user, result.value)
        verify(repository, times(1)).getById(userId)
    }

    @Test
    @DisplayName("getByID lanza excepción y devuelve DatabaseException")
    fun getByIDLanzaExcepcionDevuelveDatabaseException() {
        whenever(repository.getById("123")).thenThrow(RuntimeException("Error"))

        val result = service.getByID("123")

        assertTrue(result.isErr)
        assertTrue((result.error is UsersException.DatabaseException))
    }

    @Test
    @DisplayName("save guarda usuario correctamente")
    fun saveCorrecto() {
        whenever(repository.save(user)) doReturn user

        val result = service.save(user)

        assertTrue(result.isOk)
        assertEquals(user, result.value)
        verify(repository, times(1)).save(user)
    }

    @Test
    @DisplayName("save devuelve error si hay una excepción en la base de datos")
    fun saveError() {
        val exceptionMessage = "Fallo al guardar"
        whenever(repository.save(user)).thenThrow(RuntimeException(exceptionMessage))

        val result = service.save(user)

        assertTrue(result.isErr)
        assertTrue(result.error is UsersException.DatabaseException)
        assertEquals(exceptionMessage, (result.error as UsersException.DatabaseException).messager)
        verify(repository, times(1)).save(user)
    }

    @Test
    @DisplayName("delete elimina usuario correctamente")
    fun deleteCorrecto() {
        whenever(repository.delete(userId)) doReturn user

        val result = service.delete(userId)

        assertTrue(result.isOk)
        assertEquals(user, result.value)
        verify(repository, times(1)).delete(userId)
    }

    @Test
    @DisplayName("delete devuelve error si usuario no existe")
    fun deleteError() {
        whenever(repository.delete(userId)) doReturn null

        val result = service.delete(userId)

        assertTrue(result.isErr)
        assertTrue(result.error is UsersException.UsersNotFoundException)
        verify(repository, times(1)).delete(userId)
    }

    @Test
    @DisplayName("delete lanza excepción y devuelve DatabaseException")
    fun deleteLanzaExcepcionDevuelveDatabaseException() {
        whenever(repository.delete("123")).thenThrow(RuntimeException("Error"))

        val result = service.delete("123")

        assertTrue(result.isErr)
        assertTrue((result.error is UsersException.DatabaseException))
    }

    @Test
    @DisplayName("update actualiza usuario correctamente")
    fun updateCorrecto() {
        whenever(repository.update(user, userId)) doReturn user

        val result = service.update(userId, user)

        assertTrue(result.isOk)
        assertEquals(user, result.value)
        verify(repository, times(1)).update(user, userId)
    }

    @Test
    @DisplayName("update falla si no existe el usuario")
    fun updateError() {
        whenever(repository.update(user, userId)) doReturn null

        val result = service.update(userId, user)

        assertTrue(result.isErr)
        assertTrue(result.error is UsersException.UsersNotFoundException)
        verify(repository, times(1)).update(user, userId)
    }

    @Test
    @DisplayName("update lanza excepción y devuelve DatabaseException")
    fun updateLanzaExcepcionDevuelveDatabaseException() {
        val user = mock<User>()
        whenever(repository.update(user, "123")).thenThrow(RuntimeException("Error"))

        val result = service.update("123", user)

        assertTrue(result.isErr)
        assertTrue((result.error is UsersException.DatabaseException))
    }

}
