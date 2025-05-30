package org.example.newteamultimateedition.alineacion.viewmodels

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Alert
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.service.AlineacionServiceImpl
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.routes.RoutesManager


class AlineacionViewModel (
    val alineacionService: AlineacionServiceImpl
) {

    val state: SimpleObjectProperty<GeneralState> = SimpleObjectProperty(GeneralState())

    data class GeneralState(
        var personas: ObservableList<Persona> = FXCollections.observableArrayList(), //lista de todos los integrantes
        var alineaciones: ObservableList<Alineacion> = FXCollections.observableArrayList(),
        //val persona: PersonalState = PersonalState(), //el integrante seleccionado
    )

    fun loadAllPersonas(){
        val listaPersonas = alineacionService.getAllPersonas()
        if(listaPersonas.isOk) state.value.personas.setAll(listaPersonas.value)
        else RoutesManager.showAlertOperation(alerta = Alert.AlertType.ERROR, title = "Error", mensaje = listaPersonas.error.message)
    }

    fun loadAllAlineciones(){
        val listaAlineaciones = alineacionService.getAll()
        if(listaAlineaciones.isOk) listaAlineaciones.value.forEach { state.value.alineaciones.setAll(it) }
        else RoutesManager.showAlertOperation(alerta = Alert.AlertType.ERROR, title = "Error", mensaje = listaAlineaciones.error.message)
    }
}
    /*
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
     */
