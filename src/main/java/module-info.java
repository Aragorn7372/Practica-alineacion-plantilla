module org.example.newteamultimateedition {
    requires javafx.controls;
    requires javafx.fxml;

    requires kotlin.stdlib;
    // Logger
    requires logging.jvm;
    requires org.slf4j;
    // Kotlin Serialization
    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;
    //XML
    requires net.devrieze.xmlutil.serialization;
    // Result
    requires kotlin.result.jvm;
    // SQL
    requires java.sql;
    // Open Vadin
    requires open;
    // JDBI
    requires org.jdbi.v3.sqlobject;
    requires org.jdbi.v3.core;
    requires org.jdbi.v3.kotlin;
    requires org.jdbi.v3.sqlobject.kotlin;
    requires io.leangen.geantyref;
    requires kotlin.reflect;
    // Cache
    requires com.github.benmanes.caffeine;
    //bcrypt
    requires jbcrypt;
    //koin
    requires koin.core.jvm;



    opens org.example.newteamultimateedition to org.jdbi.v3.core, javafx.fxml, javafx.base;
    exports org.example.newteamultimateedition;

    opens org.example.newteamultimateedition.personal.controllers to javafx.fxml;
    opens org.example.newteamultimateedition.alineacion.dao to javafx.fxml;
    opens org.example.newteamultimateedition.personal.models to org.jdbi.v3.core, javafx.fxml, javafx.base;
    opens org.example.newteamultimateedition.personal.dao to javafx.fxml;
    opens org.example.newteamultimateedition.common.controller to javafx.fxml;
    opens org.example.newteamultimateedition.routes to javafx.fxml;
    opens org.example.newteamultimateedition.users.dao to javafx.fxml;
    opens org.example.newteamultimateedition.users.models to org.jdbi.v3.core, javafx.fxml, javafx.base;
    opens org.example.newteamultimateedition.users.controller to javafx.fxml;
    opens org.example.newteamultimateedition.alineacion.controllers to javafx.fxml;

    // Exportar los paquetes públicos si son utilizados en otros módulos\
    exports org.example.newteamultimateedition.personal.controllers;
    exports org.example.newteamultimateedition.alineacion.dao;
    exports org.example.newteamultimateedition.personal.dao;
    exports org.example.newteamultimateedition.users.dao;
    exports org.example.newteamultimateedition.personal.models;
    exports org.example.newteamultimateedition.common.controller;
    exports org.example.newteamultimateedition.routes;
    exports org.example.newteamultimateedition.users.controller;
    exports org.example.newteamultimateedition.alineacion.controllers;

}