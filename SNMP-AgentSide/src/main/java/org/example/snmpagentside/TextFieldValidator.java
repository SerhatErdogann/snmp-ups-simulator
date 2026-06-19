package org.example.snmpagentside;

import javafx.scene.control.TextField;

public class TextFieldValidator {

    public static void addNumericValidator(TextField textField, double min, double max) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                textField.setText(oldValue);
                return;
            }
            if (newValue.isEmpty()) {
                return;
            }
            try {
                double val = Double.parseDouble(newValue);
                if (val < min || val > max) {
                    textField.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                textField.setText(oldValue);
            }
        });
    }


    public static void addIntegerValidator(TextField textField, int min, int max) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(oldValue);
                return;
            }
            if (newValue.isEmpty()) {
                return;
            }
            try {
                int val = Integer.parseInt(newValue);
                if (val < min || val > max) {
                    textField.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                textField.setText(oldValue);
            }
        });
    }

    //eksi değer alabılıyor o yuzden direk numeric fonksıyonuna atmayıp kendimiz yazık
    public static void addTemperatureValidator(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                textField.setText(oldValue);
                return;
            }
            if (newValue.isEmpty() || newValue.equals("-")) {
                return;
            }
            try {
                double val = Double.parseDouble(newValue);
                if (val < -50 || val > 100) {
                    textField.setText(oldValue);
                }
            } catch (NumberFormatException e) {
                textField.setText(oldValue);
            }
        });
    }


    // Battery validators
    public static void addBatteryCapacityValidator(TextField textField) {
        addNumericValidator(textField, 0, 100);
    }

    public static void addBatteryVoltageValidator(TextField textField) {
        addNumericValidator(textField, 0, 400);
    }

    // Voltage validators
    public static void addVoltageValidator(TextField textField) {
        addNumericValidator(textField, 0, 400);
    }

    // Frequency validators
    public static void addFrequencyValidator(TextField textField) {
        addNumericValidator(textField, 0, 200);
    }

    public static void addBypassFrequencyValidator(TextField textField) {
        addNumericValidator(textField, 0, 100);
    }

    // Line validators
    public static void addLineCountValidator(TextField textField) {
        addIntegerValidator(textField, 0, 10);
    }

    public static void addLineIndexValidator(TextField textField) {
        addIntegerValidator(textField, 0, 10);
    }

    public static void addLineBadsValidator(TextField textField) {
        addIntegerValidator(textField, 0, 10);
    }

    // Test validators
    public static void addTestSpinlockValidator(TextField textField) {
        addIntegerValidator(textField, 0, 10);
    }

    public static void addTestResultValidator(TextField textField) {
        addIntegerValidator(textField, 1, 7);
    }

    // Alarm validator
    public static void addAlarmCountValidator(TextField textField) {
        addIntegerValidator(textField, 0, 99);
    }


    public static class UPSValidators {

        public static void applyBatteryValidators(
                TextField txtCapacity,
                TextField txtVoltage,
                TextField txtTemp) {
            addBatteryCapacityValidator(txtCapacity);
            addBatteryVoltageValidator(txtVoltage);
            addTemperatureValidator(txtTemp);
        }

        public static void applyInputValidators(
                TextField txtLineBads,
                TextField txtFreq,
                TextField txtVoltage,
                TextField txtNumLines,
                TextField txtLineIndex) {
            addLineBadsValidator(txtLineBads);
            addFrequencyValidator(txtFreq);
            addVoltageValidator(txtVoltage);
            addLineCountValidator(txtNumLines);
            addLineIndexValidator(txtLineIndex);
        }

        public static void applyOutputValidators(
                TextField txtNumLines,
                TextField txtLineIndex,
                TextField txtFreq,
                TextField txtVoltage) {
            addLineCountValidator(txtNumLines);
            addLineIndexValidator(txtLineIndex);
            addFrequencyValidator(txtFreq);
            addVoltageValidator(txtVoltage);
        }

        public static void applyBypassValidators(
                TextField txtFreq,
                TextField txtNumLines,
                TextField txtLineIndex,
                TextField txtVoltage) {
            addBypassFrequencyValidator(txtFreq);
            addLineCountValidator(txtNumLines);
            addLineIndexValidator(txtLineIndex);
            addVoltageValidator(txtVoltage);
        }

        public static void applyTestValidators(
                TextField txtSpinlock,
                TextField txtResult) {
            addTestSpinlockValidator(txtSpinlock);
            addTestResultValidator(txtResult);
        }

        public static void applyMeasurementValidators(
                TextField txtNominalInputVoltage,
                TextField txtNominalInputFreq,
                TextField txtNominalOutputVoltage,
                TextField txtNominalOutputFreq) {
            addVoltageValidator(txtNominalInputVoltage);
            addFrequencyValidator(txtNominalInputFreq);
            addVoltageValidator(txtNominalOutputVoltage);
            addFrequencyValidator(txtNominalOutputFreq);
        }
    }
}