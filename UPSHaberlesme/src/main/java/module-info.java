module com.example.usphaberlesme {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.snmp4j;
    requires javafx.graphics;


    opens com.example.usphaberlesme to javafx.fxml;
    exports com.example.usphaberlesme;
}