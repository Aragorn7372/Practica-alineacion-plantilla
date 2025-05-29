package org.example.newteamultimateedition.personal.error

import org.example.newteamultimateedition.common.error.Errors

abstract class PersonasError (messager: String):Errors(message=messager) {
    /**
    * Error que indica un problema con el almacenamiento de personas.
    *
    * @param message Mensaje de error.
    */
    class PersonasStorageError(message: String): PersonasError(message)

    /**
     * Excepción que indica que no encuentra a la persona.
     *
     * @param id Identificador personal de la persona que no se ha podido encontrar.
     */
    class PersonaNotFoundError(id: Long): PersonasError("Persona no encontrada con id: $id")

    /**
     * Excepción que indica que los datos de la persona no son válidos
     *
     * @param message Mensaje de error
     */
    class PersonasInvalidoError(message: String): PersonasError("Persona no válida: $message")


    /**
     *  Excepción que indica que la base de datos de la persona no se ha conectado
     *  @param message
     */
    class PersonaDatabaseError(message: String): PersonasError(message)
}