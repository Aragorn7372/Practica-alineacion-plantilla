package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.common.config.Config
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.routes.RoutesManager.app


import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.name

/**
 * Clase que se encarga de guardar en un zip con todas las extensiones en forma de backup
 * @property csv Almacenamiento CSV
 * @property json Almacenamiento JSON
 * @property bin Almacenamiento BIN
 * @property xml Almacenamiento XML
 */
class PersonalStorageZip(

    private val csv: EquipoStorageCSV,
    private val json: EquipoStorageJSON,
    private val bin: EquipoStorageBIN,
    private val xml: EquipoStorageXML,
) {
    private val config = Config

    private val tempDirName = "players"
    private val logger = logging()

    /**
     * Función que lee de un archivo zip
     * @param file El archivo
     * @return [Result] de [Persona] en caso correcto o [PersonasError] en caso incorrecto o de fallo
     */
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
                else -> xml.fileRead(personas.first())
            }

        } catch (ex: Exception) {
            return Err(PersonasError.PersonasStorageError(ex.message.toString()))
        }
    }

    /**
     * Función que escribe en un archivo zip
     * @param file El archivo
     * @param persona Lista de personas a exportar
     * @return [Result] de [Unit] en caso correcto o [PersonasError] en caso incorrecto o de fallo
     */
    fun escribirAUnArchivo(file: File, persona: List<Persona>): Result<Unit, PersonasError> {
        logger.debug { "exportando a zip con datos en formato: " }
        val tempDir = Files.createTempDirectory(tempDirName)
        try {
            persona.forEach {
                val imageFile = File(config.configProperties.imagesDirectory + "/" + it.imagen)
                if (imageFile.isFile) {
                    Files.copy(
                        imageFile.toPath(),
                        tempDir.resolve(imageFile.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                } else {
                    val resourceUrl = app::class.java.getResource(it.imagen)
                    if (resourceUrl != null) {
                        val inputStream = resourceUrl.openStream()
                        val targetPath = tempDir.resolve(it.imagen.substringAfterLast("/"))
                        Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING)
                    } else {
                        logger.warn { "Imagen no encontrada: ${resourceUrl}" }
                    }
                }
            }

            val datafile = csv.fileWrite(persona, File("$tempDir/data.csv"))

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
                return Ok(Unit)
            } else return Err(PersonasError.PersonasStorageError(datafile.error.toString()))
        } catch (e: Exception) {
            logger.debug { "fallo al importar" }
            return Err(PersonasError.PersonasStorageError(e.message.toString()))
        }
    }
}



