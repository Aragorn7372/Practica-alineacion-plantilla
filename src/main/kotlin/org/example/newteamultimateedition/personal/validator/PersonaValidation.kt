package org.example.newteamultimateedition.personal.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.exception.PersonasException
import org.example.practicaenequipocristianvictoraitornico.players.models.Jugadores
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.lighthousegames.logging.logging

/**
 * Esta clase se encarga de almacenar las funciones que validaran los datos
 * de todas las personas que se introduzcan.
 */
class PersonaValidation: Validate<Persona, PersonasException> {
    private val logger= logging()
    /**
     * Comprueba los datos de la persona.
     * @param item persona a comprobar
     * @throws PersonasException.PersonasInvalidoException
     */
    override fun validator(item: Persona): Result<Persona, PersonasException> {
        logger.debug { "Validando persona" }

                if(item.nombre.isBlank()){
                    return Err(PersonasException.PersonasInvalidoException("Nombre inválido, este campo no puede estar vacío."))
                }
                if (item.nombre.length <= 2){
                    return Err(PersonasException.PersonasInvalidoException("Nombre inválido, el nombre es demasiado corto."))
                }
                if(item.apellidos.isBlank()){
                    return Err(PersonasException.PersonasInvalidoException("Apellidos inválidos, este campo no puede estar vacío."))
                }
                if (item.apellidos.length <= 2){
                    return Err(PersonasException.PersonasInvalidoException("Apellidos inválidos, los apellidos son demasiado cortos."))
                }
                if (item.salario<=0){
                    return Err(PersonasException.PersonasInvalidoException("Salario inválido, el salario no puede ser igual o menor a 0."))
                }
                if(item.pais.isBlank()){
                    return Err(PersonasException.PersonasInvalidoException("País inválido, este campo no puede estar vacío."))
                }
                if (item.pais.length <= 2){
                    return Err(PersonasException.PersonasInvalidoException("País inválido, el país es demasiado corto."))
                }

                //para comprobar si están correctos el resto de datos
                return if(item is Jugadores) validarJugador(item) else Ok(item)


    }
    /**
     * Comprueba el resto de datos de jugadores
     * @param jugadores jugador a comprobar
     * @throws PersonasException.PersonasInvalidoException
     */
    private fun validarJugador(jugadores: Jugadores): Result<Persona, PersonasException> {
        val logger=logging()
        logger.debug { "validando jugador" }
        if (jugadores.dorsal<=0){
            return Err(PersonasException.PersonasInvalidoException("Dorsal inválido, el dorsal no puede ser igual o inferior a 0."))
        }
        if (jugadores.dorsal>99){
            return Err(PersonasException.PersonasInvalidoException("Dorsal inválido, el dorsal no puede ser mayor a 99."))
        }
        if (jugadores.altura<=1){
            return Err(PersonasException.PersonasInvalidoException("Altura inválida, el jugador no puede ser tan bajo."))
        }
        if(jugadores.altura>3){
            return Err(PersonasException.PersonasInvalidoException("Altura inválida, el jugador no puede ser tan alto."))
        }
        if (jugadores.peso<=45){
            return Err(PersonasException.PersonasInvalidoException("Peso inválido, necesita comer más."))
        }
        if (jugadores.peso>150){
            return Err(PersonasException.PersonasInvalidoException("Peso inválido, necesita comer menos."))
        }
        if (jugadores.goles<0){
            return Err(PersonasException.PersonasInvalidoException("Goles inválido, no puede tener goles negativos."))
        }
        if (jugadores.partidosJugados<0){
            return Err(PersonasException.PersonasInvalidoException("Partidos jugados inválidos, no puede jugar partidos negativos."))
        }
        return Ok(jugadores)
    }
}