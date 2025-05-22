package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.onSuccess
import org.example.newteamultimateedition.personal.extensions.copy
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class EquipoStorageImplTest {
    // Cuidado con el renombre de funciones. MIRAR QUE MOCKITO ESTOY IMPORTANDO
    @Mock
    private lateinit var storageCSV: EquipoStorageCSV
    @Mock
    private lateinit var storageJSON: EquipoStorageJSON
    @Mock
    private lateinit var storageXML: EquipoStorageXML
    @Mock
    private lateinit var storageBIN: EquipoStorageBIN
    @InjectMocks
    private lateinit var storage: EquipoStorageImpl

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


    @Test
    @DisplayName("StorageCSV llamado para LECTURA")
    fun callCsvImport(@TempDir tempDir: File) {
        val file = File(tempDir, "data.csv")
        whenever(storageCSV.fileRead(file)).thenReturn(Ok(list))
        whenever(storageXML.fileRead(file)).thenReturn(Ok(list))
        whenever(storageJSON.fileRead(file)).thenReturn(Ok(list))
        whenever(storageBIN.fileRead(file)).thenReturn(Ok(list))

        val result = storage.fileRead(file)

        verify(storageBIN, times(0)).fileRead(file)
        verify(storageXML, times(0)).fileRead(file)
        verify(storageJSON, times(0)).fileRead(file)
        verify(storageCSV, times(1)).fileRead(file)
    }
}