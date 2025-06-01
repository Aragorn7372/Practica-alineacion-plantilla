package org.example.newteamultimateedition.alineacion.storage

import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class AlineacionStorageHTMLTest{

    private val storage = AlineacionStorageHTML()

    private val jugador1 = Jugador(
        id = 101L,
        nombre = "Oliver",
        apellidos = "Atom",
        fechaNacimiento = LocalDate.of(1983, 4, 10),
        fechaIncorporacion = LocalDate.of(2001, 5, 15) ,
        salario = 35000.0,
        pais = "España",
        imagen = "media/jugador1.png",
        posicion = Posicion.DELANTERO,
        dorsal = 10,
        altura = 1.75,
        peso = 65.0,
        goles = 70,
        partidosJugados = 150,
        minutosJugados = 13245,
        isDeleted = false
    )

    private val jugador2 = Jugador(
        id = 102L,
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

    private val entrenador = Entrenador(
        id = 103L,
        nombre = "Pepito",
        apellidos = "Grillo",
        fechaNacimiento = LocalDate.of(1942, 1, 1),
        fechaIncorporacion = LocalDate.of(2025, 2, 2),
        salario = 1234.0,
        pais = "Italia",
        createdAt = LocalDateTime.of(2025, 5, 31, 14, 30),
        updatedAt = LocalDateTime.of(2025, 5, 31, 14, 30),
        imagen = "pepito-grillo.png",
        especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
        isDeleted = false
    )

    private val lineaAlineacion1 = LineaAlineacion(
        id = UUID.randomUUID(),
        idAlineacion = 1L,
        idPersona = 101L,
        posicion = 1
    )

    private val lineaAlineacion2 = LineaAlineacion(
        id = UUID.randomUUID(),
        idAlineacion = 1L,
        idPersona = 102L,
        posicion = 2
    )

    private val alineacion = Alineacion(
        id = 1L,
        personalList = listOf(lineaAlineacion1, lineaAlineacion2),
        createdAt = LocalDateTime.of(2025, 5, 31, 14, 30),
        updatedAt = LocalDateTime.of(2025, 5, 31, 14, 30),
        juegoDate = LocalDate.of(2025, 5, 31),
        entrenador = entrenador,
        descripcion = "El dream team"
    )

    @Nested
    @DisplayName("Tests correctos")
    inner class TestsCorrectos{

        @Test
        @DisplayName("Escribir ok")
        fun fileWriteOk(@TempDir tempDir: File) {

            val tempFile = File(tempDir, "alineaciones.html")
            tempFile.writeText("")

            val result = storage.fileWrite(alineacion, listOf(jugador1, jugador2), tempFile)

            val expectedString = """
                <!DOCTYPE html>
                <html><head><meta charset="UTF-8"/><title>Equipo</title></head><body>
                <h1>Alineacion del equipo</h1>
                <h2>Información</h2>
                <p>Id: 1</p>
                <p>Entrenador: Entrenador(id= 103, nombre= Pepito, apellidos= Grillo, fecha_nacimiento= 1942-01-01, fecha_incorporacion= 2025-02-02, salario= 1234.0, pais = Italia, createdAt= 2025-05-31T14:30, updatedAt= 2025-05-31T14:30, especialidad = ENTRENADOR_PRINCIPAL, imagen = pepito-grillo.png)</p>
                <p>Fecha de creación: 2025-05-31T14:30</p>
                <p>Última actualización: 2025-05-31T14:30</p>
                <p>Fecha de convocatoria: 2025-05-31 </p>
                <p>Descripción: El dream team</p>
                <h2>Jugadores</h2>
                <ul>
                <li>
                Nombre completo: Oliver Atom - Posición: DELANTERO - Dorsal: 10
                </li>
                <li>
                Nombre completo: Jugadora hola - Posición: DEFENSA - Dorsal: 12
                </li>
                </ul>
                </body></html>
                """.trimIndent()

            val actualString = tempFile.readText()

            assertTrue(result.isOk)
            //Con la regex y el replace eliminamos los espacios y saltos de línea
            assertEquals(expectedString.replace("\\s+".toRegex(), ""), actualString.replace("\\s+".toRegex(), ""), "El contenido del html generado debería ser ese")

        }
    }
    @Test
    @DisplayName("Crear string html")
    fun createHtml(){
        val expectedString = """<!DOCTYPE html>
<html><head><meta charset="UTF-8"/><title>Equipo</title></head><body>
<h1>Alineacion del equipo</h1>
<h2>Información</h2>
<p>Id: 1</p>
<p>Entrenador: Entrenador(id= 103, nombre= Pepito, apellidos= Grillo, fecha_nacimiento= 1942-01-01, fecha_incorporacion= 2025-02-02, salario= 1234.0, pais = Italia, createdAt= 2025-05-31T14:30, updatedAt= 2025-05-31T14:30, especialidad = ENTRENADOR_PRINCIPAL, imagen = pepito-grillo.png)</p>
<p>Fecha de creación: 2025-05-31T14:30</p>
<p>Última actualización: 2025-05-31T14:30</p>
<p>Fecha de convocatoria: 2025-05-31 </p>
<p>Descripción: El dream team</p>
<h2>Jugadores</h2>
<ul>
<li>
Nombre completo: Oliver Atom - Posición: DELANTERO - Dorsal: 10
</li>
<li>
Nombre completo: Jugadora hola - Posición: DEFENSA - Dorsal: 12
</li>
</ul>
</body></html>""".trim()
        val result = storage.createHtml(alineacion,listOf(jugador1, jugador2))
        assertEquals(result.trim(),expectedString,"deberian ser iguales")
    }

    @Nested
    @DisplayName("Tests incorrectos")
    inner class TestsIncorrectos{
        @Test
        @DisplayName("Export | No es un archivo")
        fun notFileOnExport(@TempDir tempDir: File){
            val file = File("hola", "data.csv")

            val error = storage.fileWrite(alineacion, listOf(jugador1, jugador2), file)
            assertTrue(error.isErr)
        }
    }
    @Test
    fun fileWriteNotOk() {
        val file = File( "hola", "data.csv")
        val result=storage.fileWrite(alineacion, listOf(jugador1, jugador2), file)

        assertTrue(result.isErr, "deberia ser un error")
        assertEquals(AlineacionError.AlineacionStorageError("El directorio padre del fichero no existe").message, result.error.message,"deberian ser iguales")
    }
}