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


class EquipoStorageBINTest {

    val storage = EquipoStorageBIN()
    val e = Entrenador(
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

    val j = Jugador(
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
        minutosJugados = 13245
    )

    val list = listOf(e, j)

    @Nested
    @DisplayName("Tests correctos")
    inner class Successful {
        @Test
        @DisplayName("Verificar importar y exportar BIN")
        fun importAndExportOk(@TempDir tempDir: File) {
            val file = File(tempDir, "data.bin")

            storage.fileWrite(list, file)

            val equipo = storage.fileRead(file).value

            val entrenadorActual = (equipo[0] as Entrenador)
            val jugadorActual = (equipo[1] as Jugador)

            assertAll(
                //Entrenador
                { assertEquals(e.id, entrenadorActual.id) },
                { assertEquals(e.nombre, entrenadorActual.nombre) },
                { assertEquals(e.apellidos, entrenadorActual.apellidos) },
                { assertEquals(e.fechaNacimiento, entrenadorActual.fechaNacimiento) },
                { assertEquals(e.fechaIncorporacion, entrenadorActual.fechaIncorporacion) },
                { assertEquals(e.salario, entrenadorActual.salario) },
                { assertEquals(e.pais, entrenadorActual.pais) },
                { assertEquals(e.imagen, entrenadorActual.imagen) },
                { assertEquals(e.especialidad, entrenadorActual.especialidad) },
                // Jugador
                { assertEquals(j.id, jugadorActual.id) },
                { assertEquals(j.nombre, jugadorActual.nombre) },
                { assertEquals(j.apellidos, jugadorActual.apellidos) },
                { assertEquals(j.fechaNacimiento, jugadorActual.fechaNacimiento) },
                { assertEquals(j.fechaIncorporacion, jugadorActual.fechaIncorporacion) },
                { assertEquals(j.pais, jugadorActual.pais) },
                { assertEquals(j.imagen, jugadorActual.imagen) },
                { assertEquals(j.posicion, jugadorActual.posicion) },
                { assertEquals(j.altura, jugadorActual.altura) },
                { assertEquals(j.dorsal, jugadorActual.dorsal) },
                { assertEquals(j.peso, jugadorActual.peso) },
                { assertEquals(j.goles, jugadorActual.goles) },
                { assertEquals(j.partidosJugados, jugadorActual.partidosJugados) },
                { assertEquals(j.minutosJugados, jugadorActual.minutosJugados) }
            )
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
            val file = File("data.bin", "data.bin")

            val error = storage.fileWrite(list, file)
            assertTrue(error.isErr)
        }
    }
}