package org.example.newteamultimateedition.alineacion.controllers

import javafx.fxml.FXML
import javafx.scene.control.*

class AlineacionAdminController {

    //Botones HBox de abajo
    @FXML
    lateinit var btnEditar: Button
    @FXML
    lateinit var btnVisualizar: Button
    @FXML
    lateinit var btnCrearAlineacion: Button
    @FXML
    lateinit var btnEditarPersonas: Button

    // MenuBar
    @FXML
    lateinit var importButton: MenuItem
    @FXML
    lateinit var exportButton: MenuItem
    @FXML
    lateinit var exitButton: MenuItem
    @FXML
    lateinit var aboutButton:MenuItem
    @FXML
    lateinit var logoutButton:MenuItem

    // Hbox de arriba
    @FXML
    lateinit var buscador: TextField
    @FXML
    lateinit var filterComboBox: ComboBox<String>

    //TableView
    @FXML
    lateinit var colId: TableColumn<String, String>
    @FXML
    lateinit var colFechaJuego: TableColumn<String, String>
    @FXML
    lateinit var colUpdatedAt: TableColumn<String, String>


}