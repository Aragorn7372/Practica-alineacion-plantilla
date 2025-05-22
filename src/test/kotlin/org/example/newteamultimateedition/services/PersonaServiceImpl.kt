package org.example.newteamultimateedition.services

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.models.Posicion
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl
import org.example.newteamultimateedition.personal.storage.EquipoStorageImpl
import org.example.newteamultimateedition.personal.validator.PersonaValidation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File
import java.nio.file.Path
import java.time.LocalDate


@ExtendWith(MockitoExtension::class)
class PersonaServiceImplTest {
    @Mock
    private lateinit var repository: PersonasRepositoryImplementation
    @Mock
    private lateinit var cache: Cache<Long, Persona>
    @Mock
    private lateinit var validator: PersonaValidation
    @Mock
    private lateinit var storage: EquipoStorageImpl
    @InjectMocks
    private lateinit var service: PersonaServiceImpl

    private val persona= Jugador(
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
        partidosJugados = 10,
        minutosJugados = 100,
        imagen = "jaskjndkjnas"
    )
    private val lista=listOf<Persona>(persona)
    private val file= File("xd.zip")



    @Test
    @DisplayName("importacion correctamente de jugador")
    fun importarDatosDesdeFichero() {
        whenever(repository.save(persona)) doReturn persona
        whenever(validator.validator(persona)) doReturn Ok(persona)
        whenever(storage.fileRead(file)) doReturn Ok(lista)


        val result= service.importarDatosDesdeFichero(Path.of("xd.zip"))
        assertEquals(persona, result.value.first(),"deben ser iguales")
        assertTrue(result.value.size == 1)
        assertTrue(result.isOk)
        verify(repository, times(1)).save(persona)
        verify(validator, times(1)).validator(persona)
        verify(storage, times(1)).fileRead(file)

    }

    @Test
    @DisplayName("importacion fallida de jugador")
    fun importarDatosDesdeFicheroDatoNombreIncorrecto() {

        //whenever(repository.save(persona)) doReturn persona
        whenever(validator.validator(persona)) doReturn Err(PersonasError.PersonasInvalidoError("nombre invalida"))
        whenever(storage.fileRead(file)) doReturn Err(PersonasError.PersonasStorageError("archivo invalido"))


        val result= service.importarDatosDesdeFichero(Path.of("xd.zip"))
        assertTrue(result.error is PersonasError.PersonasStorageError, "debe tener ese error")
        assertTrue(result.isErr,"debe ser un error")
        assertEquals(result.error.messager,"archivo invalido","deben ser iguales")
        verify(validator, times(0)).validator(persona)
        verify(storage, times(1)).fileRead(file)

    }

    @Test
    @DisplayName("exportacion correctamente de jugador")
    fun exportarDatosDesdeFichero() {
        val path= Path.of("xd.zip")
        whenever(storage.fileWrite(lista,path.toFile())) doReturn Ok(Unit)
        whenever(repository.getAll()) doReturn lista
        val result=service.exportarDatosDesdeFichero(path)
        assertTrue(result.isOk,"debe devolver Ok")
        assertEquals(result.value,"guardado con exito","devuelve el mensaje")
        verify(repository, times(1)).getAll()
        verify(storage, times(1)).fileWrite(lista,file)
    }

    @Test
    fun getAll() {
        whenever(repository.getAll()) doReturn lista
        val result = service.getAll()

        println(result)
        assertTrue(result.isOk)
        assertTrue(result.value.isNotEmpty(),"debe estar llena")
        assertEquals(lista.size,result.value.size,"deben ser iguales")
        verify(repository, times(1)).getAll()
    }

    @Test
    fun getByID() {}

    @Test
    fun save() {}

    @Test
    fun delete() {}

    @Test
    fun update() {}
}