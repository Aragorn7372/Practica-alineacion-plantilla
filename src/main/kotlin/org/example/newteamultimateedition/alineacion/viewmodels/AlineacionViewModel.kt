package org.example.newteamultimateedition.alineacion.viewmodels

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Alert
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.alineacion.service.AlineacionServiceImpl
import org.example.newteamultimateedition.common.error.Errors
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.routes.RoutesManager
import java.io.File
import java.time.LocalDate
import java.util.*

/**
 * Clase que representa el ViewModel de la aplicación
 * @property alineacionService servicio inyectado
 * @property modoAlineacion Modo en que se presenta la vista de alineaciones
 * @property alineacion alineación seleccionada a la hora de editar o crear
 * @see [AlineacionServiceImpl]
 */
class AlineacionViewModel (
    val alineacionService: AlineacionServiceImpl
) {
    var modoAlineacion = ModoAlineacion.VISUALIZAR
    var alineacion: Alineacion? = null
    val state: SimpleObjectProperty<GeneralState> = SimpleObjectProperty(GeneralState())

    /**
     * Data class que representa el estado general
     * @property persona La persona seleccionada en este momento
     * @property personas [ObservableList] de [Persona] que son los integrantes que hay importados en este momento
     * @property alineaciones [ObservableList] de [Alineacion]
     */
    data class GeneralState(
        var personas: ObservableList<Persona> = FXCollections.observableArrayList(), //lista de todos los integrantes
        var alineaciones: ObservableList<Alineacion> = FXCollections.observableArrayList(),
        val persona: PersonalState = PersonalState() //el integrante seleccionado
    )

    /**
     * Obtiene la [List] de [Persona] en base a la [List] de [LineaAlineacion]
     * @param list lista de alineaciones
     * @return devuelve [List] de [Persona] si va increiblemente bien, sino [Errors]
     */
    fun loadListJugadores(list:List<LineaAlineacion>): Result<List<Persona>,Errors>{
        return alineacionService.getJugadoresByLista(list)
    }

    /**
     * Carga todas las personas de la base de datos en el estado del viewmodel
     */
    fun loadAllPersonas() {
        val listaPersonas = alineacionService.getAllPersonas()
        if (listaPersonas.isOk) state.value.personas.setAll(listaPersonas.value)
        else RoutesManager.showAlertOperation(
            alerta = Alert.AlertType.ERROR,
            title = "Error",
            mensaje = listaPersonas.error.message
        )
    }

    /**
     * Carga todas las alineaciones de la base de datos
     */
    fun loadAllAlineciones() {
        val listaAlineaciones = alineacionService.getAll()
        if (listaAlineaciones.isOk) listaAlineaciones.value.forEach { state.value.alineaciones.setAll(it) }
        else RoutesManager.showAlertOperation(
            alerta = Alert.AlertType.ERROR,
            title = "Error",
            mensaje = listaAlineaciones.error.message
        )
    }

    /**
     * Guarda o actualiza una alineación.
     * @param entrenador el Entrenador
     * @param mapa la lista de Jugadores que forman la alineación
     * @param descripcion descripción de la alineación
     * @param fecha fecha del partido
     * @param id id de la alineación
     */
    fun saveAlineacion(entrenador: Entrenador, mapa: Map<Int,Jugador>, descripcion: String, fecha: LocalDate,id: Long): Result<Alineacion,AlineacionError> {
        val lineasAlienaciones = mapa.map {
                it-> LineaAlineacion(
            UUID.randomUUID(),
            idAlineacion = id,
            idPersona = it.value.id,
            posicion = it.key,
        )
        }
        if (id==0L){
        return alineacionService.save( Alineacion(
            id,
            personalList = lineasAlienaciones,
            juegoDate = fecha,
            entrenador = entrenador,
            descripcion = descripcion)
        )
       }else{
           return alineacionService.update( id,Alineacion(
               id,
               personalList = lineasAlienaciones,
               juegoDate = fecha,
               entrenador = entrenador,
               descripcion = descripcion)
           )
       }

    }

    /**
     * Exporta los datos en un fichero
     * @param alineacion La alineacion a exportar
     * @param file El archivo al cual se quieres enxportar los datos
     * @return [Result] de [Unit] en caso correcto o [Errors] en caso incorrecto o de fallo
     */
    fun exportarData(alineacion: Alineacion,file: File): Result<Unit,Errors> {
        val listaPersonas = alineacionService.getJugadoresByLista(alineacion.personalList)
            if (listaPersonas.isOk) {
            return alineacionService.exportarData(alineacion,listaPersonas.value,file)
        }else return Err(listaPersonas.error)
    }


    enum class ModoAlineacion {
        EDITAR, CREAR, VISUALIZAR
    }

    data class PersonalState(
        val id: Long = 0L,
        val nombre: String = "",
        val apellidos: String = "",
        val imagen: String = "resources/profile_picture.png",
        val especialidad: String = "",
        val posicion: String = "",
        val goles: Int = 0,
        val partidosJugados: Int = 0
    )
}
