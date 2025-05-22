package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.personal.models.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test

class EquipoStorageImplTest {
    private lateinit var storageCSV: EquipoStorageCSV
    private lateinit var storageXML: EquipoStorageXML
    private lateinit var storageJSON: EquipoStorageJSON
    private lateinit var storageBIN: EquipoStorageBIN
    private lateinit var equipoStorageImpl: EquipoStorageImpl

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

    @BeforeEach
    fun setUp() {
        storageCSV = mock()
        storageXML = mock()
        storageJSON = mock()
        storageBIN = mock()
        equipoStorageImpl = EquipoStorageImpl(storageCSV, storageXML, storageJSON, storageBIN)
    }

    @Test
    @DisplayName("Leer de CSV exitoso")
    fun readFromCSV() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileRead(csvFile)

        verify(storageCSV, times(1)).fileRead(csvFile)
        verify(storageJSON, times(0)).fileRead(jsonFile)
        verify(storageXML, times(0)).fileRead(xmlFile)
        verify(storageBIN, times(0)).fileRead(binFile)
    }

    @Test
    @DisplayName("Escribir en CSV exitoso")
    fun writeOnCSV() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileWrite(list, csvFile)

        verify(storageCSV, times(1)).fileWrite(list, csvFile)
        verify(storageJSON, times(0)).fileWrite(list, jsonFile)
        verify(storageXML, times(0)).fileWrite(list, xmlFile)
        verify(storageBIN, times(0)).fileWrite(list, binFile)
    }

    @Test
    @DisplayName("Leer de JSON exitoso")
    fun readFromJSON() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileRead(jsonFile)

        verify(storageCSV, times(0)).fileRead(csvFile)
        verify(storageJSON, times(1)).fileRead(jsonFile)
        verify(storageXML, times(0)).fileRead(xmlFile)
        verify(storageBIN, times(0)).fileRead(binFile)
    }

    @Test
    @DisplayName("Escribir en JSON exitoso")
    fun writeOnJSON() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileWrite(list, jsonFile)

        verify(storageCSV, times(0)).fileWrite(list, csvFile)
        verify(storageJSON, times(1)).fileWrite(list, jsonFile)
        verify(storageXML, times(0)).fileWrite(list, xmlFile)
        verify(storageBIN, times(0)).fileWrite(list, binFile)
    }

    @Test
    @DisplayName("Leer de XML exitoso")
    fun readFromXML() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileRead(xmlFile)

        verify(storageCSV, times(0)).fileRead(csvFile)
        verify(storageJSON, times(0)).fileRead(jsonFile)
        verify(storageXML, times(1)).fileRead(xmlFile)
        verify(storageBIN, times(0)).fileRead(binFile)
    }

    @Test
    @DisplayName("Escribir en XML exitoso")
    fun writeOnXML() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileWrite(list, xmlFile)

        verify(storageCSV, times(0)).fileWrite(list, csvFile)
        verify(storageJSON, times(0)).fileWrite(list, jsonFile)
        verify(storageXML, times(1)).fileWrite(list, xmlFile)
        verify(storageBIN, times(0)).fileWrite(list, binFile)
    }

    @Test
    @DisplayName("Leer de BIN exitoso")
    fun readFromBIN() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileRead(binFile)

        verify(storageCSV, times(0)).fileRead(csvFile)
        verify(storageJSON, times(0)).fileRead(jsonFile)
        verify(storageXML, times(0)).fileRead(xmlFile)
        verify(storageBIN, times(1)).fileRead(binFile)
    }

    @Test
    @DisplayName("Escribir en BIN exitoso")
    fun writeOnBIN() {
        val csvFile = File( "resources", "test.csv")
        val jsonFile = File ("resources", "test.json")
        val xmlFile = File( "resources", "test.xml")
        val binFile = File( "resources", "test.bin")

        whenever(storageCSV.fileRead(csvFile)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(jsonFile)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(xmlFile)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(binFile)).thenReturn(Ok(list))

        equipoStorageImpl.fileWrite(list, binFile)

        verify(storageCSV, times(0)).fileWrite(list, csvFile)
        verify(storageJSON, times(0)).fileWrite(list, jsonFile)
        verify(storageXML, times(0)).fileWrite(list, xmlFile)
        verify(storageBIN, times(1)).fileWrite(list, binFile)
    }
}
