package org.example.snmpagentside;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.snmp4j.*;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

public class SignalController implements CommandResponder {


    // UPS Identity
    @FXML private Label lblFirmware;
    @FXML private Label lblSerial;

    // Battery
    @FXML private ChoiceBox<String> chbBatteryStatus;
    @FXML private TextField txtBatteryCapacity;
    @FXML private TextField txtBatteryVoltage;
    @FXML private TextField txtBatteryTemp;

    // Input
    @FXML private TextField txtInputLineBads;
    @FXML private TextField txtInputFreq;
    @FXML private TextField txtInputVoltage;
    @FXML private TextField txtInputNumLines;
    @FXML private TextField txtInputLineIndex;

    // Output
    @FXML private ChoiceBox<String> chbOutputSource;
    @FXML private TextField txtOutputNumLines;
    @FXML private TextField txtOutputLineIndex;
    @FXML private TextField txtOutputFreq;
    @FXML private TextField txtOutputVoltage;

    // Bypass
    @FXML private TextField txtBypassFreq;
    @FXML private TextField txtBypassNumLines;
    @FXML private TextField txtBypassLineIndex;
    @FXML private TextField txtBypassVoltage;

    // Test
    @FXML private ChoiceBox<String> chbTestId;
    @FXML private TextField txtspinlock;
    @FXML private TextField txtTestResult;

    // Alarms
    @FXML private TextField txtAlarmCount;

    // Measurements
    @FXML private TextField txtNominalInputVoltage;
    @FXML private TextField txtNominalInputFreq;
    @FXML private TextField txtNominalOutputVoltage;
    @FXML private TextField txtNominalOutputFreq;

    @FXML private ChoiceBox<String> chbAutoRestart;
    @FXML private ChoiceBox<String> chbShutdownType;

    @FXML private Button btnUpdate;

    private Snmp snmp;
    private UPSData UPSDataStore;


    @FXML
    public void initialize() {

        UPSDataStore = new UPSData();

        chbAutoRestart.getItems().addAll("0", "1");
        chbTestId.getItems().addAll("1", "2", "3", "4");
        chbShutdownType.getItems().addAll(
                "Hemen kapat",
                "Geri sayımla kapat",
                "Batarya bitene kadar çalış"
        );
        chbBatteryStatus.getItems().addAll(
        "Battery Normal",
        "Battery Low",
        "Battery Depleted",
        "OnBattery");
        chbOutputSource.getItems().addAll(
                "Other",
        "None",
        "Normal",
        "Bypass",
        "Battery"
        );

        setAndUpdateUIFromDataStore();

        btnUpdate.setOnAction(e -> updateValue());

        controlText();

        // SNMP Agent'ı başlat
        try {
            new Thread(() -> {
                try {
                    startSNMPAgent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "SNMP-Thread").start();
        } catch (Exception e) {
            System.err.println("SNMP Agent başlatma hatası: " + e.getMessage());
            e.printStackTrace();
        }

    }


    private void setAndUpdateUIFromDataStore() {
        Platform.runLater(() -> {
            lblFirmware.setText(UPSDataStore.get(OIDConstants.OID_FIRMWARE));
            lblSerial.setText(UPSDataStore.get(OIDConstants.OID_SERIAL));

            chbAutoRestart.setValue(UPSDataStore.get(OIDConstants.OID_AUTO_RESTART));
            chbTestId.setValue(UPSDataStore.get(OIDConstants.OID_TEST_ID));
            chbShutdownType.setValue("Hemen kapat");
            chbBatteryStatus.setValue("Battery Normal");
            chbOutputSource.setValue("Normal");

            txtNominalInputVoltage.setText(UPSDataStore.get(OIDConstants.OID_NOMINAL_INPUT_VOLTAGE));
            txtNominalInputFreq.setText(UPSDataStore.get(OIDConstants.OID_NOMINAL_INPUT_FREQ));
            txtNominalOutputVoltage.setText(UPSDataStore.get(OIDConstants.OID_NOMINAL_OUTPUT_VOLTAGE));
            txtNominalOutputFreq.setText(UPSDataStore.get(OIDConstants.OID_NOMINAL_OUTPUT_FREQ));
            txtBatteryCapacity.setText(UPSDataStore.get(OIDConstants.OID_BATTERY_CAPACITY) );
            txtBatteryVoltage.setText(UPSDataStore.get(OIDConstants.OID_BATTERY_VOLTAGE));
            txtBatteryTemp.setText(UPSDataStore.get(OIDConstants.OID_BATTERY_TEMP));
            txtInputLineBads.setText(UPSDataStore.get(OIDConstants.OID_INPUT_LINE_BADS));
            txtInputFreq.setText(UPSDataStore.get(OIDConstants.OID_INPUT_FREQ));
            txtInputVoltage.setText(UPSDataStore.get(OIDConstants.OID_INPUT_VOLTAGE) );
            txtInputNumLines.setText(UPSDataStore.get(OIDConstants.OID_INPUT_NUM_LINES));
            txtInputLineIndex.setText(UPSDataStore.get(OIDConstants.OID_INPUT_LINE_INDEX));
            txtOutputNumLines.setText(UPSDataStore.get(OIDConstants.OID_OUTPUT_NUM_LINES));
            txtOutputLineIndex.setText(UPSDataStore.get(OIDConstants.OID_OUTPUT_LINE_INDEX));
            txtOutputFreq.setText(UPSDataStore.get(OIDConstants.OID_OUTPUT_FREQ));
            txtOutputVoltage.setText(UPSDataStore.get(OIDConstants.OID_OUTPUT_VOLTAGE));
            txtBypassFreq.setText(UPSDataStore.get(OIDConstants.OID_BYPASS_FREQ));
            txtBypassNumLines.setText(UPSDataStore.get(OIDConstants.OID_BYPASS_NUM_LINES));
            txtBypassLineIndex.setText(UPSDataStore.get(OIDConstants.OID_BYPASS_LINE_INDEX));
            txtBypassVoltage.setText(UPSDataStore.get(OIDConstants.OID_BYPASS_VOLTAGE));
            txtspinlock.setText(UPSDataStore.get(OIDConstants.OID_TEST_SPINLOCK));
            txtTestResult.setText(UPSDataStore.get(OIDConstants.OID_TEST_RESULT));
            txtAlarmCount.setText(UPSDataStore.get(OIDConstants.OID_ALARM_COUNT));

        });
    }


    private void startSNMPAgent() throws IOException {
        //udp haberleşmesi için transport başlat
        TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping(new UdpAddress("192.168.48.126/1161"));
        //snmp oluştur ve haberleşme protokolünü belirle (v1,v2c..)
        snmp = new Snmp(transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        //addCommandResponder snmp paketi gelirse bu sınıfta karşılanacagı garanti eder
        snmp.addCommandResponder(this);
        transport.listen();
    }

    //Command Responderin hazır metodu. İstek geldıgınde otomatik çalışır
    @Override
    public void processPdu(CommandResponderEvent event) {
        PDU pdu = event.getPDU();
        if (pdu == null) return;

        //hangi tip de istek geldiğine bak
        System.out.println("\n>>> SNMP İsteği Alındı: " + PDU.getTypeString(pdu.getType()));

        //responsePdu'yu req response ile olusturduk kı request-id eşlesin
        PDU responsePDU = new PDU(pdu);
        responsePDU.setErrorStatus(PDU.noError);
        responsePDU.setErrorIndex(0);
        responsePDU.setType(PDU.RESPONSE);

        if (pdu.getType() == PDU.GET) {
            handleGet(pdu, responsePDU);
        } else{
            return;
        }

        sendResponse(event, responsePDU);
    }

    private void handleGet(PDU pdu, PDU responsePDU) {
        for (int i = 0; i < pdu.size(); i++) {
            //gelen get isteginde kac oid var bak. ona göre çalış.
            VariableBinding vb = pdu.get(i);
            //gelen varbind'in oid'ini al
            String oid = vb.getOid().toString();

            //datastore'da var olan oidle eşleştir.
            if (UPSDataStore.containsKey(oid)) {
                String value = UPSDataStore.get(oid);
                //response'u doldur. iligi value'yu bul ve oidle birlikte yolla
                responsePDU.set(i, new VariableBinding(vb.getOid()
                        ,/*istenen değer tipi Variable olduğu için çevirdik*/ new OctetString(value)));
                System.out.println("  GET: " + oid + " = " + value);
            } else {
                responsePDU.set(i, new VariableBinding(vb.getOid(), Null.noSuchObject));
                System.out.println("  GET: " + oid + " (Not Found)");
            }
        }
    }


    private void sendResponse(CommandResponderEvent event, PDU responsePDU) {
        try {
            event.getMessageDispatcher().returnResponsePdu(
                    //hangi model (v2c v1...)
                    event.getMessageProcessingModel(),
                    //guvenlık degerlerı
                    event.getSecurityModel(),
                    event.getSecurityName(),
                    event.getSecurityLevel(),
                    responsePDU,
                    event.getMaxSizeResponsePDU(),
                    event.getStateReference(),
                    new StatusInformation()
            );
        } catch (MessageException e) {
            System.err.println("Yanıt gönderme hatası: " + e.getMessage());
        }
    }


    private void controlText() {

        // ========== BATTERY ==========
        TextFieldValidator.UPSValidators.applyBatteryValidators(
                txtBatteryCapacity,
                txtBatteryVoltage,
                txtBatteryTemp
        );

        // ========== INPUT ==========
        TextFieldValidator.UPSValidators.applyInputValidators(
                txtInputLineBads,
                txtInputFreq,
                txtInputVoltage,
                txtInputNumLines,
                txtInputLineIndex
        );

        // ========== OUTPUT ==========
        TextFieldValidator.UPSValidators.applyOutputValidators(
                txtOutputNumLines,
                txtOutputLineIndex,
                txtOutputFreq,
                txtOutputVoltage
        );

        // ========== BYPASS ==========
        TextFieldValidator.UPSValidators.applyBypassValidators(
                txtBypassFreq,
                txtBypassNumLines,
                txtBypassLineIndex,
                txtBypassVoltage
        );

        // ========== TEST ==========
        TextFieldValidator.UPSValidators.applyTestValidators(
                txtspinlock,
                txtTestResult
        );

        // ========== MEASUREMENTS ==========
        TextFieldValidator.UPSValidators.applyMeasurementValidators(
                txtNominalInputVoltage,
                txtNominalInputFreq,
                txtNominalOutputVoltage,
                txtNominalOutputFreq
        );

        // ========== ALARM ==========
        TextFieldValidator.addAlarmCountValidator(txtAlarmCount);
    }

    private void updateValue() {
        UPSDataStore.updateFromUI(
                chbBatteryStatus.getValue(),
                txtBatteryCapacity.getText(),
                txtBatteryVoltage.getText(),
                txtBatteryTemp.getText(),
                txtInputLineBads.getText(),
                txtInputFreq.getText(),
                txtInputVoltage.getText(),
                txtInputNumLines.getText(),
                txtInputLineIndex.getText(),
                chbOutputSource.getValue(),
                txtOutputNumLines.getText(),
                txtOutputLineIndex.getText(),
                txtOutputFreq.getText(),
                txtOutputVoltage.getText(),
                txtBypassFreq.getText(),
                txtBypassNumLines.getText(),
                txtBypassLineIndex.getText(),
                txtBypassVoltage.getText(),
                chbTestId.getValue(),
                txtspinlock.getText(),
                txtTestResult.getText(),
                txtAlarmCount.getText(),
                txtNominalInputVoltage.getText(),
                txtNominalInputFreq.getText(),
                txtNominalOutputVoltage.getText(),
                txtNominalOutputFreq.getText(),
                chbAutoRestart.getValue(),
                chbShutdownType.getValue()
        );
    }

    public void stopAgent() {
        try {
            if (snmp != null) {
                snmp.close();
                System.out.println("SNMP Agent durduruldu.");
            }
        } catch (IOException e) {
            System.err.println("Agent durdurma hatası: " + e.getMessage());
        }
    }
}