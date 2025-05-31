package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import java.io.File
import java.io.FileOutputStream

class AlineacionStoragePDF(
    private val storageHTML: AlineacionStorageHTML
):AlineacionStorage<Persona, AlineacionError, Alineacion> {
    override fun fileWrite(item: Alineacion, equipo: List<Persona>, file: File): Result<Unit, AlineacionError> {
        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            return Err(AlineacionError.AlineacionStorageError("El directorio padre del fichero no existe"))
        }
        val html= storageHTML.createHtml(item,equipo)
        return Ok(createHtmlToPdf(html,file))
    }
    fun createHtmlToPdf(html: String, file: File) {
        FileOutputStream(file).use { output ->
            PdfRendererBuilder()
                .withHtmlContent(html,null)
                .toStream(output)
                .run()
        }
    }
}