package org.example.snmpagentside;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private SignalController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("signal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 330, 500);
        stage.setTitle("UPS SNMP Agent Monitor");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        if (controller != null) {
            controller.stopAgent();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}