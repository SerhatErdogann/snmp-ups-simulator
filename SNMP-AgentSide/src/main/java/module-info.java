module org.example.snmpagentside {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.snmp4j;


    opens org.example.snmpagentside to javafx.fxml;
    exports org.example.snmpagentside;
}