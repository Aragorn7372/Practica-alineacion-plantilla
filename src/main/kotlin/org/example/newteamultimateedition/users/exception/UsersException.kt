package org.example.newteamultimateedition.users.exception



abstract class UsersException (val message: String) {

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
}