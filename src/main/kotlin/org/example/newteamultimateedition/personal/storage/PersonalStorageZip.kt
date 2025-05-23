package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.common.config.Config
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona


import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.name

class PersonalStorageZip(

    private val csv: EquipoStorageCSV,
    private val json: EquipoStorageJSON,
    private val bin: EquipoStorageBIN,
    private val xml: EquipoStorageXML,
) {
    private val config = Config

    private val tempDirName = "players"
    private val logger = logging()
    fun leerDelArchivo(file: File): Result<List<Persona>, PersonasError> {
        logger.info { "descomprimiendo archivos" }
        val tempDir = Files.createTempDirectory(tempDirName)
        try {
            ZipFile(file).use { zip ->
                zip.entries().asSequence().forEach { entry ->
                    zip.getInputStream(entry).use { input ->
                        Files.copy(
                            input,
                            Paths.get(tempDir.toString(), entry.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    }
                }
            }

            Files.walk(tempDir).forEach {
                if (!it.toString().endsWith(".csv") && Files.isRegularFile(it) || !it.toString()
                        .endsWith(".json") && Files.isRegularFile(it) ||
                    !it.toString().endsWith(".bin") && Files.isRegularFile(it) || !it.toString()
                        .endsWith(".xml") && Files.isRegularFile(it)
                ) {
                    Files.copy(
                        it,
                        Paths.get(config.configProperties.imagesDirectory + "/", it.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }

            val personas = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) && it.toString().matches(Regex(".*\\.(csv|json|bin|xml)$")) }
                .map {
                    it.toFile()
                }.toList()
            return when (personas.first().extension) {
                "csv" -> csv.fileRead(personas.first())
                "json" -> json.fileRead(personas.first())
                "bin" -> bin.fileRead(personas.first())
                "xml" -> xml.fileRead(personas.first())

                else -> {
                    return Err(PersonasError.PersonasStorageError("tipo invalido"))
                }
            }

        } catch (ex: Exception) {
            return Err(PersonasError.PersonasStorageError("tipo invalido"))
        }
    }


    fun escribirAUnArchivo(file: File, persona: List<Persona>, tipe: Tipo): Result<String, PersonasError> {
        logger.debug { "exportando a zip con datos en formato: $tipe " }
        val tempDir = Files.createTempDirectory(tempDirName)
        try {
            persona.forEach {
                if (it is Jugador) {
                    val newfile = File(config.configProperties.imagesDirectory + "/" + it.imagen)
                    if (newfile.exists()) {
                        Files.copy(
                            newfile.toPath(),
                            Paths.get("$tempDir/", file.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    }
                }
            }

            val datafile = when (tipe) {
                Tipo.CSV -> csv.fileWrite(persona, File("$tempDirName/data.csv"))
                Tipo.JSON -> json.fileWrite(persona,File("$tempDirName/data.json"))
                Tipo.XML -> xml.fileWrite(persona,File("$tempDirName/data.xml"))
                Tipo.BIN -> bin.fileWrite(persona, File("$tempDirName/data.bin"))
            }
            if (datafile.isOk) {
                val archivos = Files.walk(tempDir).filter { Files.isRegularFile(it) }.toList()
                ZipOutputStream(Files.newOutputStream(file.toPath())).use { zip ->
                    archivos.forEach { archivos ->
                        val zipEntry = ZipEntry(tempDir.relativize(archivos).toString())
                        zip.putNextEntry(zipEntry)
                        Files.copy(archivos, zip)
                        zip.closeEntry()
                    }
                }
                tempDir.toFile().deleteRecursively()
                return Ok(file.absolutePath)
            } else return Err(PersonasError.PersonasStorageError(datafile.error.toString()))
        } catch (e: Exception) {
            logger.debug { "fallo al importar" }
            return Err(PersonasError.PersonasStorageError(e.message.toString()))
        }
    }
}
enum class Tipo {
    CSV, JSON, XML, BIN
}


