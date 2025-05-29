package org.example.newteamultimateedition.personal.mapper

import org.example.newteamultimateedition.personal.dao.PersonaEntity
import org.example.newteamultimateedition.personal.dto.IntegranteDTO
import org.example.newteamultimateedition.personal.dto.IntegranteXmlDTO
import org.example.newteamultimateedition.personal.models.*
import org.example.newteamultimateedition.personal.viewmodels.EquipoViewModel
import java.time.LocalDate

    /**
     * Convierte un [PersonaEntity] en un [Persona]
     */
    fun PersonaEntity.toModel(): Persona {
        return if (this.rol == "Jugador") {
            Jugador(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = fechaNacimiento,
                fechaIncorporacion = fechaIncorporacion,
                salario = salario,
                pais = pais,
                posicion = Posicion.valueOf(posicion!!),
                dorsal = dorsal!!,
                altura = altura!!,
                peso = peso!!,
                goles = goles!!,
                partidosJugados = partidosJugados!!,
                createdAt = createdAt,
                updatedAt = updatedAt,
                imagen = imagen,
                minutosJugados = minutosJugados!!,
                isDeleted = isDeleted,
            )
        } else {
            Entrenador(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = fechaNacimiento,
                fechaIncorporacion = fechaIncorporacion,
                salario = salario,
                pais = pais,
                especialidad = Especialidad.valueOf(especialidad!!),
                createdAt = createdAt,
                updatedAt = updatedAt,
                imagen = imagen,
                isDeleted = isDeleted,
            )
        }
    }
/* id = 2,
        rol = "Entrenador",
        nombre = "Entrenadora",
        apellidos = "hola",
        fechaNacimiento = LocalDate.parse("2020-01-01"),
        fechaIncorporacion = LocalDate.parse("2020-01-02"),
        salario = 3000.0,
        pais = "españa",
        especialidad = "ENTRENADOR_PRINCIPAL",
        imagen = "oijsdoiasjd",
        createdAt = persona2.createdAt,
        updatedAt = persona2.updatedAt,*/

fun Persona.toEntity(): PersonaEntity {
    return when(this){
        is Jugador -> this.toEntity()
        is Entrenador -> this.toEntity()
        else -> throw IllegalArgumentException("No se trata de un jugador ni de un entrenador")
    }
}

    /**
     * Convierte un [Jugador] en un [PersonaEntity]
     */
    fun Jugador.toEntity(): PersonaEntity {
        return PersonaEntity(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fechaNacimiento = fechaNacimiento,
            fechaIncorporacion = fechaIncorporacion,
            salario = salario,
            pais = pais,
            rol = "Jugador",
            posicion = posicion.toString(),
            especialidad = null,
            dorsal = dorsal,
            altura = altura,
            peso = peso,
            goles = goles,
            partidosJugados = partidosJugados,
            createdAt = createdAt,
            updatedAt = updatedAt,
            minutosJugados = minutosJugados,
            imagen = imagen,
            isDeleted = isDeleted,
        )
    }

    /**
     * Convierte un [Entrenador] en un [PersonaEntity]
     */
    fun Entrenador.toEntity(): PersonaEntity {
        return PersonaEntity(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fechaNacimiento = fechaNacimiento,
            fechaIncorporacion = fechaIncorporacion,
            salario = salario,
            pais = pais,
            rol = "Entrenador",
            posicion = null,
            especialidad = especialidad.toString(),
            dorsal = null,
            altura = null,
            peso = null,
            goles = null,
            partidosJugados = null,
            createdAt = createdAt,
            updatedAt = updatedAt,
            minutosJugados = null,
            imagen = imagen,
            isDeleted = isDeleted,
        )
    }

    /**
     * Funcion de extension que convierte [IntegranteDTO] a un objeto [Persona] [Jugador] o [Entrenador] segun [IntegranteDTO.rol]
     * @return La version [Persona] de la DTO. O bien [Jugador] o [Entrenador]
     */
    fun IntegranteDTO.toModel(): Persona {
        return if (this.rol == "Jugador") {
            Jugador(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = LocalDate.parse(fechaNacimiento),
                fechaIncorporacion = LocalDate.parse(fechaIncorporacion),
                salario = salario,
                pais = pais,
                posicion = Posicion.valueOf(posicion!!),
                dorsal = dorsal!!,
                altura = altura!!,
                peso = peso!!,
                goles = goles!!,
                partidosJugados = partidosJugados!!,
                imagen = imagen,
                minutosJugados = minutosJugados!!,
                isDeleted = isDeleted,
            )
        } else {
            Entrenador(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = LocalDate.parse(fechaNacimiento),
                fechaIncorporacion = LocalDate.parse(fechaIncorporacion),
                salario = salario,
                pais = pais,
                especialidad = Especialidad.valueOf(especialidad!!),
                imagen = imagen,
                isDeleted = isDeleted,
            )
        }
    }

    /**
     * Funcion de extension que convierte [IntegranteXmlDTO] a un objeto [Persona] [Jugador] o [Entrenador] segun [IntegranteXmlDTO.rol]
     * @return La version [Persona] de la DTO. O bien [Jugador] o [Entrenador]
     */
    fun IntegranteXmlDTO.toModel(): Persona {
        return if (this.rol == "Jugador") {
            Jugador(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = LocalDate.parse(fechaNacimiento),
                fechaIncorporacion = LocalDate.parse(fechaIncorporacion),
                salario = salario,
                pais = pais,
                posicion = Posicion.valueOf(posicion!!),
                dorsal = dorsal!!.toInt(),
                altura = altura!!.toDouble(),
                peso = peso!!.toDouble(),
                goles = goles!!.toInt(),
                partidosJugados = partidosJugados!!.toInt(),
                imagen = imagen,
                minutosJugados = minutosJugados!!.toInt(),
                isDeleted = isDeleted
            )
        } else {
            Entrenador(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = LocalDate.parse(fechaNacimiento),
                fechaIncorporacion = LocalDate.parse(fechaIncorporacion),
                salario = salario,
                pais = pais,
                especialidad = Especialidad.valueOf(especialidad!!),
                imagen = imagen,
                isDeleted = isDeleted,
            )
        }
    }

    /**
     * Funcion de extension que convierte un [Entrenador] a [IntegranteXmlDTO]
     * @return la version [IntegranteXmlDTO] del objeto con los campos que no tiene vacios
     */
    fun Entrenador.toXmlDTO(): IntegranteXmlDTO {
        return IntegranteXmlDTO(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fechaNacimiento = fechaNacimiento.toString(),
            fechaIncorporacion = fechaIncorporacion.toString(),
            salario = salario,
            pais = pais,
            especialidad = especialidad.toString(),
            rol = "Entrenador",
            posicion = "",
            dorsal = "",
            altura = "",
            peso = "",
            goles = "",
            partidosJugados = "",
            minutosJugados = "",
            imagen = imagen,
            isDeleted = isDeleted,
        )
    }

    /**
     * Funcion de extension que convierte un [Jugador] a [IntegranteXmlDTO]
     * @return la version [IntegranteXmlDTO] del objeto con los campos que no tiene vacios
     */
    fun Jugador.toXmlDTO(): IntegranteXmlDTO {
        return IntegranteXmlDTO(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fechaNacimiento = fechaNacimiento.toString(),
            fechaIncorporacion = fechaIncorporacion.toString(),
            salario = salario,
            pais = pais,
            especialidad = "",
            rol = "Jugador",
            posicion = posicion.toString(),
            dorsal = dorsal.toString(),
            altura = altura.toString(),
            peso = peso.toString(),
            goles = goles.toString(),
            partidosJugados = partidosJugados.toString(),
            minutosJugados = minutosJugados.toString(),
            imagen = imagen,
            isDeleted = isDeleted,
        )
    }

    /**
     * Funcion de extension que convierte un [Entrenador] en su version [IntegranteDTO]
     * @return la version [IntegranteDTO] del objeto con los campos que no tiene vacios
     */
    fun Entrenador.toDto(): IntegranteDTO {
        return IntegranteDTO(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fechaNacimiento = fechaNacimiento.toString(),
            fechaIncorporacion = fechaIncorporacion.toString(),
            salario = salario,
            pais = pais,
            especialidad = especialidad.toString(),
            rol = "Entrenador",
            posicion = "",
            dorsal = null,
            altura = null,
            peso = null,
            goles = null,
            partidosJugados = null,
            minutosJugados = null,
            imagen = imagen,
            isDeleted = isDeleted
        )
    }

    /**
     * Funcion de extension que convierte un [Jugador] en su version [IntegranteDTO]
     * @return la version [IntegranteDTO] del objeto con los campos que no tiene vacios
     */
    fun Jugador.toDto(): IntegranteDTO {
        return IntegranteDTO(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fechaNacimiento = fechaNacimiento.toString(),
            fechaIncorporacion = fechaIncorporacion.toString(),
            salario = salario,
            pais = pais,
            rol = "Jugador",
            especialidad = "",
            posicion = posicion.toString(),
            dorsal = dorsal,
            altura = altura,
            peso = peso,
            goles = goles,
            partidosJugados = partidosJugados,
            minutosJugados = minutosJugados,
            imagen = imagen,
            isDeleted = isDeleted,
        )
    }

/**
 * Convierte un [EquipoViewModel.IntegranteState] en un [Jugador]
 */
fun EquipoViewModel.PersonalState.toJugadorModel(): Persona {
    return Jugador(
        id=this.id,
        nombre = this.nombre,
        apellidos = this.apellidos,
        fechaNacimiento = this.fechaNacimiento,
        fechaIncorporacion = this.fechaIncorporacion,
        salario = this.salario,
        pais = this.pais,
        imagen = this.imagen,
        posicion = Posicion.valueOf(this.posicion),
        dorsal = this.dorsal,
        altura = this.altura,
        peso = this.peso,
        goles = this.goles,
        partidosJugados = this.partidosJugados,
        minutosJugados = this.minutosJugados,
        isDeleted = false
    )
}
/**
 * Convierte un [EquipoViewModel.IntegranteState] en un [Entrenador]
 */
fun EquipoViewModel.PersonalState.toEntrenadorModel(): Persona {
    return Entrenador(
        id = this.id,
        nombre = this.nombre,
        apellidos = this.apellidos,
        fechaNacimiento = this.fechaNacimiento,
        fechaIncorporacion = this.fechaIncorporacion,
        salario = this.salario,
        pais = this.pais,
        imagen = this.imagen,
        especialidad = Especialidad.valueOf(this.especialidad),
        isDeleted = false
    )
}


