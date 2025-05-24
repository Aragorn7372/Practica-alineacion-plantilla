package org.example.newteamultimateedition.alineacion.error

import org.example.newteamultimateedition.common.error.Errors


abstract class AlineacionError(mensaje: String): Errors(mensaje) {


    /**
     * Excepción que indica que no encuentra a la persona.
     *
     * @param id Identificador personal de la persona que no se ha podido encontrar.
     */
    class AlineacionNotFoundError(id: String): AlineacionError("Persona no encontrada con id: $id")

    /**
     * Excepción que indica que los datos de la persona no son válidos
     *
     * @param message Mensaje de error
     */
    class AlineacionInvalidoError(message: String): AlineacionError("Persona no válida: $message")


    /**
     *  Excepción que indica que la base de datos de la persona no se ha conectado
     *  @param message
     */
    class AlineacionDatabaseError(message: String): AlineacionError(message)
}