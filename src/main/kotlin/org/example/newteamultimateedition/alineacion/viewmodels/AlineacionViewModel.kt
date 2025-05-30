package org.example.newteamultimateedition.alineacion.viewmodels

import com.github.michaelbull.result.Result
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
import java.time.LocalDate
import java.util.*


class AlineacionViewModel (
    val alineacionService: AlineacionServiceImpl
) {
    var modoAlineacion = ModoAlineacion.VISUALIZAR
    var alineacion: Alineacion? = null

    val state: SimpleObjectProperty<GeneralState> = SimpleObjectProperty(GeneralState())

    data class GeneralState(
        var personas: ObservableList<Persona> = FXCollections.observableArrayList(), //lista de todos los integrantes
        var alineaciones: ObservableList<Alineacion> = FXCollections.observableArrayList(),
        val persona: PersonalState = PersonalState() //el integrante seleccionado
    )
    fun loadListJugadores(list:List<LineaAlineacion>): Result<List<Persona>,Errors>{
        return alineacionService.getJugadoresByLista(list)
    }
    fun loadAllPersonas() {
        val listaPersonas = alineacionService.getAllPersonas()
        if (listaPersonas.isOk) state.value.personas.setAll(listaPersonas.value)
        else RoutesManager.showAlertOperation(
            alerta = Alert.AlertType.ERROR,
            title = "Error",
            mensaje = listaPersonas.error.message
        )
    }

    fun loadAllAlineciones() {
        val listaAlineaciones = alineacionService.getAll()
        if (listaAlineaciones.isOk) listaAlineaciones.value.forEach { state.value.alineaciones.setAll(it) }
        else RoutesManager.showAlertOperation(
            alerta = Alert.AlertType.ERROR,
            title = "Error",
            mensaje = listaAlineaciones.error.message
        )
    }

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
