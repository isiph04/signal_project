package com.alerts;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private Map<Integer, ECGDataGenerator> ecgDataGeneratorMap;
    public static final Logger LOGGER = Logger.getLogger(AlertGenerator.class.getName());

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
        lowSaturationAlert(patientRecordList, outputStrategy);
        rapidDropAlert(patientRecordList, outputStrategy);
    }


        /**
         * Triggers an alert for the monitoring system. This method can be extended to
         * notify medical staff, log the alert, or perform other actions. The method
         * currently assumes that the alert information is fully formed when passed as
         * an argument.
         *
         * @param alert the alert object containing details about the alert condition
         */
    private void triggerAlert(Alert alert)
    {
        // Implementation might involve logging the alert or notifying staff
        LOGGER.info(alert.toString());
    }

    // task 1
    public void trendAlert(List<PatientRecord> trendRecords)
    {
        // Sort the records by their timestamp in ascending order
        trendRecords.sort(Comparator.comparing(PatientRecord::getTimestamp));

        // Check the trend of the records
        for (int i = 2; i < trendRecords.size(); i++)
        {
            // Ensure we're only looking at blood pressure readings
            if ("SystolicPressure".equals(trendRecords.get(i).getRecordType()) || "DiastolicPressure".equals(trendRecords.get(i).getRecordType()))
            {
                // Calculate the differences between the three latest records
                double change1 = trendRecords.get(i - 2).getMeasurementValue() - trendRecords.get(i - 1).getMeasurementValue();
                double change2 = trendRecords.get(i - 1).getMeasurementValue() - trendRecords.get(i).getMeasurementValue();

                // Check if the changes in readings are consistently increasing or decreasing by more than 10 mmHg
                if (Math.abs(change1) > 10 && Math.abs(change2) > 10 && Math.signum(change1) == Math.signum(change2))
                {
                    // Create an alert message
                    String alertType = change1 > 0 ? "Increasing blood pressure trend alert" : "Decreasing blood pressure trend alert";

                    // Trigger the alert
                    Alert alert = new Alert(String.valueOf(trendRecords.get(i).getPatientId()), alertType, trendRecords.get(i).getTimestamp());
                    triggerAlert(alert);
                }
            }
        }
    }

    public void thresholdAlert(List<PatientRecord> patientRecordList, OutputStrategy outputStrategy)
    {
        for (PatientRecord record : patientRecordList)
        {
            if ("SystolicPressure".equals(record.getRecordType()))
            {
                if (record.getMeasurementValue() > 180.0)
                {
                    // Trigger an alert for high systolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically high systolic blood pressure alert");
                }
                else if (record.getMeasurementValue() < 90.0)
                {
                    // Trigger an alert for low systolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically low systolic blood pressure alert");
                }
            }
            else if ("DiastolicPressure".equals(record.getRecordType())) {
                if (record.getMeasurementValue() > 120.0)
                {
                    // Trigger an alert for high diastolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically high diastolic blood pressure alert");
                }
                else if (record.getMeasurementValue() < 60.0)
                {
                    // Trigger an alert for low diastolic blood pressure
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Critically low diastolic blood pressure alert");
                }
            }
        }
    }

    // task 2
    public void lowSaturationAlert(List<PatientRecord> patientRecordList, OutputStrategy outputStrategy)
    {
        for (PatientRecord record : patientRecordList)
        {
            if ("OxygenSaturation".equals(record.getRecordType()))
            {
                if (record.getMeasurementValue() < 92.0) {
                    // Trigger an alert for low oxygen saturation
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Low oxygen saturation alert");
                }
            }
        }
    }

    public void rapidDropAlert(List<PatientRecord> patientRecordList, OutputStrategy outputStrategy)
    {
        // Sort the records by their timestamp in ascending order
        patientRecordList.sort(Comparator.comparing(PatientRecord::getTimestamp));

        for (int i = 1; i < patientRecordList.size(); i++)
        {
            // Ensure we're only looking at oxygen saturation readings
            if ("OxygenSaturation".equals(patientRecordList.get(i).getRecordType()))
            {
                // Calculate the difference between the two latest records
                double change = patientRecordList.get(i - 1).getMeasurementValue() - patientRecordList.get(i).getMeasurementValue();

                // Check if the change in readings is a drop of 5% or more
                if (change >= 5.0)
                {
                    // Trigger the alert
                    outputStrategy.output(patientRecordList.get(i).getPatientId(), patientRecordList.get(i).getTimestamp(), patientRecordList.get(i).getRecordType(), "Rapid drop in oxygen saturation alert");
                }
            }
        }

    }

    // task 3

    public void hypotensiveHypoxemiaAlert(List<PatientRecord> patientRecordList, OutputStrategy outputStrategy)
    {
        // Sort the records by their timestamp in ascending order
        patientRecordList.sort(Comparator.comparing(PatientRecord::getTimestamp));

        // Initialize variables to hold the latest systolic pressure and oxygen saturation readings
        double latestSystolicPressure = 0.0;
        double latestOxygenSaturation = 0.0;

        // Iterate over the patient records
        for (PatientRecord record : patientRecordList) {
            // Update the latest systolic pressure reading if the current record is a systolic pressure reading
            if ("SystolicPressure".equals(record.getRecordType())) {
                latestSystolicPressure = record.getMeasurementValue();
            }

            // Update the latest oxygen saturation reading if the current record is an oxygen saturation reading
            if ("OxygenSaturation".equals(record.getRecordType())) {
                latestOxygenSaturation = record.getMeasurementValue();
            }

            // Check if the latest systolic pressure and oxygen saturation readings meet the alert condition
            if (latestSystolicPressure < 90.0 && latestOxygenSaturation < 92.0) {
                // Trigger the alert
                outputStrategy.output(record.getPatientId(), record.getTimestamp(), "HypotensiveHypoxemia", "Hypotensive hypoxemia alert");
                break;
            }
        }
    }

    // task 4
    public void abnormalHeartRateAlert(List<PatientRecord> patientRecordList, OutputStrategy outputStrategy)
    {
        for (PatientRecord record : patientRecordList)
        {
            if ("HeartRate".equals(record.getRecordType()))
            {
                if (record.getMeasurementValue() < 50.0 || record.getMeasurementValue() > 100.0) {
                    // Trigger an alert for abnormal heart rate
                    outputStrategy.output(record.getPatientId(), record.getTimestamp(), record.getRecordType(), "Abnormal heart rate alert");
                }
            }
        }
    }


    public void irregularBeatAlert(List<PatientRecord> patientRecordList, OutputStrategy outputStrategy)
    {
        // Sort the records by their timestamp in ascending order
        patientRecordList.sort(Comparator.comparing(PatientRecord::getTimestamp));

        for (int i = 1; i < patientRecordList.size(); i++)
        {
            // Ensure we're only looking at heart beat readings
            if ("HeartBeat".equals(patientRecordList.get(i).getRecordType()))
            {
                // Calculate the difference between the two latest records
                double change = patientRecordList.get(i - 1).getMeasurementValue() - patientRecordList.get(i).getMeasurementValue();

                // Check if the change in readings is a significant variation
                if (Math.abs(change) > 0.1) // Assuming 0.1 as a significant variation
                {
                    // Trigger the alert
                    outputStrategy.output(patientRecordList.get(i).getPatientId(), patientRecordList.get(i).getTimestamp(), patientRecordList.get(i).getRecordType(), "Irregular heart beat alert");
                }
            }
        }
    }
    // task 5
    public void triggerManualAlert(int patientId)
    {
        triggerAlert(new Alert(String.valueOf(patientId), "Manual alert triggered", System.currentTimeMillis()));
    }
}
