package org.example.newteamultimateedition.personal.services

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.*
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.example.newteamultimateedition.personal.storage.EquipoStorageImpl
import org.example.newteamultimateedition.personal.validator.PersonaValidation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertAll
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDate
import java.time.LocalDateTime

class PersonaServiceImplTest {
    private lateinit var validator: PersonaValidation
    private lateinit var storage: EquipoStorageImpl
    private lateinit var repository: PersonasRepositoryImplementation
    private lateinit var cache: Cache<Long, Persona>
    private lateinit var service: PersonaServiceImpl

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

    private val invalidJugador = Jugador (
        id = 1,
        nombre = "",
        apellidos = "Ortega",
        salario = 12000.0,
        pais = "España",
        fechaNacimiento = LocalDate.of(2003,8,20),
        fechaIncorporacion = LocalDate.of(2025, 5,16),
        imagen = "brr brr patapim",
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

    private val entrenador = Entrenador(
        id = 1L,
        nombre = "Pepito",
        apellidos = "Grillo",
        fechaNacimiento = LocalDate.of(1942, 1, 1),
        fechaIncorporacion = LocalDate.of(2025, 2, 2),
        salario = 1234.0,
        pais = "Italia",
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        imagen = "pepito-grillo.png",
        especialidad = Especialidad.ENTRENADOR_PRINCIPAL
    )

    private val equipo = listOf(entrenador, jugador)

    @BeforeEach
    fun setUp(){
        validator = mock()
        storage = mock()
        repository = mock()
        cache = mock()
        service = PersonaServiceImpl(repository,cache,validator,storage)
    }

    @Nested
    @DisplayName("Tests correctos")
    inner class TestsCorrectos{

        @Test
        @DisplayName("Eliminar persona (también de caché)")
        fun eliminarPersonaOk(){
            whenever(repository.delete(entrenador.id)).thenReturn(entrenador)
            whenever(cache.getIfPresent(entrenador.id)).thenReturn(entrenador)

            val result = service.delete(entrenador.id)

            assertAll(
                { assertTrue(result.isOk) },
                { assertEquals(result.value, entrenador) }
            )

            verify(repository, times(1)).delete(entrenador.id)
            verify(cache, times(1)).getIfPresent(entrenador.id)
            verify(cache, times(1)).invalidate(entrenador.id)
        }

        @Test
        @DisplayName("Eliminar persona (no estaba en caché)")
        fun eliminarPersonaNoEstaEnCache() {
            whenever(repository.delete(entrenador.id)).thenReturn(entrenador)
            whenever(cache.getIfPresent(entrenador.id)).thenReturn(null)

            val result = service.delete(entrenador.id)

            assertAll(
                { assertTrue(result.isOk) },
                { assertEquals(entrenador, result.value) }
            )

            verify(repository, times(1)).delete(entrenador.id)
            verify(cache, times(1)).getIfPresent(entrenador.id)
            verify(cache, times(0)).invalidate(entrenador.id) // no debería invalidar porque no estaba en caché
        }

        @Test
        @DisplayName("Actualizar persona (no estaba en caché)")
        fun actualizarPersonaNoEstaEnCache() {

            whenever(validator.validator(jugador)).thenReturn(Ok(jugador))
            whenever(repository.update(jugador, jugador.id)).thenReturn(jugador)
            whenever(cache.getIfPresent(jugador.id)).thenReturn(null)

            val result = service.update(jugador.id, jugador)

            assertAll(
                { assertTrue(result.isOk) },
                { assertEquals(jugador, result.value) }
            )

            verify(validator, times(1)).validator(jugador)
            verify(repository, times(1)).update(jugador, jugador.id)
            verify(cache, times(1)).getIfPresent(jugador.id)
            verify(cache, times(0)).invalidate(jugador.id)
            verify(cache, times(0)).put(jugador.id, jugador)
        }

        @Test
        @DisplayName("Actualizar persona (también en caché)")
        fun actualizarPersonaEstaEnCache() {

            whenever(validator.validator(jugador)).thenReturn(Ok(jugador))
            whenever(repository.update(jugador, jugador.id)).thenReturn(jugador)
            whenever(cache.getIfPresent(jugador.id)).thenReturn(jugador)

            val result = service.update(jugador.id, jugador)

            assertAll(
                { assertTrue(result.isOk) },
                { assertEquals(jugador, result.value) }
            )

            verify(validator, times(1)).validator(jugador)
            verify(repository, times(1)).update(jugador, jugador.id)
            verify(cache, times(1)).getIfPresent(jugador.id)
            verify(cache, times(1)).invalidate(jugador.id)
            verify(cache, times(1)).put(jugador.id, jugador)
        }


        @Test
        @DisplayName("Guardar persona")
        fun guardarPersonaOk(){
            whenever(validator.validator(jugador)).thenReturn(Ok(jugador))
            whenever(repository.save(jugador)).thenReturn(jugador)

            val expected = jugador

            val actual = service.save(jugador)

            assertAll(
                {assertTrue(actual.isOk)},
                {assertEquals(expected, actual.value, "El resultado debe coincidir con lo esperado")},
            )

            verify(validator, times(1)).validator(jugador)
            verify(repository, times(1)).save(jugador)
        }

        @Test
        @DisplayName("Buscar por id de caché")
        fun getByIdFromCache(){
            whenever(cache.getIfPresent(jugador.id)).thenReturn(jugador)

            val expected = jugador
            val actual = service.getByID(jugador.id)

            assertAll(
                { assertTrue(actual.isOk) },
                { assertEquals(expected, actual.value) }
            )

            verify(cache, times(1)).getIfPresent(jugador.id)
            verify(repository, times(0)).getById(jugador.id)
        }

        @Test
        @DisplayName("Buscar por id en repositorio")
        fun getByIdFromRepository(){
            whenever(cache.getIfPresent(jugador.id)).thenReturn(null)
            whenever(repository.getById(jugador.id)).thenReturn(jugador)

            val expected = jugador
            val actual = service.getByID(jugador.id)

            assertAll(
                { assertTrue(actual.isOk) },
                { assertEquals(expected, actual.value) }
            )

            verify(cache, times(1)).getIfPresent(jugador.id)
            verify(repository, times(1)).getById(jugador.id)
            verify(cache, times(1)).put(jugador.id, jugador)
        }

        @Test
        @DisplayName("Obtener todos")
        fun getAllOk(){
            whenever(repository.getAll()).thenReturn(equipo)

            val result = service.getAll()

            assertAll(
                { assertTrue(result.isOk) },
                { assertEquals(result.value.size, equipo.size) },
                { assertEquals(result.value, equipo) },
            )

            verify(repository, times(1)).getAll()
        }
    }

    @Nested
    @DisplayName("Tests incorrectos")
    inner class TestsIncorrectos() {
        @Test
        @DisplayName("Buscar persona que no existe")
        fun getByIdNotExists() {
            whenever(cache.getIfPresent(jugador.id)).thenReturn(null)
            whenever(repository.getById(jugador.id)).thenReturn(null)

            val result = service.getByID(jugador.id)
            assertTrue(result.isErr)
            assertTrue(result.error is PersonasError.PersonaNotFoundError)

            verify(cache, times(1)).getIfPresent(jugador.id)
            verify(repository, times(1)).getById(jugador.id)
        }

        @Test
        @DisplayName("Guardar persona no válida")
        fun saveNotValidPlayer(){
            whenever(validator.validator(invalidJugador)).thenReturn(Err(PersonasError.PersonasInvalidoError("Mendrugo <3")))

            val result = service.save(invalidJugador)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonasInvalidoError) }
            )

            verify(validator, times(1)).validator(invalidJugador)
            verify(repository, times(0)).save(invalidJugador)
        }

        @Test
        @DisplayName("Eliminar persona que no existe")
        fun eliminarPersonaQueNoExiste(){
            whenever(repository.delete(entrenador.id)).thenReturn(null)

            val result = service.delete(entrenador.id)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonaNotFoundError) }
            )

            verify(repository, times(1)).delete(entrenador.id)
            verify(cache, times(0)).getIfPresent(entrenador.id)
            verify(cache, times(0)).invalidate(entrenador.id)
        }

        @Test
        @DisplayName("Falla bbdd al hacer getAll")
        fun bbddFailureGetAll(){

            whenever(repository.getAll()).thenThrow(RuntimeException("Error en la base de datos"))

            val result = service.getAll()

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonaDatabaseError) }
            )

            verify(repository, times(1)).getAll()
        }

        @Test
        @DisplayName("Falla bbdd al hacer getById")
        fun bbddFailureGetById(){

            whenever(repository.getById(entrenador.id)).thenThrow(RuntimeException("Error en la base de datos"))

            val result = service.getByID(entrenador.id)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonaDatabaseError) }
            )

            verify(repository, times(1)).getById(entrenador.id)
        }

        @Test
        @DisplayName("Falla bbdd al hacer save")
        fun bbddFailureSave(){

            whenever(validator.validator(entrenador)).thenReturn(Ok(entrenador))
            whenever(repository.save(entrenador)).thenThrow(RuntimeException("Error en la base de datos"))

            val result = service.save(entrenador)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonaDatabaseError) }
            )

            verify(validator, times(1)).validator(entrenador)
            verify(repository, times(1)).save(entrenador)
        }

        @Test
        @DisplayName("Falla bbdd al hacer delete")
        fun bbddFailureDelete(){

            whenever(repository.delete(entrenador.id)).thenThrow(RuntimeException("Error en la base de datos"))

            val result = service.delete(entrenador.id)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonaDatabaseError) }
            )

            verify(repository, times(1)).delete(entrenador.id)
            verify(cache, times(0)).getIfPresent(entrenador.id)
            verify(cache, times(0)).invalidate(entrenador.id)
        }

        @Test
        @DisplayName("Falla bbdd al hacer update")
        fun bbddFailureUpdate(){

            whenever(validator.validator(entrenador)).thenReturn(Ok(entrenador))
            whenever(repository.update(entrenador,entrenador.id)).thenThrow(RuntimeException("Error en la base de datos"))

            val result = service.update(entrenador.id, entrenador)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonaDatabaseError) }
            )

            verify(validator, times(1)).validator(entrenador)
            verify(repository, times(1)).update(entrenador, entrenador.id)
            verify(cache, times(0)).getIfPresent(entrenador.id)
            verify(cache, times(0)).invalidate(entrenador.id)
            verify(cache, times(0)).put(entrenador.id, entrenador)

        }

        @Test
        @DisplayName("Actualizar persona no válida")
        fun updateNotValid(){

            whenever(validator.validator(invalidJugador)).thenReturn(Err(PersonasError.PersonasInvalidoError("Nombre inválido, este campo no puede estar vacío.")))

            val result = service.update(invalidJugador.id, invalidJugador)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonasInvalidoError) }
            )

            verify(validator, times(1)).validator(invalidJugador)
            verify(repository, times(0)).update(invalidJugador, invalidJugador.id)
            verify(cache, times(0)).getIfPresent(invalidJugador.id)
            verify(cache, times(0)).invalidate(invalidJugador.id)
            verify(cache, times(0)).put(invalidJugador.id, invalidJugador)
        }

        @Test
        @DisplayName("Actualizar persona que no existe")
        fun updateNotExisting(){

            whenever(validator.validator(jugador)).thenReturn(Ok(jugador))
            whenever(repository.update(jugador, jugador.id)).thenReturn(null)

            val result = service.update(jugador.id, jugador)

            assertAll(
                { assertTrue(result.isErr) },
                { assertTrue(result.error is PersonasError.PersonaNotFoundError) }
            )

            verify(validator, times(1)).validator(jugador)
            verify(repository, times(1)).update(jugador, jugador.id)
            verify(cache, times(0)).getIfPresent(jugador.id)
            verify(cache, times(0)).invalidate(jugador.id)
            verify(cache, times(0)).put(jugador.id, jugador)
        }

    }
}