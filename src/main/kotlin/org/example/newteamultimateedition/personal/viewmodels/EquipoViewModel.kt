package org.example.newteamultimateedition.personal.viewmodels

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.example.newteamultimateedition.common.config.Config
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.extensions.redondearA2Decimales
import org.example.newteamultimateedition.personal.mapper.toEntrenadorModel
import org.example.newteamultimateedition.personal.mapper.toJugadorModel
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.Instant
import java.time.LocalDate


/**
 * Clase que representa el ViewModel de la aplicación
 * @property service servicio inyectado
 * @see [EquipoServiceImpl]
 */
class EquipoViewModel (
    private val service: PersonaServiceImpl
) {
    private val logger = logging()

    private var allIntegrantes: List<Persona> = emptyList()

    val state: SimpleObjectProperty<GeneralState> = SimpleObjectProperty(GeneralState())

    data class GeneralState(
        var personas: ObservableList<Persona> = FXCollections.observableArrayList(), //lista de todos los integrantes
        val persona: PersonalState = PersonalState(), //el integrante seleccionado
        val goalAvg: String = "0.0", //goles promedio
        val minutesAvg: String = "0.0", //minutos jugados promedio
        val totalCost: String = "0.0" // Coste total de la plantilla
    )

    data class PersonalState(
        val id: Long = 0L,
        val nombre: String = "",
        val apellidos: String = "",
        val fechaNacimiento: LocalDate = LocalDate.now(),
        val fechaIncorporacion: LocalDate = LocalDate.now(),
        val salario: Double = 0.0,
        val pais: String = "",
        val imagen: String = "media/profile_picture.png",
        val especialidad: String = "",
        val posicion: String = "",
        val dorsal: Int = 0,
        val altura: Double = 0.0,
        val peso: Double = 0.0,
        val goles: Int = 0,
        val partidosJugados: Int = 0,
        val minutosJugados: Int = 0
    )

    /**
     * Guarda un integrante
     * @param persona el integrante a guardar
     * @see updateState
     */
    fun savePersona(persona: Persona): Result<Persona, PersonasError> {
        // Comprueba si el integrante seleccionado es 0, puesto que eso significa que se está creando en ese momento. De lo contrario, lanza una actualización para que no se duplique el integrante
        logger.debug { persona.toString() }
        return if(persona.id==0L){
            service.save(persona).onSuccess {
            state.value.personas.addAll(it)
            updateState()
        }
        }else {
            service.update(persona.id,persona).onSuccess {
                updateState()
                state.value.personas.filter { it.id == persona.id }.forEach { state.value.personas[state.value.personas.indexOf(it)] = persona}
                service.getAll().value.forEach { println(it) }
            }
        }
    }

    /**
     * Elimina un integrante
     * @param id el id del integrante a eliminar
     * @see updateState
     */
    fun deletePersonas(id: Long) {
        service.delete(id).onSuccess {
            state.value.personas.removeIf { it.id == id }
        }
        updateState()
    }

    /**
     * Carga todos los integrantes en el estado
     * @see [EquipoServiceImpl.getAll]
     * @see updateState
     */
    fun loadAllPersonas() {
        logger.debug { "Cargando los integrantes en el estado" }
        val newIntegrantes = service.getAll().value
        state.value.personas.setAll(newIntegrantes)
        updateState()
    }

    /**
     * Actualiza el estado para que la media de goles, de minutos jugados y el coste total de salarios de la plantilla estén actualizados.
     * @see redondearA2Decimales
     */
    private fun updateState() {
        val goalAvg = state.value.personas.filterIsInstance<Jugador>().map { it.goles }.average().redondearA2Decimales().toString()
        val minutesAvg = state.value.personas.filterIsInstance<Jugador>().map { it.minutosJugados }.average().redondearA2Decimales().toString()
        val totalCost = state.value.personas.sumOf { it.salario }.redondearA2Decimales().toString()

        state.value = state.value.copy(
            goalAvg = goalAvg,
            minutesAvg = minutesAvg,
            totalCost = totalCost
        )
    }



    /**
     * Guarda en el estado los integrantes filtrados
     * @see updateState
     */
    fun filterPersonas(personasFiltrados: List<Persona>) {
        logger.debug { "Filtrando la lista de integrantes" }

        state.value.personas.setAll(personasFiltrados)
        updateState()

    }

    /**
     * Elimina todos los filtros
     * @see filterPersonas
     * @see updateState
     */
    fun quitarFiltros() {
        logger.debug { "Quitando filtros" }

        allIntegrantes = service.getAll().value
        filterPersonas(allIntegrantes)
        updateState()
    }

    /**
     * Realiza una copia de seguridad de los integrantes en un fichero
     * @param file el fichero
     * @return un [Result] de [Unit] en caso de exportar correctamente o de [GestionErrors.StorageError] en caso contrario
     */
    fun exportIntegrantestoFile(file: File) : Result<Unit, PersonasError> {
        logger.debug { "Exportando integrantes a fichero $file"}
        return service.exportarDatosDesdeFichero(file.toPath())
    }

    /**
     * Importa los integrantes de un fichero
     * @param file el fichero
     * @return un [Result] de una lista de [Integrante] en caso de importar correctamente o de [GestionErrors.StorageError] en caso contrario
     */
    fun loadIntegrantesFromFile(file: File) : Result<List<Persona>, PersonasError> {
        logger.debug { "Cargando integrantes desde fichero $file"}
        return service.importarDatosDesdeFichero(file.toPath()).also { loadAllPersonas() }
    }

    /**
     * Crea un integrante vacío
     */
    fun createEmptyPersona(emptyIntegrante: PersonalState) {
        state.value = state.value.copy(persona = emptyIntegrante)
    }

    /**
     * Actualiza el integrante seleccionado con los datos del integrante que le entra por parámetro
     * @param persona integrante cuyos datos queremos guardar en el integrante seleccionado
     */
    fun updatePersonalSelected(persona: Persona) {
        if (persona is Jugador){
            state.value = state.value.copy(
                persona = PersonalState(
                    id = persona.id,
                    nombre = persona.nombre,
                    apellidos = persona.apellidos,
                    fechaNacimiento = persona.fechaNacimiento,
                    fechaIncorporacion = persona.fechaIncorporacion,
                    salario = persona.salario,
                    pais = persona.pais,
                    imagen = persona.imagen,
                    posicion = persona.posicion.toString(),
                    dorsal = persona.dorsal,
                    altura = persona.altura,
                    peso = persona.peso,
                    goles = persona.goles,
                    partidosJugados = persona.partidosJugados,
                    minutosJugados = persona.minutosJugados
                )
            )
        }
        else if (persona is Entrenador){
            state.value = state.value.copy(
                persona = PersonalState(
                    id = persona.id,
                    nombre = persona.nombre,
                    apellidos = persona.apellidos,
                    fechaNacimiento = persona.fechaNacimiento,
                    fechaIncorporacion = persona.fechaIncorporacion,
                    salario = persona.salario,
                    pais = persona.pais,
                    imagen = persona.imagen,
                    especialidad = persona.especialidad.toString()
                )
            )
        }
    }

    /**
     * Actualiza la imagen de un integrante
     * @param fileName ruta del archivo de imagen
     * @see getImagenName
     */
    fun updateImageIntegrante(fileName: File) {
        logger.debug { "Guardando imagen $fileName" }

        val newName = getImagenName(fileName)
        val newFileImage = File(Config.configProperties.imagesDirectory, newName)

        logger.debug { "Copiando a: ${newFileImage.absolutePath}" }

        Files.copy(fileName.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)

        state.value = state.value.copy(
            persona = state.value.persona.copy(
                imagen = newFileImage.toURI().toString()
            )
        )
        val id = state.value.persona.id
        if (state.value.persona.especialidad == "") {
            service.update(id, state.value.persona.toJugadorModel()).onSuccess {
                state.value.personas.find { it.id == id }?.let {it.imagen = newFileImage.toURI().toString()}
            }
        }
        else service.update(id, state.value.persona.toEntrenadorModel()).onSuccess {
            state.value.personas.find { it.id == id }?.let {it.imagen = newFileImage.toURI().toString()}
        }
    }

    /**
     * Obtiene el nombre de la imagen a partir de la ruta donde se encuentra
     * @param newFileImage la ruta de la imagen
     * @return el nombre de la imagen
     */
    private fun getImagenName(newFileImage: File): String {
        val name = newFileImage.name
        val extension = name.substring(name.lastIndexOf(".") + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

}

 