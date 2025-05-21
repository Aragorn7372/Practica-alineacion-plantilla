package org.example.newteamultimateedition.personal.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging

/**
 * Esta clase se encarga de almacenar las funciones que validaran los datos
 * de todas las personas que se introduzcan.
 */
class PersonaValidation: Validate<Persona, PersonasError> {
    private val logger= logging()
    /**
     * Comprueba los datos de la persona.
     * @param item persona a comprobar
     * @throws PersonasError.PersonasInvalidoError
     */
    override fun validator(item: Persona): Result<Persona, PersonasError> {
        logger.debug { "Validando persona" }

                if(item.nombre.isBlank()){
                    return Err(PersonasError.PersonasInvalidoError("Nombre inválido, este campo no puede estar vacío."))
                }
                if (item.nombre.length <= 2){
                    return Err(PersonasError.PersonasInvalidoError("Nombre inválido, el nombre es demasiado corto."))
                }
                if(item.apellidos.isBlank()){
                    return Err(PersonasError.PersonasInvalidoError("Apellidos inválidos, este campo no puede estar vacío."))
                }
                if (item.apellidos.length <= 2){
                    return Err(PersonasError.PersonasInvalidoError("Apellidos inválidos, los apellidos son demasiado cortos."))
                }
                if (item.salario<=0){
                    return Err(PersonasError.PersonasInvalidoError("Salario inválido, el salario no puede ser igual o menor a 0."))
                }
                if(item.pais.isBlank()){
                    return Err(PersonasError.PersonasInvalidoError("País inválido, este campo no puede estar vacío."))
                }
                if (item.pais.length <= 2){
                    return Err(PersonasError.PersonasInvalidoError("País inválido, el país es demasiado corto."))
                }

                //para comprobar si están correctos el resto de datos
                return if(item is Jugador) validarJugador(item) else Ok(item)


    }
    /**
     * Comprueba el resto de datos de jugadores
     * @param jugadores jugador a comprobar
     * @throws PersonasError.PersonasInvalidoError
     */
    private fun validarJugador(jugadores: Jugador): Result<Persona, PersonasError> {
        val logger=logging()
        logger.debug { "validando jugador" }
        if (jugadores.dorsal<=0){
            return Err(PersonasError.PersonasInvalidoError("Dorsal inválido, el dorsal no puede ser igual o inferior a 0."))
        }
        if (jugadores.dorsal>99){
            return Err(PersonasError.PersonasInvalidoError("Dorsal inválido, el dorsal no puede ser mayor a 99."))
        }
        if (jugadores.altura<=1){
            return Err(PersonasError.PersonasInvalidoError("Altura inválida, el jugador no puede ser tan bajo."))
        }
        if(jugadores.altura>3){
            return Err(PersonasError.PersonasInvalidoError("Altura inválida, el jugador no puede ser tan alto."))
        }
        if (jugadores.peso<=45){
            return Err(PersonasError.PersonasInvalidoError("Peso inválido, necesita comer más."))
        }
        if (jugadores.peso>150){
            return Err(PersonasError.PersonasInvalidoError("Peso inválido, necesita comer menos."))
        }
        if (jugadores.goles<0){
            return Err(PersonasError.PersonasInvalidoError("Goles inválido, no puede tener goles negativos."))
        }
        if (jugadores.partidosJugados<0){
            return Err(PersonasError.PersonasInvalidoError("Partidos jugados inválidos, no puede jugar partidos negativos."))
        }
        return Ok(jugadores)
    }
}