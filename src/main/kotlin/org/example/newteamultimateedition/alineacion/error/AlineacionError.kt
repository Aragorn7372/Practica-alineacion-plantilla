package org.example.newteamultimateedition.alineacion.error

import org.example.newteamultimateedition.common.error.Errors

/**
 * Clase que representa los errores relativos a las alineaciones

 */
abstract class AlineacionError(mensaje: String): Errors(mensaje) {


    /**
     * Error que indica que no encuentra a la persona.
     *
     * @param id Identificador personal de la persona que no se ha podido encontrar.
     */
    class AlineacionNotFoundError(id: String): AlineacionError("Alineacion no encontrada con id: $id")

    /**
     * Error que indica que los datos de la persona no son válidos
     *
     * @param message Mensaje de error
     */
    class AlineacionInvalidoError(message: String): AlineacionError("Alineacion no válida: $message")


    /**
     *  Error que indica que la base de datos de la persona no se ha conectado
     *  @param message Mensaje de error
     */
    class AlineacionDatabaseError(message: String): AlineacionError(message)

    /**
     * Error que indica un fallo en el almacenamiento
     * @param message Mensaje de error
     */
    class AlineacionStorageError(message: String): AlineacionError(message)
}