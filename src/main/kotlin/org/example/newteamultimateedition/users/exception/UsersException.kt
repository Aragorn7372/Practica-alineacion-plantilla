package org.example.newteamultimateedition.users.exception



abstract class UsersException (val messager: String) {

    /**
     * Excepción que indica que no se ha encontrado el usuario buscado.
     *
     * @param message Mensaje de error.
     */
    class UsersNotFoundException(id: String): UsersException("Persona no encontrada con id: $id")
    /**
     * Excepción que indica que el nombre o la contraseña están en blanco.
     *
     * @param userName Mensaje de error.
     */
    class IsBlankException(userName: String): UsersException("El usuario no puede estar en blanco")
    /**
     * Excepción que indica un fallo en la base de datos.
     *
     * @param messager Mensaje de error.
     */
    class DatabaseException(messager: String): UsersException(messager)
    /**
     * Excepción que indica que has escrito mal la contraseña.
     *
     * @param messager Mensaje de error.
     */
    class ContraseniaEquivocadaException(messager: String): UsersException(messager)
}