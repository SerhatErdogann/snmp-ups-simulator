module org.example.snmpclientside {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.snmp4j;


    opens org.example.snmpclientside to javafx.fxml;
    exports org.example.snmpclientside;
}