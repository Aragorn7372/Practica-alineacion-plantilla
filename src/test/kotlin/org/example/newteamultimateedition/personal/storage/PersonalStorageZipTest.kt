package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.common.database.DatabaseManager
import org.example.newteamultimateedition.personal.dao.getPersonasDao
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.io.TempDir
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import org.mockito.ArgumentMatchers.argThat
import org.mockito.kotlin.*
import java.util.zip.ZipOutputStream
import org.mockito.kotlin.doReturn

class PersonalStorageZipTest {

    private lateinit var storageCSV: EquipoStorageCSV
    private lateinit var storageXML: EquipoStorageXML
    private lateinit var storageJSON: EquipoStorageJSON
    private lateinit var storageBIN: EquipoStorageBIN
    private lateinit var storageZip: PersonalStorageZip

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
     imagen = "test.png",
     especialidad = Especialidad.ENTRENADOR_PRINCIPAL,
     isDeleted = false
    )

    private val jugador = Jugador(
     id = 2L,
     nombre = "Oliver",
     apellidos = "Atom",
     fechaNacimiento = LocalDate.of(1983, 4, 10),
     fechaIncorporacion = LocalDate.of(2001, 5, 15),
     salario = 35000.0,
     pais = "España",
     imagen = "asdfpkjnas.png",
     posicion = Posicion.DELANTERO,
     dorsal = 10,
     altura = 1.75,
     peso = 65.0,
     goles = 70,
     partidosJugados = 150,
     minutosJugados = 13245,
     isDeleted = false
    )

    private val listaPersonas = listOf(entrenador)

    @BeforeEach
    fun setUp(){
        storageCSV = mock()
        storageXML = mock()
        storageJSON = mock()
        storageBIN = mock()
        storageZip = PersonalStorageZip(storageCSV, storageJSON, storageBIN, storageXML)

    }

    @Nested
    @DisplayName("Tests correctos")
    inner class TestsCorrectos {

        @Test
        @DisplayName("Exportar correctamente")
        fun exportOk(@TempDir tempDir: File) {
            val outputFile = File(tempDir, "output.zip")
            whenever(storageCSV.fileWrite(eq(listaPersonas),any())).thenReturn(Ok(Unit))
            // When
            val result = storageZip.escribirAUnArchivo(outputFile, listaPersonas)
            if (result.isErr) {
                println("Error devuelto: ${result.error}")
                println(result.error.message)
            }
            // Then
            assertTrue(result.isOk, "La exportación debería ser exitosa")
            assertTrue(outputFile.exists(), "El archivo zip debería existir")

            ZipInputStream(Files.newInputStream(outputFile.toPath())).use { zis ->
                val entries = generateSequence { zis.nextEntry }.map { it.name }.toList()
                assertTrue("test.png" in entries, "El zip debe contener la imagen")

            }

            verify(storageCSV).fileWrite(eq(listaPersonas), any())
        }



        @Test
        @DisplayName("Leer correctamente CSV")
        fun readOkCSV(){
            // Creamos un ZIP temporal con un CSV dentro
            val tempZip = Files.createTempFile("test", ".zip").toFile()
            val tempDir = Files.createTempDirectory("tempData").toFile()
            val tempCsv = File(tempDir, "data.csv")
            tempCsv.writeText("id,nombre,apellidos,fechaNacimiento,...")

            // Zipeamos el archivo CSV
            ZipOutputStream(Files.newOutputStream(tempZip.toPath())).use { zip ->
                val zipEntry = ZipEntry("data.csv")
                zip.putNextEntry(zipEntry)
                Files.copy(tempCsv.toPath(), zip)
                zip.closeEntry()
            }

            // Simulamos que storageCSV devuelve correctamente la lista de personas
            whenever(storageCSV.fileRead(any())).thenReturn(Ok(listaPersonas))

            val result = storageZip.leerDelArchivo(tempZip)

            assertTrue(result.isOk)
            assertEquals(listaPersonas, result.value)
            verify(storageCSV, times(1)).fileRead(any())
            verify(storageBIN, times(0)).fileRead(any())
            verify(storageXML, times(0)).fileRead(any())
            verify(storageJSON, times(0)).fileRead(any())
        }

        @Test
        @DisplayName("Leer correctamente XML")
        fun readOkXML(){
            // Creamos un ZIP temporal con un XML dentro
            val tempZip = Files.createTempFile("test", ".zip").toFile()
            val tempDir = Files.createTempDirectory("tempData").toFile()
            val tempXml = File(tempDir, "data.xml")
            tempXml.writeText("id,nombre,apellidos,fechaNacimiento,...")

            // Zipeamos el archivo XML
            ZipOutputStream(Files.newOutputStream(tempZip.toPath())).use { zip ->
                val zipEntry = ZipEntry("data.xml")
                zip.putNextEntry(zipEntry)
                Files.copy(tempXml.toPath(), zip)
                zip.closeEntry()
            }

            // Simulamos que storageXML devuelve correctamente la lista de personas
            whenever(storageXML.fileRead(any())).thenReturn(Ok(listaPersonas))

            val result = storageZip.leerDelArchivo(tempZip)

            assertTrue(result.isOk)
            assertEquals(listaPersonas, result.value)
            verify(storageCSV, times(0)).fileRead(any())
            verify(storageBIN, times(0)).fileRead(any())
            verify(storageXML, times(1)).fileRead(any())
            verify(storageJSON, times(0)).fileRead(any())
        }

        @Test
        @DisplayName("Leer correctamente JSON")
        fun readOkJSON(){
            // Creamos un ZIP temporal con un JSON dentro
            val tempZip = Files.createTempFile("test", ".zip").toFile()
            val tempDir = Files.createTempDirectory("tempData").toFile()
            val tempJson = File(tempDir, "data.json")
            tempJson.writeText("id,nombre,apellidos,fechaNacimiento,...")

            // Zipeamos el archivo JSON
            ZipOutputStream(Files.newOutputStream(tempZip.toPath())).use { zip ->
                val zipEntry = ZipEntry("data.json")
                zip.putNextEntry(zipEntry)
                Files.copy(tempJson.toPath(), zip)
                zip.closeEntry()
            }

            // Simulamos que storageJSON devuelve correctamente la lista de personas
            whenever(storageJSON.fileRead(any())).thenReturn(Ok(listaPersonas))

            val result = storageZip.leerDelArchivo(tempZip)

            assertTrue(result.isOk)
            assertEquals(listaPersonas, result.value)
            verify(storageCSV, times(0)).fileRead(any())
            verify(storageBIN, times(0)).fileRead(any())
            verify(storageXML, times(0)).fileRead(any())
            verify(storageJSON, times(1)).fileRead(any())
        }

        @Test
        @DisplayName("Leer correctamente BIN")
        fun readOkBIN(){
            // Creamos un ZIP temporal con un BIN dentro
            val tempZip = Files.createTempFile("test", ".zip").toFile()
            val tempDir = Files.createTempDirectory("tempData").toFile()
            val tempBin = File(tempDir, "data.bin")
            tempBin.writeText("id,nombre,apellidos,fechaNacimiento,...")

            // Zipeamos el archivo BIN
            ZipOutputStream(Files.newOutputStream(tempZip.toPath())).use { zip ->
                val zipEntry = ZipEntry("data.bin")
                zip.putNextEntry(zipEntry)
                Files.copy(tempBin.toPath(), zip)
                zip.closeEntry()
            }

            // Simulamos que storageBIN devuelve correctamente la lista de personas
            whenever(storageBIN.fileRead(any())).thenReturn(Ok(listaPersonas))

            val result = storageZip.leerDelArchivo(tempZip)

            assertTrue(result.isOk)
            assertEquals(listaPersonas, result.value)
            verify(storageCSV, times(0)).fileRead(any())
            verify(storageBIN, times(1)).fileRead(any())
            verify(storageXML, times(0)).fileRead(any())
            verify(storageJSON, times(0)).fileRead(any())
        }
    }

    @Nested
    @DisplayName("Tests incorrectos")
    inner class TestsIncorrectos {
        @Test
        @DisplayName("Extensión de archivo no soportada")
        fun readError(){
            // Creamos un ZIP temporal con un TXT dentro
            val tempZip = Files.createTempFile("test", ".zip").toFile()
            val tempDir = Files.createTempDirectory("tempData").toFile()
            val tempCsv = File(tempDir, "test.txt")
            tempCsv.writeText("id,nombre,apellidos,fechaNacimiento,...")

            // Zipeamos el archivo TXT
            ZipOutputStream(Files.newOutputStream(tempZip.toPath())).use { zip ->
                val zipEntry = ZipEntry("test.txt")
                zip.putNextEntry(zipEntry)
                Files.copy(tempCsv.toPath(), zip)
                zip.closeEntry()
            }

            val result = storageZip.leerDelArchivo(tempZip)

            assertTrue(result.isErr)
            assertTrue(result.error is PersonasError.PersonasStorageError)
            verify(storageCSV, times(0)).fileRead(any())
            verify(storageBIN, times(0)).fileRead(any())
            verify(storageXML, times(0)).fileRead(any())
            verify(storageJSON, times(0)).fileRead(any())
        }
        @Test
        @DisplayName("Exportar con error")
        fun exportBad(@TempDir tempDir: File) {
            val outputFile = File(tempDir, "output.zip")
            whenever(storageCSV.fileWrite(eq(listaPersonas),any())).thenReturn(Err(PersonasError.PersonasStorageError("todo lo que podia salir mal ha sucedido")))
            // When
            val result = storageZip.escribirAUnArchivo(outputFile, listaPersonas)
            if (result.isErr) {
                println("Error devuelto: ${result.error}")
                println(result.error.message)
            }
            // Then
            assertTrue(result.isErr, "La exportación debería ser exitosa")
            assertTrue(result.error is PersonasError.PersonasStorageError,"deberia ser un error de personas")


            verify(storageCSV).fileWrite(eq(listaPersonas), any())
        }
        @Test
        @DisplayName("Exportar con excepcion")
        fun exportBadException(@TempDir tempDir: File) {
            val outputFile = File(tempDir, "output.zip")
            whenever(storageCSV.fileWrite(eq(listaPersonas),any())).thenThrow(RuntimeException("todo salio mal"))
            // When
            val result = storageZip.escribirAUnArchivo(outputFile, listaPersonas)

            // Then
            assertTrue(result.isErr, "deberia ser un error")
            assertEquals(result.error.message ,"todo salio mal","deberian ser iguales")

            verify(storageCSV).fileWrite(eq(listaPersonas), any())
        }
    }
}