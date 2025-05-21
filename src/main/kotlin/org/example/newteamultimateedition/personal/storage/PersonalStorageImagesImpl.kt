package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.practicaenequipocristianvictoraitornico.common.config.Config
import org.example.newteamultimateedition.personal.exception.PersonasException
import org.lighthousegames.logging.logging

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.Instant

class PersonalStorageImagesImpl(
    private val config: Config
        ): PersonalStorageImages {
    private val logger= logging()
    override fun saveImage(fileName: File): Result<File, PersonasException> {
        logger.debug { "Saving $fileName." }
        val name= File(config.configProperties.imgDir + "/" + getName(fileName))
        return try {
            Files.copy(fileName.toPath(), name.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(fileName)
        }catch (e: Exception){
            return Err(PersonasException.PersonasStorageException(e.message.toString()))
        }
    }
    private fun getName(fileName: File):String {
        val name=fileName.name
        val extension=name.substring(name.lastIndexOf('.') + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

    override fun loadImage(fileName: String): Result<File, PersonasException> {
        logger.debug { "Loading $fileName." }
        val file= File(config.configProperties.imgDir + "/"+ fileName)
        if(file.exists()){
            return Ok(file)
        }else{
            return Err(PersonasException.PersonasStorageException(file.name))
        }

    }

    override fun deleteImage(fileName: String): Result<Unit, PersonasException> {
        logger.debug { "Deleting $fileName." }
        val file= File(fileName)
        Files.deleteIfExists(file.toPath())
        return Ok(Unit)
    }

    override fun deleteAllImage(): Result<Int, PersonasException> {
        logger.debug { "Deleting all files." }
        try {
            return Ok(Files.walk(Paths.get(config.configProperties.imgDir))
                .filter{Files.isRegularFile(it)}.map{Files.deleteIfExists(it)}
                .count().toInt())
        }
        catch (e: Exception){
            return  Err(PersonasException.PersonasStorageException(e.message.toString()))
        }

    }

    override fun updateImage(Imagen: String, newFileImage: File): Result<File, PersonasException> {
        logger.debug { "Updating $Imagen." }
        val updated= File(config.configProperties.imgDir + "/" + getName(newFileImage))
        try {
            Files.copy(updated.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            return Ok(newFileImage)
        }catch (e: Exception){
            return Err(PersonasException.PersonasStorageException(e.message.toString()))
        }
    }
}