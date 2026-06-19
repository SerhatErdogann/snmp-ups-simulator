package org.example.snmpagentside;

public class OIDConstants {
    // UPS Identity
    public static final String OID_FIRMWARE = "1.3.6.1.2.1.33.1.1.3.0";
    public static final String OID_SERIAL = "1.3.6.1.2.1.33.1.1.4.0";

    // Battery
    public static final String OID_BATTERY_STATUS = "1.3.6.1.2.1.33.1.2.1.0";
    public static final String OID_BATTERY_CAPACITY = "1.3.6.1.2.1.33.1.2.4.0";
    public static final String OID_BATTERY_VOLTAGE = "1.3.6.1.2.1.33.1.2.5.0";
    public static final String OID_BATTERY_TEMP = "1.3.6.1.2.1.33.1.2.7.0";

    // Input
    public static final String OID_INPUT_LINE_BADS = "1.3.6.1.2.1.33.1.3.1.0";
    public static final String OID_INPUT_FREQ = "1.3.6.1.2.1.33.1.3.3.1.2.1.0";
    public static final String OID_INPUT_VOLTAGE = "1.3.6.1.2.1.33.1.3.3.1.3.1.0";
    public static final String OID_INPUT_NUM_LINES = "1.3.6.1.2.1.33.1.3.2.0";
    public static final String OID_INPUT_LINE_INDEX = "1.3.6.1.2.1.33.1.3.3.1.1.1.0";

    // Output
    public static final String OID_OUTPUT_SOURCE = "1.3.6.1.2.1.33.1.4.1.0";
    public static final String OID_OUTPUT_NUM_LINES = "1.3.6.1.2.1.33.1.4.3.0";
    public static final String OID_OUTPUT_FREQ = "1.3.6.1.2.1.33.1.4.2.0";
    public static final String OID_OUTPUT_VOLTAGE = "1.3.6.1.2.1.33.1.4.4.1.2.1.0";
    public static final String OID_OUTPUT_LINE_INDEX = "1.3.6.1.2.1.33.1.4.4.1.1.1.0";

    // Bypass
    public static final String OID_BYPASS_FREQ = "1.3.6.1.2.1.33.1.5.1.0";
    public static final String OID_BYPASS_NUM_LINES = "1.3.6.1.2.1.33.1.5.2.0";
    public static final String OID_BYPASS_LINE_INDEX = "1.3.6.1.2.1.33.1.5.3.1.1.1.0";
    public static final String OID_BYPASS_VOLTAGE = "1.3.6.1.2.1.33.1.5.3.1.2.1.0";

    // Alarms & Tests
    public static final String OID_ALARM_COUNT = "1.3.6.1.2.1.33.1.6.1.0";
    public static final String OID_TEST_ID = "1.3.6.1.2.1.33.1.7.1.0";
    public static final String OID_TEST_SPINLOCK = "1.3.6.1.2.1.33.1.7.2.0";
    public static final String OID_TEST_RESULT = "1.3.6.1.2.1.33.1.7.3.0";

    // Measurements
    public static final String OID_NOMINAL_INPUT_VOLTAGE = "1.3.6.1.2.1.33.1.9.1.0";
    public static final String OID_NOMINAL_INPUT_FREQ = "1.3.6.1.2.1.33.1.9.2.0";
    public static final String OID_NOMINAL_OUTPUT_VOLTAGE = "1.3.6.1.2.1.33.1.9.3.0";
    public static final String OID_NOMINAL_OUTPUT_FREQ = "1.3.6.1.2.1.33.1.9.4.0";

    // Controls
    public static final String OID_AUTO_RESTART = "1.3.6.1.2.1.33.1.8.5.0";
    public static final String OID_SHUTDOWN_TYPE = "1.3.6.1.2.1.33.1.8.1.0";

}