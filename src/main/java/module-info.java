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



    opens org.example.newteamultimateedition to javafx.fxml;
    exports org.example.newteamultimateedition;
}