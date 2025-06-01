package org.example.newteamultimateedition.users.exception

import org.example.newteamultimateedition.common.error.Errors


/**
 * Representa la clase inicial de los posibles errores de usuarios
 * @property message Mensaje de error
 */
abstract class UsersException (message: String): Errors(message) {

    /**
     * Excepción que indica que no se ha encontrado el usuario buscado.
     *
     * @param message Mensaje de error.
     */
    class UsersNotFoundException(id: String): UsersException("Persona no encontrada con id: $id")

    /**
     * Excepción que indica un fallo en la base de datos.
     *
     * @param messager Mensaje de error.
     */
    class DatabaseException(messager: String): UsersException(messager)
    class ContraseniaEquivocadaException(messager: String): UsersException(messager)
    class AccessDeniedException(messager: String) : UsersException(messager)
}