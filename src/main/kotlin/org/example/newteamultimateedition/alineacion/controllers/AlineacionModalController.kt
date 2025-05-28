package org.example.newteamultimateedition.alineacion.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import org.example.newteamultimateedition.personal.models.Persona

class AlineacionModalController {

    //Campo fútbol
    @FXML
    private lateinit var extremoIzquierdo: Pane

    @FXML
    private lateinit var extremoIzquierdoImg: ImageView

    @FXML
    private lateinit var delanteroIzquierdo: Pane

    @FXML
    private lateinit var delanteroIzquierdoImg: ImageView

    @FXML
    private lateinit var extremoDerecho: Pane

    @FXML
    private lateinit var extremoDerechoImg: ImageView

    @FXML
    private lateinit var delanteroDerecho: Pane

    @FXML
    private lateinit var delanteroDerechoImg: ImageView

    @FXML
    private lateinit var lateralIzquierdo: Pane

    @FXML
    private lateinit var lateralIzquierdoImg: ImageView

    @FXML
    private lateinit var centralIzquierdo: Pane

    @FXML
    private lateinit var centralIzquierdoImg: ImageView

    @FXML
    private lateinit var centralDerecho: Pane

    @FXML
    private lateinit var centralDerechoImg: ImageView

    @FXML
    private lateinit var lateralDerecho: Pane

    @FXML
    private lateinit var lateralDerechoImg: ImageView

    @FXML
    private lateinit var medioCentroIzquierdo: Pane

    @FXML
    private lateinit var medioCentroIzquierdoImg: ImageView

    @FXML
    private lateinit var medioCentroDerecho: Pane

    @FXML
    private lateinit var medioCentroDerechoImg: ImageView

    @FXML
    private lateinit var poteroTitular: Pane

    @FXML
    private lateinit var porteroTitularImg: ImageView


    //Suplentes
    @FXML
    private lateinit var suplente1: Pane

    @FXML
    private lateinit var suplente1Img: ImageView

    @FXML
    private lateinit var suplente2: Pane

    @FXML
    private lateinit var suplente2Img: ImageView

    @FXML
    private lateinit var suplente3: Pane

    @FXML
    private lateinit var suplente3Img: ImageView

    @FXML
    private lateinit var suplente4: Pane

    @FXML
    private lateinit var suplente4Img: ImageView

    @FXML
    private lateinit var suplente5: Pane

    @FXML
    private lateinit var suplente5Img: ImageView

    @FXML
    private lateinit var suplente6: Pane

    @FXML
    private lateinit var suplente6Img: ImageView

    @FXML
    private lateinit var porteroSuplente: Pane

    @FXML
    private lateinit var porteroSuplenteImg: ImageView

    @FXML
    private lateinit var entrenador: Pane

    @FXML
    private lateinit var entrenadorImg: ImageView


    //Tabla
    @FXML
    private lateinit var tablaAlineacion: TableView<Persona>

    @FXML
    private lateinit var colNombre: TableColumn<Persona, String>

    @FXML
    private lateinit var colPartidosJugados: TableColumn<Persona, Int>

    @FXML
    private lateinit var colGoles: TableColumn<Persona, Int>

    @FXML
    private lateinit var colEspecialidad: TableColumn<Persona, String>


    //Botones

    @FXML
    private lateinit var addButton: Button

    @FXML
    private lateinit var saveButton: Button

    @FXML
    private lateinit var deleteButton: Button

    @FXML
    private lateinit var backButton: Button

    fun initialize() {
        initEvents()
        initBindings()
        initDefaultValues()
    }

    private fun initEvents() {
        TODO()
    }

    private fun initDefaultValues() {
        TODO()
    }

    private fun initBindings() {
        TODO()
    }



}