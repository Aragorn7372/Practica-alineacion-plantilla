package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.personal.models.Persona
import java.io.File
import java.io.FileOutputStream

/**
 * Clase que representa el storage de pdf de [Alineacion]
 */
class AlineacionStoragePDF(
    private val storageHTML: AlineacionStorageHTML
):AlineacionStorage<Persona, AlineacionError, Alineacion> {
    override fun fileWrite(item: Alineacion, equipo: List<Persona>, file: File): Result<Unit, AlineacionError> {
        if (!file.isFile ||!file.parentFile.exists()) {
            return Err(AlineacionError.AlineacionStorageError("El directorio padre del fichero no existe"))
        }
        val html= storageHTML.createHtml(item,equipo)
        FileOutputStream(file).use { output ->
            PdfRendererBuilder()
                .withHtmlContent(html,null)
                .toStream(output)
                .run()
        }
        return Ok(Unit)
    }
}