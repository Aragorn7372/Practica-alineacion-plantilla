package org.example.newteamultimateedition.alineacion.controllers

import com.github.benmanes.caffeine.cache.Cache
import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.newteamultimateedition.users.models.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

class AlineacionAdminController(): KoinComponent {
    private val cache: Cache<Long, User> by inject()
    private val logger= logging()
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

    fun initialize() {
        initEvents()
        initBindings()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        TODO("Not yet implemented")
    }

    private fun initBindings() {
        TODO("Not yet implemented")
    }

    private fun initEvents() {
        TODO("Not yet implemented")
    }

}