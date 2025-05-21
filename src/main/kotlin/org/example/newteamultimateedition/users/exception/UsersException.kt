package org.example.newteamultimateedition.users.exception



abstract class UsersException (val messager: String) {
    /**
     * Excepción que indica que no se ha encontrado el usuario buscado.
     *
     * @param message Mensaje de error.
     */
    class UsersNotFoundException(id: String): UsersException("Persona no encontrada con id: $id")

    class IsBlankException(userName: String): UsersException("El usuario no puede estar en blanco")

    class DatabaseException(messager: String): UsersException(messager)
    class ContraseniaEquivocadaException(messager: String): UsersException(messager)
}