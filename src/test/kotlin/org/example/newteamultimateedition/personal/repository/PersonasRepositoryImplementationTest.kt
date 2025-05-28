package org.example.newteamultimateedition.personal.repository


import org.example.newteamultimateedition.personal.dao.PersonaDao
import org.example.newteamultimateedition.personal.dao.PersonaEntity
import org.example.newteamultimateedition.personal.models.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDate
import java.time.LocalDateTime


@ExtendWith(MockitoExtension::class)
class PersonasRepositoryImplementationTest {
    @Mock
    private lateinit var dao: PersonaDao

    @InjectMocks
    private lateinit var repository: PersonasRepositoryImplementation

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
        imagen = "jaskjndkjnas",
        isDeleted = false
    )
    private val persona2 = Entrenador(
        id = 2,
        nombre = "Entrenadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
        imagen = "oijsdoiasjd",
        isDeleted = false
    )
    private val persona3 = PersonaEntity(
        id = 2,
        rol = "Entrenador",
        nombre = "Entrenadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        especialidad = "ENTRENADOR_PRINCIPAL",
        imagen = "oijsdoiasjd",
        createdAt = persona2.createdAt,
        updatedAt = persona2.updatedAt,
        isDeleted = false
    )
    private val persona4 = PersonaEntity(
        id = 1,
        rol = "Jugador",
        nombre = "Jugadora",
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
        createdAt = persona.createdAt,
        updatedAt = persona.updatedAt,
        isDeleted = false
    )
    class PersonaRemix(
        id: Long = 0L,
        nombre: String,
        apellidos: String,
        fechaNacimiento: LocalDate,
        fechaIncorporacion: LocalDate,
        salario: Double,
        pais: String,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
        imagen: String,
    ): Persona(id = id, nombre = nombre, apellidos = apellidos, fechaNacimiento = fechaNacimiento, fechaIncorporacion = fechaIncorporacion, salario = salario, pais = pais, createdAt = createdAt,updatedAt = updatedAt, imagen = imagen)
    private val personaRemix = PersonaRemix(
        id = 1,
        nombre = "Jugadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        imagen = "jaskjndkjnas"
    )

    @Test
    fun getAll() {
        whenever(dao.getAll()) doReturn listOf(persona3, persona4)


        val result=repository.getAll()
        assertEquals(result.size,2,"debe contener dos personas")
        assertNotNull(result,"no deberia ser nulo")
        assertTrue(result.isNotEmpty())

        verify(dao,times(1)).getAll()

    }

    @Test
    @DisplayName("get id  estando esa persona")
    fun getById() {
        whenever(dao.getById(2)) doReturn persona3


        val result=repository.getById(persona2.id)
        assertEquals(result!!.nombre,persona2.nombre,"deberia devolver la persona3")
        assertNotNull(result,"no deberia ser nulo")
        assertEquals(result.id,persona2.id,"deberia devolver la persona3")

        verify(dao,times(1)).getById(2)

    }
    @Test
    @DisplayName("get id  estando ese jugador")
    fun getByIdJugador() {
        whenever(dao.getById(1)) doReturn persona4


        val result=repository.getById(persona.id)
        assertEquals(result!!.nombre,persona.nombre,"deberia devolver la persona")
        assertNotNull(result,"no deberia ser nulo")
        assertEquals(result.id,persona.id,"deberia devolver la persona")

        verify(dao,times(1)).getById(1)

    }
    @Test
    @DisplayName("get id sin estar esa persona")
    fun getByIdBad() {
        whenever(dao.getById(3)) doReturn null

        val result=repository.getById(3.toLong())

        assertNull(result,"deberia ser nulo")

        verify(dao,times(1)).getById(3)
    }

    @Test
    @DisplayName("update estando bien")
    fun update() {
        whenever(dao.update(persona3,persona3.id)) doReturn 1



        val result=repository.update(persona2,persona2.id)

        assertEquals(result!!.nombre,persona2.nombre,"deberia devolver la persona2")
        assertEquals(result.apellidos,persona2.apellidos,"deberia devolver la persona2")
        assertNotNull(result,"no deberia ser nulo")
        assertEquals(result.id,persona2.id,"deberia devolver la persona2")

        verify(dao,times(1)).update(persona3,persona3.id)

    }
    @Test
    @DisplayName("update estando bien jugador")
    fun updateJugador() {
        whenever(dao.update(persona4,persona4.id)) doReturn 1



        val result=repository.update(persona,persona.id)

        assertEquals(result!!.nombre,persona.nombre,"deberia devolver la persona")
        assertEquals(result.apellidos,persona.apellidos,"deberia devolver la persona")
        assertNotNull(result,"no deberia ser nulo")
        assertEquals(result.id,persona.id,"deberia devolver la persona")

        verify(dao,times(1)).update(persona4,persona4.id)

    }
    @Test
    @DisplayName("update no estando")
    fun updateNotPersona() {
        whenever(dao.update(persona3,persona3.id)) doReturn 0


        val result=repository.update(persona2,persona2.id)

        assertNull(result,"no deberia ser nulo")


        verify(dao,times(1)).update(persona3,persona3.id)

    }
    @Test
    @DisplayName("delete persona fisico")
    fun deleteFisico() {
        whenever(dao.getById(persona3.id)) doReturn persona3
        whenever(dao.deleteById(persona3.id)) doReturn 1


        val result=repository.deleteFisico(persona2.id)

        assertEquals(result!!.nombre,persona2.nombre,"deberia devolver la persona2")
        assertNotNull(result,"no deberia ser nulo")
        assertEquals(result.id,persona2.id,"deberia devolver la persona2")

        verify(dao, times(1)).deleteById(persona3.id)

        verify(dao, times(1)).getById(persona3.id)
    }
    @Test
    @DisplayName("delete persona fisico not deleted")
    fun deleteFisicoNotDeleted() {
        whenever(dao.getById(persona3.id)) doReturn persona3
        whenever(dao.deleteById(persona3.id)) doReturn 0


        val result=repository.deleteFisico(persona2.id)


        assertNull(result,"deberia ser nulo")


        verify(dao, times(1)).deleteById(persona3.id)

        verify(dao, times(1)).getById(persona3.id)
    }
    @Test
    @DisplayName("delete persona fisico bad, Not Found")
    fun deleteFisicoNotFound() {
        whenever(dao.getById(persona3.id)) doReturn null



        val result=repository.deleteFisico(persona2.id)


        assertNull(result,"no deberia ser nulo")


        verify(dao, times(0)).deleteById(persona3.id)

        verify(dao, times(1)).getById(persona3.id)
    }

    @Test
    @DisplayName("delete persona")
    fun delete() {
        whenever(dao.getById(persona3.id)) doReturn persona3
        whenever(dao.update(persona3.copy(isDeleted=true),persona3.id)) doReturn 1


        val result=repository.delete(persona2.id)

        assertEquals(result!!.nombre,persona2.nombre,"deberia devolver la persona2")
        assertNotNull(result,"no deberia ser nulo")
        assertEquals(result.id,persona2.id,"deberia devolver la persona2")

        verify(dao,times(1)).update(persona3.copy(isDeleted=true),persona3.id)

        verify(dao, times(1)).getById(persona3.id)
    }
    @Test
    @DisplayName("delete no estando")
    fun deleteNotPersona() {
        whenever(dao.getById(persona3.id)) doReturn null



        val result=repository.delete(persona2.id)

        assertNull(result,"deberia ser nulo")


        verify(dao,times(1)).getById(persona3.id)

        verify(dao, times(0)).deleteById(any())
    }
    @Test
    @DisplayName("delete no estando y fallando")
    fun notDeletePersona() {
        whenever(dao.getById(persona3.id)) doReturn persona3
        //whenever(mapper.toDatabaseModel(persona3)) doReturn persona2
        whenever(dao.update(persona3.copy(isDeleted=true),persona3.id)) doReturn 0


        val result=repository.delete(persona2.id)

        assertNull(result,"deberia ser nulo")


        verify(dao,times(1)).update(persona3.copy(isDeleted=true),persona3.id)

        verify(dao, times(1)).getById(persona3.id)
    }

    @Test
    @DisplayName("save estando Entrenador")
    fun save() {

        whenever(dao.save(persona3)) doReturn 1

        val result=repository.save(persona2)
        assertEquals(result.id,1,"deberia devolver la persona2")
        assertNotNull(result)
        assertEquals(result.nombre,persona2.nombre,"deberia devolver la persona2")


        verify(dao, times(1)).save(persona3)
    }

@Test
@DisplayName("save estando Jugador")
fun saveJugador() {

    whenever(dao.save(persona4)) doReturn 1

    val result=repository.save(persona)
    assertEquals(result.id,1,"deberia devolver la persona1")
    assertNotNull(result)
    assertEquals(result.nombre,persona.nombre,"deberia devolver la persona1")


    verify(dao, times(1)).save(persona4)
}
}