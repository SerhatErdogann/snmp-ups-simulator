package com.example.usphaberlesme;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SignalController {

    private Snmp snmp;
    private final String upsIp = "10.222.39.98";
    private final String community = "public";

    private final boolean running = true;

    // ========== IDENTITY ==========
    @FXML private Label lblFirmware;
    @FXML private Label lblSerial;

    // ========== BATTERY ==========
    @FXML private Label lblBatteryStatus;
    @FXML private Label lblBatteryCapacity;
    @FXML private Label lblBatteryVoltage;
    @FXML private Label lblBatteryTemp;

    // ========== INPUT ==========
    @FXML private Label lblInputLineBads;
    @FXML private Label lblInputNumLines;
    @FXML private Label lblInputLineIndex;
    @FXML private Label lblInputFreq;
    @FXML private Label lblInputVoltage;

    // ========== OUTPUT ==========
    @FXML private Label lblOutputSource;
    @FXML private Label lblOutputNumLines;
    @FXML private Label lblOutputLineIndex;
    @FXML private Label lblOutputFreq;
    @FXML private Label lblOutputVoltage;

    // ========== BYPASS ==========
    @FXML private Label lblBypassFreq;
    @FXML private Label lblBypassNumLines;
    @FXML private Label lblBypassLineIndex;
    @FXML private Label lblBypassVoltage;

    // ========== TEST ==========
    @FXML private Label lblTestId;
    @FXML private Label lblspinlock;
    @FXML private Label lblTestResult;



    // ========== ALARMS ==========
    @FXML private Label lblAlarmCount;


    // ========== MEASUREMENTS ==========
    @FXML private Label lblNominalInputVoltage;
    @FXML private Label lblNominalInputFreq;
    @FXML private Label lblNominalOutputVoltage;
    @FXML private Label lblNominalOutputFreq;

    // ========== CONTROLS ==========
    @FXML private Label lblAutoRestart;
    @FXML private Label lblShutdownType;


    public void initialize() {
        try {
            startSnmp();
            startAutoUpdateThread();
        } catch (Exception e) {
            System.out.println("SNMP başlatılamadı: " + e.getMessage());
        }
    }

    private void startSnmp() throws Exception {
        TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }


    private void startAutoUpdateThread() {
        Thread thread = new Thread(() -> {

            while (running) {
                try {

                    // ========== IDENTITY ==========
                    String firmware = snmpGet("1.3.6.1.2.1.33.1.1.3.0");
                    String serial   = snmpGet("1.3.6.1.2.1.33.1.1.4.0");

                    // ========== BATTERY ==========
                    String battStatus = snmpGet("1.3.6.1.2.1.33.1.2.1.0");
                    String battPct    = snmpGet("1.3.6.1.2.1.33.1.2.4.0");
                    String battVolt   = snmpGet("1.3.6.1.2.1.33.1.2.5.0");
                    String battTemp   = snmpGet("1.3.6.1.2.1.33.1.2.7.0");

                    // ========== INPUT ==========
                    String inLineBads = snmpGet("1.3.6.1.2.1.33.1.3.1.0");
                    String inLines    = snmpGet("1.3.6.1.2.1.33.1.3.2.0");
                    String inIndex    = snmpGet("1.3.6.1.2.1.33.1.3.3.1.1.1.0");
                    String inFreq     = snmpGet("1.3.6.1.2.1.33.1.3.3.1.2.1.0");
                    String inVolt     = snmpGet("1.3.6.1.2.1.33.1.3.3.1.3.1.0");

                    // ========== OUTPUT ==========
                    String outSource = snmpGet("1.3.6.1.2.1.33.1.4.1.0");
                    String outFreq   = snmpGet("1.3.6.1.2.1.33.1.4.2.0");
                    String outLines  = snmpGet("1.3.6.1.2.1.33.1.4.3.0");
                    String outIndex  = snmpGet("1.3.6.1.2.1.33.1.4.4.1.1.1.0");
                    String outVolt   = snmpGet("1.3.6.1.2.1.33.1.4.4.1.2.1.0");

                    // ========== BYPASS ==========
                    String byFreq  = snmpGet("1.3.6.1.2.1.33.1.5.1.0");
                    String byLines = snmpGet("1.3.6.1.2.1.33.1.5.2.0");
                    String byIndex = snmpGet("1.3.6.1.2.1.33.1.5.3.1.1.1.0");
                    String byVolt  = snmpGet("1.3.6.1.2.1.33.1.5.3.1.2.1.0");


                    // ========== TEST ==========
                    String testId = snmpGet("1.3.6.1.2.1.33.1.7.1.0");
                    String testResultSummary = snmpGet("1.3.6.1.2.1.33.1.7.3.0");
                    String spinlock = snmpGet("1.3.6.1.2.1.33.1.7.2.0");


                    // ========== ALARMS ==========
                    String alarmCount   = snmpGet("1.3.6.1.2.1.33.1.6.1.0");


                    // ========== MEASUREMENTS ==========
                    String nomInVolt = snmpGet("1.3.6.1.2.1.33.1.9.1.0");
                    String nomInFreq = snmpGet("1.3.6.1.2.1.33.1.9.2.0");
                    String nomOutVolt= snmpGet("1.3.6.1.2.1.33.1.9.3.0");
                    String nomOutFreq= snmpGet("1.3.6.1.2.1.33.1.9.4.0");

                    // ========== CONTROL ==========
                    String autoRes   = snmpGet("1.3.6.1.2.1.33.1.8.5.0");
                    String shtdwnT   = snmpGet("1.3.6.1.2.1.33.1.8.1.0");

                    Platform.runLater(() -> {

                        // IDENTITY
                        lblFirmware.setText(firmware);
                        lblSerial.setText(serial);

                        // BATTERY
                        lblBatteryStatus.setText(battStatus);
                        lblBatteryCapacity.setText(battPct+ " %");
                        lblBatteryVoltage.setText(battVolt + " V");
                        lblBatteryTemp.setText(battTemp+" °C");

                        // INPUT
                        lblInputLineBads.setText(inLineBads);
                        lblInputNumLines.setText(inLines);
                        lblInputLineIndex.setText(inIndex);
                        lblInputFreq.setText(inFreq + " Hz");
                        lblInputVoltage.setText(inVolt + " V");

                        // OUTPUT
                        lblOutputSource.setText(outSource);
                        lblOutputNumLines.setText(outLines);
                        lblOutputLineIndex.setText(outIndex);
                        lblOutputFreq.setText(outFreq+ " Hz");
                        lblOutputVoltage.setText(outVolt+ " V");

                        // BYPASS
                        lblBypassFreq.setText(byFreq);
                        lblBypassNumLines.setText(byLines);
                        lblBypassLineIndex.setText(byIndex);
                        lblBypassVoltage.setText(byVolt+ " V");

                        // TEST
                        lblTestId.setText(testId);
                        lblspinlock.setText(spinlock);
                        lblTestResult.setText(testResultSummary);


                        // ALARMS
                        lblAlarmCount.setText(alarmCount);


                        // MEASUREMENTS
                        lblNominalInputVoltage.setText(nomInVolt+ " V");
                        lblNominalInputFreq.setText(nomInFreq+ " Hz");
                        lblNominalOutputVoltage.setText(nomOutVolt+ " V");
                        lblNominalOutputFreq.setText(nomOutFreq+ " Hz");

                        // CONTROLS
                        lblAutoRestart.setText(autoRes);
                        lblShutdownType.setText(shtdwnT);

                    });

                    Thread.sleep(1000);

                } catch (Exception e) {
                    System.out.println("Thread Hatası: " + e.getMessage());
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    private String snmpGet(String oid) {
        try {
            //response için protokol duzenlemesi
            CommunityTarget<UdpAddress> target = new CommunityTarget<>();
            target.setCommunity(new OctetString(community));
            target.setAddress(new UdpAddress(upsIp + "/161"));
            target.setVersion(SnmpConstants.version2c);
            //1.5saniye cevap bekle yoksa timeout at
            target.setTimeout(1500);
            //timeout aldıktan sonra 1 kez daha dene
            target.setRetries(1);

            //oid için pdu oluşturma
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid)));
            pdu.setType(PDU.GET);

            //get ile oid ve protokol atılacak
            ResponseEvent event = snmp.get(pdu, target);

            //cevap gelirse if içine giriyor
            if (event != null && event.getResponse() != null) {
                //get(0)= get ile 1tane oid yolladık o yuzden ilkini alacagız dırek. getvariable ile de gelen degere ulastık.
                return event.getResponse().get(0).getVariable().toString();
            }

        } catch (Exception e) {
            System.out.println("SNMP GET Hatası (" + oid + "): " + e.getMessage());
        }
        return "-";
    }


}
