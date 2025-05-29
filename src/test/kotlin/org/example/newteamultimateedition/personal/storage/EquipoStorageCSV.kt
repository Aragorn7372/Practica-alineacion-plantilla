package org.example.newteamultimateedition.personal.storage

import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime


class EquipoStorageCSVTest {

    val storage = EquipoStorageCSV()
    private val e = Entrenador(
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
        especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
        isDeleted = false
    )

    private val j = Jugador(
        id = 2L,
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

    val list = listOf(e, j)

    @Nested
    @DisplayName("Tests correctos")
    inner class Successful {
        @Test
        @DisplayName("Verificar importar CSV")
        fun checkImportarEntrenador(@TempDir tempDir: File){
            val file = File(tempDir, "data.csv")
            file.writeText("id,nombre,apellidos,fecha_nacimiento,fecha_incorporacion,salario,pais,rol,especialidad,posicion,dorsal,altura,peso,goles,partidos_jugados,minutos_jugados,imagen\n" +
                    "1,Roberto,Hongo,1960-07-17,2000-01-01,60000.0,Brasil,Entrenador,ENTRENADOR_PRINCIPAL,,,,,,,,media/entrenador1.png")
            val lista = storage.fileRead(file).value

            assertEquals(1, lista.size)
            val entrenadorExpected = Entrenador(
                id = 1L,
                nombre = "Roberto",
                apellidos = "Hongo",
                fechaNacimiento = LocalDate.of(1960, 7, 17),
                fechaIncorporacion = LocalDate.of(2000, 1, 1),
                salario = 60000.0,
                pais = "Brasil",
                imagen = "media/entrenador1.png",
                especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
                isDeleted = false
            )
            val entrenadorActual = lista.first() as Entrenador

            assertAll(
                { assertEquals(entrenadorExpected.id, entrenadorActual.id) },
                { assertEquals(entrenadorExpected.nombre, entrenadorActual.nombre) },
                { assertEquals(entrenadorExpected.apellidos, entrenadorActual.apellidos) },
                { assertEquals(entrenadorExpected.fechaNacimiento, entrenadorActual.fechaNacimiento) },
                { assertEquals(entrenadorExpected.fechaIncorporacion, entrenadorActual.fechaIncorporacion) },
                { assertEquals(entrenadorExpected.salario, entrenadorActual.salario) },
                { assertEquals(entrenadorExpected.pais, entrenadorActual.pais) },
                { assertEquals(entrenadorExpected.imagen, entrenadorActual.imagen) },
                { assertEquals(entrenadorExpected.especialidad, entrenadorActual.especialidad) }
            )
        }

        @Test
        @DisplayName("Verificar exportar CSV")
        fun checkImportarJugador(@TempDir tempDir: File){
            val file = File(tempDir, "data.csv")
            file.writeText("id,nombre,apellidos,fecha_nacimiento,fecha_incorporacion,salario,pais,rol,especialidad,posicion,dorsal,altura,peso,goles,partidos_jugados,minutos_jugados,imagen\n" +
                    "2,Oliver,Atom,1983-04-10,2001-05-15,35000.0,España,Jugador,,DELANTERO,10,1.75,65.0,70,150,13245,media/jugador1.png\n")

            val lista = storage.fileRead(file).value

            assertEquals(1, lista.size)

            val jugadorExpected = Jugador(
                id = 2L,
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
            val jugadorActual = lista.first() as Jugador

            assertAll(
                { assertEquals(jugadorExpected.id, jugadorActual.id) },
                { assertEquals(jugadorExpected.nombre, jugadorActual.nombre) },
                { assertEquals(jugadorExpected.apellidos, jugadorActual.apellidos) },
                { assertEquals(jugadorExpected.fechaNacimiento, jugadorActual.fechaNacimiento) },
                { assertEquals(jugadorExpected.fechaIncorporacion, jugadorActual.fechaIncorporacion) },
                { assertEquals(jugadorExpected.pais, jugadorActual.pais) },
                { assertEquals(jugadorExpected.imagen, jugadorActual.imagen) },
                { assertEquals(jugadorExpected.posicion, jugadorActual.posicion) },
                { assertEquals(jugadorExpected.altura, jugadorActual.altura) },
                { assertEquals(jugadorExpected.dorsal, jugadorActual.dorsal) },
                { assertEquals(jugadorExpected.peso, jugadorActual.peso) },
                { assertEquals(jugadorExpected.goles, jugadorActual.goles) },
                { assertEquals(jugadorExpected.partidosJugados, jugadorActual.partidosJugados) },
                { assertEquals(jugadorExpected.minutosJugados, jugadorActual.minutosJugados) }
            )
        }

        @Test
        @DisplayName("Verificar Exportar CSV")
        fun checkExport(@TempDir tempDir: File){
            val file = File(tempDir, "data.csv")
            storage.fileWrite(list, file)

            val expectedString = "id,nombre,apellidos,fecha_nacimiento,fecha_incorporacion,salario,pais,rol,especialidad,posicion,dorsal,altura,peso,goles,partidos_jugados,minutos_jugados,imagen\n" + "1,Pepito,Grillo,1942-01-01,2025-02-02,1234.0,Italia,Entrenador,ENTRENADOR_PRINCIPAL,,,,,,,,pepito-grillo.png\n" + "2,Oliver,Atom,1983-04-10,2001-05-15,35000.0,España,Jugador,,DELANTERO,10,1.75,65.0,70,150,13245,media/jugador1.png"

            val actualString = file.readText().trim()
            assertEquals(expectedString, actualString)
        }
    }

    @Nested
    @DisplayName("Tests que esperan fallos")
    inner class Failed{

        @Test
        @DisplayName("Import | No es un archivo")
        fun notFileOnImport(){
            val file = File("media/")
            val error = storage.fileRead(file)

            assertTrue(error.isErr)
        }

        @Test
        @DisplayName("Export | No es un archivo")
        fun notFileOnExport(@TempDir tempDir: File){
            val file = File("data.csv", "data.csv")

            val error = storage.fileWrite(list, file)
            assertTrue(error.isErr)
        }

    }
}