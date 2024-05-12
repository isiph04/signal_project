package com.alerts;

import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.Comparator;
import java.util.List;

;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient)  {
        // Implementation goes here
        List<PatientRecord> patientRecordList = dataStorage.getRecords(patient.getPatientId(), System.currentTimeMillis() - 60000, System.currentTimeMillis());

        // Define an OutputStrategy
        OutputStrategy outputStrategy = new OutputStrategy() {
            @Override
            public void output(int patientId, long timestamp, String recordType, String alertType) {
                // Implementation might involve logging the alert or notifying staff
            }
        };

        trendAlert(patientRecordList);
        thresholdAlert(patientRecordList, outputStrategy);
    }


        /**
         * Triggers an alert for the monitoring system. This method can be extended to
         * notify medical staff, log the alert, or perform other actions. The method
         * currently assumes that the alert information is fully formed when passed as
         * an argument.
         *
         * @param alert the alert object containing details about the alert condition
         */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
    }

    public void trendAlert(List<PatientRecord> trendRecords) {
        // Sort the records by their timestamp in ascending order
        trendRecords.sort(Comparator.comparing(PatientRecord::getTimestamp));

        // Check the trend of the records
        for (int i = 2; i < trendRecords.size(); i++) {
            // Ensure we're only looking at blood pressure readings
            if ("SystolicPressure".equals(trendRecords.get(i).getRecordType()) || "DiastolicPressure".equals(trendRecords.get(i).getRecordType())) {
                // Calculate the differences between the three latest records
                double change1 = trendRecords.get(i - 2).getMeasurementValue() - trendRecords.get(i - 1).getMeasurementValue();
                double change2 = trendRecords.get(i - 1).getMeasurementValue() - trendRecords.get(i).getMeasurementValue();

                // Check if the changes in readings are consistently increasing or decreasing by more than 10 mmHg
                if (Math.abs(change1) > 10 && Math.abs(change2) > 10 && Math.signum(change1) == Math.signum(change2)) {
                    // Create an alert message
                    String alertType = change1 > 0 ? "Increasing blood pressure trend alert" : "Decreasing blood pressure trend alert";

                    // Trigger the alert
                    Alert alert = new Alert(String.valueOf(trendRecords.get(i).getPatientId()), alertType, trendRecords.get(i).getTimestamp());
                    triggerAlert(alert);
                }
            }
        }
    }

    public void thresholdAlert(List<PatientRecord> patientRecordList, OutputStrategy outputStrategy) {
        for (PatientRecord record : patientRecordList) {
            if ("SystolicPressure".equals(record.getRecordType())) {
                if (record.getMeasurementValue() > 180.0) {
                    // Trigger an alert for high systolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically high systolic blood pressure alert");
                } else if (record.getMeasurementValue() < 90.0) {
                    // Trigger an alert for low systolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically low systolic blood pressure alert");
                }
            } else if ("DiastolicPressure".equals(record.getRecordType())) {
                if (record.getMeasurementValue() > 120.0) {
                    // Trigger an alert for high diastolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically high diastolic blood pressure alert");
                } else if (record.getMeasurementValue() < 60.0) {
                    // Trigger an alert for low diastolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically low diastolic blood pressure alert");
                }
            }
        }
    }
}
