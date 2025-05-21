package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.error.PersonasError
import java.io.File

interface PersonalStorageImages {
    fun saveImage(fileName: File): Result<File, PersonasError>
    fun loadImage(fileName: String): Result<File, PersonasError>
    fun deleteImage(fileName: String): Result<Unit, PersonasError>
    fun deleteAllImage(): Result<Int, PersonasError>
    fun updateImage(Imagen: String, newFileImage: File): Result<File, PersonasError>
}