package org.example.newteamultimateedition.personal.storage

import org.example.newteamultimateedition.personal.extensions.copy
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


class EquipoStorageJSONTest {

    val storage = EquipoStorageJSON()
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
        minutosJugados = 13245
    )

    val list = listOf(e, j)

    @Nested
    @DisplayName("Tests correctos")
    inner class Successful {
        @Test
        @DisplayName("Verificar importar JSON")
        fun checkImportarEntrenador(@TempDir tempDir: File){
            val file = File(tempDir, "data.json")
            file.writeText(
                "[\n" +
                    "    {\n" +
                    "        \"id\": 1,\n" +
                    "        \"nombre\": \"Pepito\",\n" +
                    "        \"apellidos\": \"Grillo\",\n" +
                    "        \"fecha_nacimiento\": \"1942-01-01\",\n" +
                    "        \"fecha_incorporacion\": \"2025-02-02\",\n" +
                    "        \"salario\": 1234.0,\n" +
                    "        \"pais\": \"Italia\",\n" +
                    "        \"rol\": \"Entrenador\",\n" +
                    "        \"especialidad\": \"ENTRENADOR_PRINCIPAL\",\n" +
                    "        \"posicion\": \"\",\n" +
                    "        \"dorsal\": null,\n" +
                    "        \"altura\": null,\n" +
                    "        \"peso\": null,\n" +
                    "        \"goles\": null,\n" +
                    "        \"partidos_jugados\": null,\n" +
                    "        \"minutos_jugados\": null,\n" +
                    "        \"imagen\": \"pepito-grillo.png\"\n" +
                    "    }\n" +
            "]")
            val lista = storage.fileRead(file).value

            assertEquals(1, lista.size)
            val entrenadorExpected = e.copy()
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
        @DisplayName("Verificar exportar JSON")
        fun checkImportarJugador(@TempDir tempDir: File){
            val file = File(tempDir, "data.json")
            file.writeText("[\n"+
                    "    {\n" +
                    "        \"id\": 2,\n" +
                    "        \"nombre\": \"Oliver\",\n" +
                    "        \"apellidos\": \"Atom\",\n" +
                    "        \"fecha_nacimiento\": \"1983-04-10\",\n" +
                    "        \"fecha_incorporacion\": \"2001-05-15\",\n" +
                    "        \"salario\": 35000.0,\n" +
                    "        \"pais\": \"España\",\n" +
                    "        \"rol\": \"Jugador\",\n" +
                    "        \"especialidad\": \"\",\n" +
                    "        \"posicion\": \"DELANTERO\",\n" +
                    "        \"dorsal\": 10,\n" +
                    "        \"altura\": 1.75,\n" +
                    "        \"peso\": 65.0,\n" +
                    "        \"goles\": 70,\n" +
                    "        \"partidos_jugados\": 150,\n" +
                    "        \"minutos_jugados\": 13245,\n" +
                    "        \"imagen\": \"media/jugador1.png\"\n" +
                    "    }" +
            "]")

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
                minutosJugados = 13245
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
        @DisplayName("Verificar Exportar JSON")
        fun checkExport(@TempDir tempDir: File){
            val file = File(tempDir, "data.json")
            storage.fileWrite(list, file)

            val expectedString = "[\n" +
                    "    {\n" +
                    "        \"id\": 1,\n" +
                    "        \"nombre\": \"Pepito\",\n" +
                    "        \"apellidos\": \"Grillo\",\n" +
                    "        \"fecha_nacimiento\": \"1942-01-01\",\n" +
                    "        \"fecha_incorporacion\": \"2025-02-02\",\n" +
                    "        \"salario\": 1234.0,\n" +
                    "        \"pais\": \"Italia\",\n" +
                    "        \"rol\": \"Entrenador\",\n" +
                    "        \"especialidad\": \"ENTRENADOR_PRINCIPAL\",\n" +
                    "        \"posicion\": \"\",\n" +
                    "        \"dorsal\": null,\n" +
                    "        \"altura\": null,\n" +
                    "        \"peso\": null,\n" +
                    "        \"goles\": null,\n" +
                    "        \"partidos_jugados\": null,\n" +
                    "        \"minutos_jugados\": null,\n" +
                    "        \"imagen\": \"pepito-grillo.png\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": 2,\n" +
                    "        \"nombre\": \"Oliver\",\n" +
                    "        \"apellidos\": \"Atom\",\n" +
                    "        \"fecha_nacimiento\": \"1983-04-10\",\n" +
                    "        \"fecha_incorporacion\": \"2001-05-15\",\n" +
                    "        \"salario\": 35000.0,\n" +
                    "        \"pais\": \"España\",\n" +
                    "        \"rol\": \"Jugador\",\n" +
                    "        \"especialidad\": \"\",\n" +
                    "        \"posicion\": \"DELANTERO\",\n" +
                    "        \"dorsal\": 10,\n" +
                    "        \"altura\": 1.75,\n" +
                    "        \"peso\": 65.0,\n" +
                    "        \"goles\": 70,\n" +
                    "        \"partidos_jugados\": 150,\n" +
                    "        \"minutos_jugados\": 13245,\n" +
                    "        \"imagen\": \"media/jugador1.png\"\n" +
                    "    }\n" +
                    "]"
            val actualString = file.readText()
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
            val file = File("data.json", "data.json")

            val error = storage.fileWrite(list, file)
            assertTrue(error.isErr)
        }

    }
}