package org.example.newteamultimateedition.personal.extensions

import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.pow
import kotlin.math.round

/**
 * Crea una copia de un objeto de la clase Jugador
 * @param newId Nuevo id que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newNombre Nuevo nombre que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newApellidos Nuevos apellidos que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newFechaNacimiento Nueva fecha de nacimiento que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newFechaIncorporacion Nueva fecha de incorporacion que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newPais Nuevo pais que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newSalario Nuevo salario que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newPosicion Nueva posicion que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newDorsal Nueva dorsal que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newAltura Nueva altura que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newPeso Nuevo peso que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newGoles Nuevo numero de goles que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newPartidosJugados Nuevo numero de partidos jugados que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newMinutosJugados Nuevo minutos jugados que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newImagen Nueva imagen que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @timeStamp Franja de tiempo que recibira el objeto, por defecto el momento en el que se llama a la funcion
 * @return La copia del objeto creado
 */
fun Jugador.copy(
    newId: Long= this.id,
    newNombre: String= this.nombre,
    newApellidos: String = this.apellidos,
    newFechaNacimiento: LocalDate = this.fechaNacimiento,
    newFechaIncorporacion: LocalDate = this.fechaIncorporacion,
    newSalario: Double = this.salario,
    newPais: String = this.pais,
    newPosicion: Posicion = this.posicion,
    newDorsal: Int = this.dorsal,
    newAltura: Double = this.altura,
    newPeso: Double = this.peso,
    newGoles: Int  = this.goles,
    newPartidosJugados: Int  = this.partidosJugados,
    newMinutosJugados: Int = this.minutosJugados,
    newImagen: String= this.imagen,
    timeStamp: LocalDateTime = LocalDateTime.now()
): Jugador {
    return Jugador(
        id = newId,
        nombre = newNombre,
        apellidos = newApellidos,
        fechaNacimiento = newFechaNacimiento,
        fechaIncorporacion = newFechaIncorporacion,
        salario = newSalario,
        pais = newPais,
        createdAt = timeStamp,
        updatedAt = timeStamp,
        posicion = newPosicion,
        dorsal = newDorsal,
        altura = newAltura,
        peso = newPeso,
        goles = newGoles,
        partidosJugados = newPartidosJugados,
        imagen = newImagen,
        minutosJugados = newMinutosJugados
    )
}
/**
 * Crea una copia de un objeto de la clase Entrenador
 * @param newId Nuevo id que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newNombre Nuevo nombre que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newApellidos Nuevos apellidos que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newFechaNacimiento Nueva fecha de nacimiento que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newFechaIncorporacion Nueva fecha de incorporacion que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newPais Nuevo pais que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newSalario Nuevo salario que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newEspecialidad Nueva especialidad que recibira el objeto, por defecto la misma de antes
 * @param newImagen Nueva imagen que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @return La copia del objeto creado
 */
fun Entrenador.copy(
    newId: Long= this.id,
    newNombre: String= this.nombre,
    newApellidos: String = this.apellidos,
    newFechaNacimiento: LocalDate = this.fechaNacimiento,
    newFechaIncorporacion: LocalDate = this.fechaIncorporacion,
    newSalario: Double = this.salario,
    newPais: String = this.pais,
    newEspecialidad: Especialidad = this.especialidad,
    newImagen: String= this.imagen,
    timeStamp: LocalDateTime = LocalDateTime.now()
): Entrenador {
    return Entrenador(
        id = newId,
        nombre = newNombre,
        apellidos = newApellidos,
        fechaNacimiento = newFechaNacimiento,
        fechaIncorporacion = newFechaIncorporacion,
        salario = newSalario,
        pais = newPais,
        createdAt = timeStamp,
        updatedAt = timeStamp,
        especialidad = newEspecialidad,
        imagen = newImagen,
    )
}

/**
 * Función de extensión de la clase Double que redondea un doble a dos cifras decimales.
 * @return El número ya redondeado.
 */
fun Double.redondearA2Decimales(): Double {
    val factor = 10.0.pow(2)
    return round(this * factor) / factor
}