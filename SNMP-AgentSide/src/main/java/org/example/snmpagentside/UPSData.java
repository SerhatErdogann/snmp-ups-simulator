package org.example.snmpagentside;

import org.example.snmpagentside.OIDConstants;

import java.util.HashMap;
import java.util.Map;

public class UPSData {
    private final Map<String, String> dataStore;

    public UPSData() {
        this.dataStore = new HashMap<>();
        initializeDefaults();
    }

    private void initializeDefaults() {
        dataStore.put(OIDConstants.OID_FIRMWARE, "V33");
        dataStore.put(OIDConstants.OID_SERIAL, "SN123456789");
        dataStore.put(OIDConstants.OID_BATTERY_STATUS, "2");
        dataStore.put(OIDConstants.OID_BATTERY_CAPACITY, "100");
        dataStore.put(OIDConstants.OID_BATTERY_VOLTAGE, "27.4");
        dataStore.put(OIDConstants.OID_BATTERY_TEMP, "25");
        dataStore.put(OIDConstants.OID_INPUT_LINE_BADS, "0");
        dataStore.put(OIDConstants.OID_INPUT_FREQ, "50");
        dataStore.put(OIDConstants.OID_INPUT_VOLTAGE, "230");
        dataStore.put(OIDConstants.OID_INPUT_NUM_LINES, "1");
        dataStore.put(OIDConstants.OID_INPUT_LINE_INDEX, "1");
        dataStore.put(OIDConstants.OID_OUTPUT_SOURCE, "3");
        dataStore.put(OIDConstants.OID_OUTPUT_NUM_LINES, "1");
        dataStore.put(OIDConstants.OID_OUTPUT_FREQ, "50");
        dataStore.put(OIDConstants.OID_OUTPUT_VOLTAGE, "230");
        dataStore.put(OIDConstants.OID_OUTPUT_LINE_INDEX, "1");
        dataStore.put(OIDConstants.OID_BYPASS_FREQ, "50");
        dataStore.put(OIDConstants.OID_BYPASS_NUM_LINES, "1");
        dataStore.put(OIDConstants.OID_BYPASS_LINE_INDEX, "1");
        dataStore.put(OIDConstants.OID_BYPASS_VOLTAGE, "230");
        dataStore.put(OIDConstants.OID_ALARM_COUNT, "0");
        dataStore.put(OIDConstants.OID_TEST_ID, "1");
        dataStore.put(OIDConstants.OID_TEST_SPINLOCK, "1");
        dataStore.put(OIDConstants.OID_TEST_RESULT, "6");
        dataStore.put(OIDConstants.OID_NOMINAL_INPUT_VOLTAGE, "230");
        dataStore.put(OIDConstants.OID_NOMINAL_INPUT_FREQ, "50");
        dataStore.put(OIDConstants.OID_NOMINAL_OUTPUT_VOLTAGE, "230");
        dataStore.put(OIDConstants.OID_NOMINAL_OUTPUT_FREQ, "50");
        dataStore.put(OIDConstants.OID_AUTO_RESTART, "1");
        dataStore.put(OIDConstants.OID_SHUTDOWN_TYPE, "1");
    }

    public String get(String oid) {
        return dataStore.get(oid);
    }

    public void put(String oid, String value) {
        dataStore.put(oid, value);
    }

    public boolean containsKey(String oid) {
        return dataStore.containsKey(oid);
    }

    public Map<String, String> getAll() {
        return new HashMap<>(dataStore);
    }

    public void updateFromUI(
            String batteryStatus,
            String batteryCapacity,
            String batteryVoltage,
            String batteryTemp,
            String inputLineBads,
            String inputFreq,
            String inputVoltage,
            String inputNumLines,
            String inputLineIndex,
            String outputSource,
            String outputNumLines,
            String outputLineIndex,
            String outputFreq,
            String outputVoltage,
            String bypassFreq,
            String bypassNumLines,
            String bypassLineIndex,
            String bypassVoltage,
            String testId,
            String spinlock,
            String testResult,
            String alarmCount,
            String nomInVolt,
            String nomInFreq,
            String nomOutVolt,
            String nomOutFreq,
            String autoRestart,
            String shutdownType
    ) {
        // ===== BATTERY =====
        if (batteryStatus != null)
            put(OIDConstants.OID_BATTERY_STATUS, mapBatteryStatus(batteryStatus));

        if (batteryCapacity != null && !batteryCapacity.isEmpty())
            put(OIDConstants.OID_BATTERY_CAPACITY, batteryCapacity);

        if (batteryVoltage != null && !batteryVoltage.isEmpty())
            put(OIDConstants.OID_BATTERY_VOLTAGE, batteryVoltage);

        if (batteryTemp != null && !batteryTemp.isEmpty())
            put(OIDConstants.OID_BATTERY_TEMP, batteryTemp);

        // ===== INPUT =====
        if (inputLineBads != null && !inputLineBads.isEmpty())
            put(OIDConstants.OID_INPUT_LINE_BADS, inputLineBads);

        if (inputFreq != null && !inputFreq.isEmpty())
            put(OIDConstants.OID_INPUT_FREQ, inputFreq);

        if (inputVoltage != null && !inputVoltage.isEmpty())
            put(OIDConstants.OID_INPUT_VOLTAGE, inputVoltage);

        if (inputNumLines != null && !inputNumLines.isEmpty())
            put(OIDConstants.OID_INPUT_NUM_LINES, inputNumLines);

        if (inputLineIndex != null && !inputLineIndex.isEmpty())
            put(OIDConstants.OID_INPUT_LINE_INDEX, inputLineIndex);

        // ===== OUTPUT =====
        if (outputSource != null)
            put(OIDConstants.OID_OUTPUT_SOURCE, mapOutputSource(outputSource));

        if (outputNumLines != null && !outputNumLines.isEmpty())
            put(OIDConstants.OID_OUTPUT_NUM_LINES, outputNumLines);

        if (outputLineIndex != null && !outputLineIndex.isEmpty())
            put(OIDConstants.OID_OUTPUT_LINE_INDEX, outputLineIndex);

        if (outputFreq != null && !outputFreq.isEmpty())
            put(OIDConstants.OID_OUTPUT_FREQ, outputFreq);

        if (outputVoltage != null && !outputVoltage.isEmpty())
            put(OIDConstants.OID_OUTPUT_VOLTAGE, outputVoltage);

        // ===== BYPASS =====
        if (bypassFreq != null && !bypassFreq.isEmpty())
            put(OIDConstants.OID_BYPASS_FREQ, bypassFreq);

        if (bypassNumLines != null && !bypassNumLines.isEmpty())
            put(OIDConstants.OID_BYPASS_NUM_LINES, bypassNumLines);

        if (bypassLineIndex != null && !bypassLineIndex.isEmpty())
            put(OIDConstants.OID_BYPASS_LINE_INDEX, bypassLineIndex);

        if (bypassVoltage != null && !bypassVoltage.isEmpty())
            put(OIDConstants.OID_BYPASS_VOLTAGE, bypassVoltage);

        // ===== TEST =====
        if (testId != null)
            put(OIDConstants.OID_TEST_ID, testId);

        if (spinlock != null && !spinlock.isEmpty())
            put(OIDConstants.OID_TEST_SPINLOCK, spinlock);

        if (testResult != null && !testResult.isEmpty())
            put(OIDConstants.OID_TEST_RESULT, testResult);

        // ===== ALARMS =====
        if (alarmCount != null && !alarmCount.isEmpty())
            put(OIDConstants.OID_ALARM_COUNT, alarmCount);

        // ===== MEASUREMENTS =====
        if (nomInVolt != null && !nomInVolt.isEmpty())
            put(OIDConstants.OID_NOMINAL_INPUT_VOLTAGE, nomInVolt);

        if (nomInFreq != null && !nomInFreq.isEmpty())
            put(OIDConstants.OID_NOMINAL_INPUT_FREQ, nomInFreq);

        if (nomOutVolt != null && !nomOutVolt.isEmpty())
            put(OIDConstants.OID_NOMINAL_OUTPUT_VOLTAGE, nomOutVolt);

        if (nomOutFreq != null && !nomOutFreq.isEmpty())
            put(OIDConstants.OID_NOMINAL_OUTPUT_FREQ, nomOutFreq);

        // ===== CONTROLS =====
        if (autoRestart != null)
            put(OIDConstants.OID_AUTO_RESTART, autoRestart);

        if (shutdownType != null)
            put(OIDConstants.OID_SHUTDOWN_TYPE, mapShutdownType(shutdownType));
    }
    private String mapShutdownType(String ui) {
        switch (ui) {
            case "Hemen kapat": return "1";
            case "Geri sayımla kapat": return "2";
            case "Batarya bitene kadar çalış": return "3";
            default: return "?";
        }
    }

    private String mapBatteryStatus(String ui) {
        switch (ui) {
            case "Unknown": return "1";
            case "Battery Normal": return "2";
            case "Battery Low": return "3";
            case "Battery Depleted": return "4";
            case "OnBattery": return "8";
            default: return "?";
        }
    }

    private String mapOutputSource(String ui) {
        switch (ui) {
            case "Other": return "1";
            case "None": return "2";
            case "Normal": return "3";
            case "Bypass": return "4";
            case "Battery": return "5";
            default: return "?";
        }
    }




}