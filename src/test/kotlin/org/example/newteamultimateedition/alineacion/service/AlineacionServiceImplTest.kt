package org.example.newteamultimateedition.alineacion.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.alineacion.repository.AlineacionRepositoryImpl
import org.example.newteamultimateedition.alineacion.validador.AlineacionValidate
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class AlineacionServiceImplTest {
    @Mock
    private lateinit var cache: Cache<Long, Alineacion>
    @Mock
    private lateinit var repository: AlineacionRepositoryImpl
    @Mock
    private lateinit var personalService: PersonaServiceImpl
    @Mock
    private lateinit var validador: AlineacionValidate
    @InjectMocks
    private lateinit var service: AlineacionServiceImpl

    private val code= LineaAlineacion(
        id= UUID.fromString("7fa10f96-a0fc-4ccd-8aa5-5238a2642488"),
        idAlineacion = 1L,
        idPersona = 1L,
        posicion = 1
    )
    private val jugador = Jugador(
        id = 1L,
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
    val alineacion: Alineacion = Alineacion(
        id = 1L,
        personalList = listOf(code),
        juegoDate =LocalDate.now(),
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
    )

    @Nested
    @DisplayName("service good")
    inner class ServiceGood {
        @Test
        @DisplayName("service good con cache")
        fun getByFecha() {
            whenever(repository.getByDate(alineacion.juegoDate)) doReturn (alineacion)
            whenever(cache.getIfPresent(alineacion.id)) doReturn (alineacion)
            val result=service.getByFecha(alineacion.juegoDate)
            assertTrue(result.isOk,"deberia devolver Ok")
            assertEquals(alineacion,result.value,"deberian ser iguales")
            verify(cache, times(1)).getIfPresent(alineacion.id)
            verify(cache, times(0)).put(alineacion.id,alineacion)
            verify(repository, times(1)).getByDate(alineacion.juegoDate)
    }
        @Test
        @DisplayName("service good sin cache")
        fun getByFechaSinCache() {
            whenever(repository.getByDate(alineacion.juegoDate)) doReturn (alineacion)
            whenever(cache.getIfPresent(alineacion.id)) doReturn (null)

            val result=service.getByFecha(alineacion.juegoDate)
            assertTrue(result.isOk,"deberia devolver Ok")
            assertEquals(alineacion,result.value,"deberian ser iguales")
            verify(cache, times(1)).getIfPresent(alineacion.id)
            verify(cache, times(1)).put(alineacion.id,alineacion)
            verify(repository, times(1)).getByDate(alineacion.juegoDate)
        }

    @Test
    @DisplayName("personas good")
    fun getJugadores() {
        whenever(personalService.getAll()) doReturn Ok(listOf(jugador))
        val result=service.getJugadores()
        assertTrue(result.isOk)
        assertEquals(jugador,result.value.first(),"Deberia Ser el jugador")
        verify(personalService,times(1)).getAll()
    }

    @Test
    @DisplayName("personas good by lista")
    fun getJugadoresByLista() {
        whenever(personalService.getByID(jugador.id)) doReturn Ok(jugador)
        val result= service.getJugadoresByLista(alineacion.personalList)
        assertTrue(result.isOk)
        assertEquals(jugador,result.value.first(),"Deberia ser el Jugador")
    }

    @Test
    @DisplayName("personas good by lista by alineacion")
    fun getJugadoresByAlinecionId() {
        whenever(cache.getIfPresent(alineacion.id)) doReturn (alineacion)
        whenever(personalService.getByID(code.idPersona)) doReturn (Ok(jugador))
        val result= service.getJugadoresByAlinecionId(alineacion.id)
        assertTrue(result.isOk,"deberia devolver Ok")
        assertEquals(jugador,result.value.first(),"Deberia ser iguales")
        verify(personalService,times(1)).getByID(code.idPersona)
    }

    @Test
    fun getAll() {
        whenever(repository.getAll()) doReturn (listOf(alineacion))
        val result= service.getAll()
        assertTrue(result.isOk,"deberia devolver Ok")
        assertEquals(alineacion,result.value.first(),"Deberia Ser el jugador")
    }

    @Test
    @DisplayName("alineacion good by lista en cache")
    fun getByID() {
        whenever(cache.getIfPresent(alineacion.id)) doReturn (alineacion)
        val result=service.getByID(alineacion.id)
        assertTrue(result.isOk)
        assertEquals(alineacion,result.value,"deberian ser iguales")
        verify(cache, times(1)).getIfPresent(alineacion.id)
        verify(repository, times(0)).getById(alineacion.id)
        verify(cache, times(0)).put(alineacion.id,alineacion)
    }
        @Test
        @DisplayName("alineacion good sin cache")
        fun getByIDSinCache() {
            whenever(cache.getIfPresent(alineacion.id)) doReturn (null)
            whenever(repository.getById(alineacion.id)) doReturn (alineacion)
            val result=service.getByID(alineacion.id)
            assertTrue(result.isOk)
            assertEquals(alineacion,result.value,"deberian ser iguales")
            verify(cache, times(1)).getIfPresent(alineacion.id)
            verify(repository, times(1)).getById(alineacion.id)
            verify(cache, times(1)).put(alineacion.id,alineacion)
        }

    @Test
    fun save() {
        whenever(repository.save(alineacion)) doReturn (alineacion)
        whenever(validador.validator(alineacion)) doReturn (Ok(alineacion))
        val result = service.save(alineacion)
        assertTrue(result.isOk,"deberia devolver Ok")
        assertEquals(result.value,alineacion,"deberian ser iguales")
        verify(repository, times(1)).save(alineacion)
        verify(validador, times(1)).validator(alineacion)
    }

    @Test
    fun delete() {
        whenever(repository.delete(alineacion.id)) doReturn (alineacion)
        whenever(cache.getIfPresent(alineacion.id)) doReturn (alineacion)
        val result = service.delete(alineacion.id)
        assertTrue(result.isOk,"deberia devolver Ok")
        assertEquals(result.value,alineacion,"deberian ser iguales")
        verify(repository, times(1)).delete(alineacion.id)
        verify(cache, times(1)).invalidate(alineacion.id)
        verify(cache, times(1)).getIfPresent(alineacion.id)
    }
        @Test
        @DisplayName("delete sin cache")
        fun deleteSinCache() {
            whenever(repository.delete(alineacion.id)) doReturn (alineacion)
            whenever(cache.getIfPresent(alineacion.id)) doReturn (null)
            val result = service.delete(alineacion.id)
            assertTrue(result.isOk,"deberia devolver Ok")
            assertEquals(result.value,alineacion,"deberian ser iguales")
            verify(repository, times(1)).delete(alineacion.id)
            verify(cache, times(0)).invalidate(alineacion.id)
            verify(cache, times(1)).getIfPresent(alineacion.id)
        }

    @Test
    fun update() {
        whenever(validador.validator(alineacion)) doReturn (Ok(alineacion))
        whenever(repository.update(alineacion,alineacion.id)) doReturn (alineacion)
        whenever(cache.getIfPresent(alineacion.id)) doReturn (alineacion)
        val result = service.update(alineacion.id,alineacion)
        assertTrue(result.isOk,"deberia devolver Ok")
        assertEquals(result.value,alineacion,"deberian ser iguales")
        verify(repository, times(1)).update(alineacion,alineacion.id)
        verify(validador, times(1)).validator(alineacion)
        verify(cache, times(1)).put(alineacion.id,alineacion)
        verify(cache, times(1)).getIfPresent(alineacion.id)
        verify(cache, times(1)).invalidate(alineacion.id)
    }
        @Test
        @DisplayName("update sin cache")
        fun updateSinCache() {
            whenever(validador.validator(alineacion)) doReturn (Ok(alineacion))
            whenever(repository.update(alineacion,alineacion.id)) doReturn (alineacion)
            whenever(cache.getIfPresent(alineacion.id)) doReturn (null)
            val result = service.update(alineacion.id,alineacion)
            assertTrue(result.isOk,"deberia devolver Ok")
            assertEquals(result.value,alineacion,"deberian ser iguales")
            verify(repository, times(1)).update(alineacion,alineacion.id)
            verify(validador, times(1)).validator(alineacion)
            verify(cache, times(0)).put(alineacion.id,alineacion)
            verify(cache, times(1)).getIfPresent(alineacion.id)
            verify(cache, times(0)).invalidate(alineacion.id)
        }
}
    @Nested
    @DisplayName("AlineacionService test malos")
    inner class AlineacionServiceTest {
        @Test
        @DisplayName("get by fecha sin estar")
        fun getByFechaSinEstar() {
            whenever(repository.getByDate(alineacion.juegoDate)) doReturn (null)
            val result=service.getByFecha(alineacion.juegoDate)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("Alineacion no encontrada con id: ${alineacion.juegoDate}",result.error.message,"deberian ser iguales")
            verify(cache, times(0)).getIfPresent(alineacion.id)
            verify(cache, times(0)).put(alineacion.id,alineacion)
            verify(repository, times(1)).getByDate(alineacion.juegoDate)
        }
        @Test
        @DisplayName("get by fecha con excepcion")
        fun getByFechaException() {
            whenever(repository.getByDate(alineacion.juegoDate)) doThrow(RuntimeException("error en la base de datos"))

            val result=service.getByFecha(alineacion.juegoDate)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("error en la base de datos",result.error.message,"deberian ser iguales")
            verify(cache, times(0)).getIfPresent(alineacion.id)
            verify(cache, times(0)).put(alineacion.id,alineacion)
            verify(repository, times(1)).getByDate(alineacion.juegoDate)
        }
        @Test
        @DisplayName("get all personas with error")
        fun getJugadores() {
            whenever(personalService.getAll()) doThrow(RuntimeException("error en la base de datos"))
            val result=service.getJugadores()
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("error en la base de datos",result.error.message,"Deberian Ser iguales")
            verify(personalService,times(1)).getAll()
        }
        @Test
        @DisplayName("get jugadores by alineacion id not found")
        fun getJugadoresByAlinecionIdNotFound() {
            whenever(cache.getIfPresent(alineacion.id)) doReturn (null)
            whenever(repository.getById(alineacion.id)) doReturn (null)
            val result= service.getJugadoresByAlinecionId(alineacion.id)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("Alineacion no encontrada con id: 1",result.error.message,"Deberia ser iguales")
            verify(personalService,times(0)).getByID(code.idPersona)
        }
        @Test
        @DisplayName("get jugadores by alineacion id with list error")
        fun getJugadoresByAlinecionIdWithListError() {
            val list= listOf(code,code.copy(idPersona = 2L))
            whenever(cache.getIfPresent(alineacion.id)) doReturn (alineacion.copy(personalList = list))
            whenever(personalService.getByID(jugador.id)) doReturn Ok(jugador)
            whenever(personalService.getByID(2L)) doReturn Err(PersonasError.PersonaNotFoundError(2L))
            val result= service.getJugadoresByAlinecionId(alineacion.id)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("Persona no encontrada con id: 2",result.error.message,"Deberia ser iguales")
            verify(personalService,times(1)).getByID(code.idPersona)
        }
        @Test
        @DisplayName("get jugadores by alineacion id with Database error")
        fun getJugadoresByAlinecionId() {
            whenever(cache.getIfPresent(alineacion.id)) doReturn (alineacion)
            whenever(personalService.getByID(jugador.id)) doThrow(RuntimeException("error en la base de datos"))
            val result= service.getJugadoresByAlinecionId(alineacion.id)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("error en la base de datos",result.error.message,"Deberia ser iguales")
            verify(personalService,times(1)).getByID(code.idPersona)
        }
        @Test
        @DisplayName("get with error")
        fun getJugadoresByListaError() {
            val list= listOf(code,code.copy(idPersona = 2L))
            whenever(personalService.getByID(jugador.id)) doReturn Ok(jugador)
            whenever(personalService.getByID(2L)) doReturn Err(PersonasError.PersonaNotFoundError(2))
            val result= service.getJugadoresByLista(list)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("Persona no encontrada con id: 2",result.error.message,"Deberia ser el Jugador")
            verify(personalService,times(1)).getByID(jugador.id)
            verify(personalService,times(1)).getByID(2L)
        }
        @Test
        @DisplayName("get with database exception")
        fun getJugadoresByListaDatabaseException() {
            val list= listOf(code)
            whenever(personalService.getByID(jugador.id)) doThrow(RuntimeException("error en la base de datos"))
            val result= service.getJugadoresByLista(list)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("error en la base de datos",result.error.message,"Deberia ser el Jugador")
            verify(personalService,times(1)).getByID(jugador.id)
        }

        @Test
        @DisplayName("get all database exception")
        fun getAll() {
            whenever(repository.getAll()) doThrow(RuntimeException("error en la base de datos"))
            val result= service.getAll()
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("error en la base de datos",result.error.message,"Deberia Ser el jugador")
        }
        @Test
        @DisplayName("alineacion por id sin estar")
        fun getByIDSinEstar() {
            whenever(cache.getIfPresent(alineacion.id)) doReturn (null)
            whenever(repository.getById(alineacion.id)) doReturn (null)
            val result=service.getByID(alineacion.id)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("Alineacion no encontrada con id: 1",result.error.message,"deberian ser iguales")
            verify(cache, times(1)).getIfPresent(alineacion.id)
            verify(repository, times(1)).getById(alineacion.id)
            verify(cache, times(0)).put(alineacion.id,alineacion)
        }
        @Test
        @DisplayName("alineacion por id databaseError")
        fun getByIDErrorDatabase() {
            whenever(cache.getIfPresent(alineacion.id)) doReturn (null)
            whenever(repository.getById(alineacion.id)) doThrow(RuntimeException("error en la base de datos"))
            val result=service.getByID(alineacion.id)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals("error en la base de datos",result.error.message,"deberian ser iguales")
            verify(cache, times(1)).getIfPresent(alineacion.id)
            verify(repository, times(1)).getById(alineacion.id)
            verify(cache, times(0)).put(alineacion.id,alineacion)
        }
        @Test
        @DisplayName("alineacion error validacion")
        fun saveBad() {
            whenever(validador.validator(alineacion)) doReturn (Err(AlineacionError.AlineacionInvalidoError("tiene algo mal")))
            val result = service.save(alineacion)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals(result.error.message,"Alineacion no válida: tiene algo mal","deberian ser iguales")
            verify(repository, times(0)).save(alineacion)
            verify(validador, times(1)).validator(alineacion)
        }
        @Test
        @DisplayName("guardar con error de base de dato")
        fun saveBadDatabaseError() {
            whenever(repository.save(alineacion)) doThrow(RuntimeException("error en la base de datos"))
            whenever(validador.validator(alineacion)) doReturn Ok(alineacion)
            val result = service.save(alineacion)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals(result.error.message,"error en la base de datos","deberian ser iguales")
            verify(repository, times(1)).save(alineacion)
            verify(validador, times(1)).validator(alineacion)
        }
        @Test
        @DisplayName("delete sin estar")
        fun deleteNotFound() {
            whenever(repository.delete(alineacion.id)) doReturn (null)

            val result = service.delete(alineacion.id)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals(result.error.message,"Alineacion no encontrada con id: 1","deberian ser iguales")
            verify(repository, times(1)).delete(alineacion.id)
            verify(cache, times(0)).invalidate(alineacion.id)
            verify(cache, times(0)).getIfPresent(alineacion.id)
        }
        @Test
        @DisplayName("delete fallando base de datos")
        fun deleteDatabaseError() {
            whenever(repository.delete(alineacion.id)) doThrow(RuntimeException("error en la base de datos"))

            val result = service.delete(alineacion.id)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals(result.error.message,"error en la base de datos","deberian ser iguales")
            verify(repository, times(1)).delete(alineacion.id)
            verify(cache, times(0)).invalidate(alineacion.id)
            verify(cache, times(0)).getIfPresent(alineacion.id)
        }
        @Test
        @DisplayName("update fallando cache")
        fun updateValidatorFail() {
            whenever(validador.validator(alineacion)) doReturn (Err(AlineacionError.AlineacionInvalidoError("tiene algo mal")))
            val result = service.update(alineacion.id,alineacion)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals(result.error.message,"Alineacion no válida: tiene algo mal","deberian ser iguales")
            verify(repository, times(0)).update(alineacion,alineacion.id)
            verify(validador, times(1)).validator(alineacion)
            verify(cache, times(0)).put(alineacion.id,alineacion)
            verify(cache, times(0)).getIfPresent(alineacion.id)
            verify(cache, times(0)).invalidate(alineacion.id)
        }
        @Test
        @DisplayName("update sin estar el original")
        fun updateNotFound() {
            whenever(validador.validator(alineacion)) doReturn (Ok(alineacion))
            whenever(repository.update(alineacion,alineacion.id)) doReturn (null)
            val result = service.update(alineacion.id,alineacion)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals(result.error.message,"Alineacion no encontrada con id: 1","deberian ser iguales")
            verify(repository, times(1)).update(alineacion,alineacion.id)
            verify(validador, times(1)).validator(alineacion)
            verify(cache, times(0)).put(alineacion.id,alineacion)
            verify(cache, times(0)).getIfPresent(alineacion.id)
            verify(cache, times(0)).invalidate(alineacion.id)
        }
        @Test
        @DisplayName("update con database exception")
        fun updateDatabaseException() {
            whenever(validador.validator(alineacion)) doReturn (Ok(alineacion))
            whenever(repository.update(alineacion,alineacion.id)) doThrow(RuntimeException("error en la base de datos"))
            val result = service.update(alineacion.id,alineacion)
            assertTrue(result.isErr,"deberia devolver Err")
            assertEquals(result.error.message,"error en la base de datos","deberian ser iguales")
            verify(repository, times(1)).update(alineacion,alineacion.id)
            verify(validador, times(1)).validator(alineacion)
            verify(cache, times(0)).put(alineacion.id,alineacion)
            verify(cache, times(0)).getIfPresent(alineacion.id)
            verify(cache, times(0)).invalidate(alineacion.id)
        }
    }
}