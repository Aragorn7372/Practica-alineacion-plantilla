package org.example.newteamultimateedition.users.mapper
import org.example.newteamultimateedition.users.dao.UsersEntity
import org.example.newteamultimateedition.users.models.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UsersMapperTest {

    private val mapper = UsersMapper()

    private val user = User(
        name = "nombre",
        password = "contraseña",
        isAdmin = true
    )

    private val userEntity = UsersEntity(
        name = "nombre",
        password = "contraseña",
        admin = true
    )

    @Nested
    @DisplayName("Tests correctos")
    inner class TestsCorrectos {
        @Test
        @DisplayName("User a UsersEntity")
        fun userToEntity() {
            val expected = userEntity

            val result = mapper.toEntity(user)

            assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
        }

        @Test
        @DisplayName("UsersEntity a User")
        fun entityToUser() {
            val expected = user

            val result = mapper.toModel(userEntity)

            assertEquals(expected, result, "El resultado del mapeo debe coincidir con lo esperado.")
        }

    }
}