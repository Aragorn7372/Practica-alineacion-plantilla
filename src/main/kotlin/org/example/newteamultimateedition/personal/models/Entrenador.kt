package org.example.newteamultimateedition.personal.models


import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Clase que representa un Entrenador y hereda de [Persona]
 * @param id [Long] Identificador
 * @param nombre [String] Nombre
 * @param apellidos [String] Apellidos
 * @param fechaNacimiento [LocalDate] Fecha de nacimiento
 * @param fechaIncorporacion [LocalDate] Fecha de incorporacion al equipo
 * @param pais [String] Pais de origen
 * @param createdAt [LocalDateTime] Fecha y hora a la que se creo el objeto
 * @param updatedAt [LocalDateTime] Fecha y hora a la que se actualizo el objeto por ultima vez
 * @param especialidad [Especialidad] Especializacion del entrenador
 * @property rol [String] Con valor "Entrenador"
 */
class Entrenador(
    id: Long = 0L,
    nombre: String,
    apellidos: String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario: Double,
    pais: String,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
    imagen: String,
    val especialidad: Especialidad
): Persona(id = id, nombre = nombre, apellidos = apellidos, fechaNacimiento = fechaNacimiento, fechaIncorporacion = fechaIncorporacion, salario = salario, pais = pais, createdAt = createdAt, updatedAt = updatedAt, imagen = imagen) {
    /**
     * Sobreescribe la funcion [toString] predeterminada dandole un formato mas legible
     */
    override fun toString(): String {
        return "Entrenador(id= $id, nombre= $nombre, apellidos= $apellidos, fecha_nacimiento= $fechaNacimiento, fecha_incorporacion= $fechaIncorporacion, salario= $salario, pais = $pais, createdAt= $createdAt, updatedAt= $updatedAt, especialidad = $especialidad, imagen = $imagen)"
    }

    val rol: String = "Entrenador"

    val miEspecialidad = especialidad.toString()
}