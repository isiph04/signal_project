package alerts;

import com.alerts.AlertGenerator;
import com.cardio_generator.generators.HealthDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AlertGeneratorTest {
    // Task 1 Test Cases
    @Test
    public void testIncreasingTrendAlert() {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        dataStorage.addPatientData(1, 100.00, "BloodPressure", System.currentTimeMillis() - 3000);
        dataStorage.addPatientData(1, 110.00, "BloodPressure", System.currentTimeMillis() - 2000);
        dataStorage.addPatientData(1, 120.00, "BloodPressure", System.currentTimeMillis() - 1000);

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Generate an alert for the patient
        alertGenerator.trendAlert(dataStorage.getRecords(1, System.currentTimeMillis() - 60000, System.currentTimeMillis()));
    }

    @Test
    public void testDecreasingTrendAlert() {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        dataStorage.addPatientData(1, 120.00, "BloodPressure", System.currentTimeMillis() - 3000);
        dataStorage.addPatientData(1, 110.00, "BloodPressure", System.currentTimeMillis() - 2000);
        dataStorage.addPatientData(1, 100.00, "BloodPressure", System.currentTimeMillis() - 1000);

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Generate an alert for the patient
        alertGenerator.trendAlert(dataStorage.getRecords(1, System.currentTimeMillis() - 60000, System.currentTimeMillis()));
    }
    //DO NOT PUSH THIS FILE TO GITHUB
    @Test
    public void testCriticalThresholdsAlert() {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        dataStorage.addPatientData(1, 181.0, "SystolicPressure", System.currentTimeMillis());
        dataStorage.addPatientData(1, 121.0, "DiastolicPressure", System.currentTimeMillis());
        dataStorage.addPatientData(1, 89.0, "SystolicPressure", System.currentTimeMillis());
        dataStorage.addPatientData(1, 59.0, "DiastolicPressure", System.currentTimeMillis());

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Mock the OutputStrategy
        OutputStrategy outputStrategy = mock(OutputStrategy.class);

        // Generate an alert for the patient
        alertGenerator.thresholdAlert(dataStorage.getRecords(1, System.currentTimeMillis() - 60000, System.currentTimeMillis()), outputStrategy);

        // Verify that the output method was called with the correct parameters
        verify(outputStrategy).output(eq(1), anyLong(), eq("SystolicPressure"), eq("Critically high systolic blood pressure alert"));
        verify(outputStrategy).output(eq(1), anyLong(), eq("DiastolicPressure"), eq("Critically high diastolic blood pressure alert"));
        verify(outputStrategy).output(eq(1), anyLong(), eq("SystolicPressure"), eq("Critically low systolic blood pressure alert"));
        verify(outputStrategy).output(eq(1), anyLong(), eq("DiastolicPressure"), eq("Critically low diastolic blood pressure alert"));
    }

    // Task 2 Test Cases
    @Test
    public void testLowSaturationAlert() {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        dataStorage.addPatientData(1, 91.0, "OxygenSaturation", System.currentTimeMillis());

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Mock the OutputStrategy
        OutputStrategy outputStrategy = mock(OutputStrategy.class);

        // Generate an alert for the patient
        alertGenerator.lowSaturationAlert(dataStorage.getRecords(1, System.currentTimeMillis() - 60000, System.currentTimeMillis()), outputStrategy);

        // Verify that the output method was called with the correct parameters
        verify(outputStrategy).output(eq(1), anyLong(), eq("OxygenSaturation"), eq("Low oxygen saturation alert"));
    }

    // Test for Rapid Drop Alert
    @Test
    public void testRapidDropAlert() {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        long currentTime = System.currentTimeMillis();
        dataStorage.addPatientData(1, 100.0, "OxygenSaturation", currentTime - 600000); // 10 minutes ago
        dataStorage.addPatientData(1, 94.0, "OxygenSaturation", currentTime); // Now

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Mock the OutputStrategy
        OutputStrategy outputStrategy = mock(OutputStrategy.class);

        // Generate an alert for the patient
        alertGenerator.rapidDropAlert(dataStorage.getRecords(1, currentTime - 600000, currentTime), outputStrategy);

        // Verify that the output method was called with the correct parameters
        verify(outputStrategy).output(eq(1), anyLong(), eq("OxygenSaturation"), eq("Rapid drop in oxygen saturation alert"));
    }

    /// test for task 3
    @Test
    public void testHypotensiveHypoxemiaAlert()
    {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        long currentTime = System.currentTimeMillis();
        dataStorage.addPatientData(1, 89.0, "SystolicPressure", currentTime - 600000); // 10 minutes ago
        dataStorage.addPatientData(1, 91.0, "OxygenSaturation", currentTime); // Now

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Mock the OutputStrategy
        OutputStrategy outputStrategy = mock(OutputStrategy.class);

        // Generate an alert for the patient
        alertGenerator.hypotensiveHypoxemiaAlert(dataStorage.getRecords(1, currentTime - 600000, currentTime), outputStrategy);

        // Verify that the output method was called with the correct parameters
        verify(outputStrategy).output(eq(1), anyLong(), eq("HypotensiveHypoxemia"), eq("Hypotensive hypoxemia alert"));
    }

    // task 4
    @Test
    public void testAbnormalHeartRateAlert() {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        dataStorage.addPatientData(1, 45.0, "HeartRate", System.currentTimeMillis() - 3000);
        dataStorage.addPatientData(1, 105.0, "HeartRate", System.currentTimeMillis() - 2000);

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Mock the OutputStrategy
        OutputStrategy outputStrategy = mock(OutputStrategy.class);

        // Generate an alert for the patient
        alertGenerator.abnormalHeartRateAlert(dataStorage.getRecords(1, System.currentTimeMillis() - 60000, System.currentTimeMillis()), outputStrategy);

        // Verify that the output method was called with the correct parameters
        verify(outputStrategy, times(2)).output(eq(1), anyLong(), eq("HeartRate"), eq("Abnormal heart rate alert"));
    }

    @Test
    public void testIrregularBeatAlert() {
        // Create a real DataStorage
        DataStorage dataStorage = new DataStorage();

        // Add the patient records to the data storage
        dataStorage.addPatientData(1, 0.8, "HeartBeat", System.currentTimeMillis() - 3000);
        dataStorage.addPatientData(1, 1.0, "HeartBeat", System.currentTimeMillis() - 2000);
        dataStorage.addPatientData(1, 0.7, "HeartBeat", System.currentTimeMillis() - 1000);

        // Create an AlertGenerator with the real DataStorage
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);

        // Mock the OutputStrategy
        OutputStrategy outputStrategy = mock(OutputStrategy.class);

        // Generate an alert for the patient
        alertGenerator.irregularBeatAlert(dataStorage.getRecords(1, System.currentTimeMillis() - 60000, System.currentTimeMillis()), outputStrategy);

        // Verify that the output method was called with the correct parameters
        verify(outputStrategy, times(2)).output(eq(1), anyLong(), eq("HeartBeat"), eq("Irregular heart beat alert"));
    }
    // task 5
    @Test
    public void testManualTriggeredAlert() {
        // Create an AlertGenerator
        AlertGenerator alertGenerator = mock(AlertGenerator.class);

        // Create a HealthDataGenerator with the mocked AlertGenerator and a patient ID
        HealthDataGenerator healthDataGenerator = new HealthDataGenerator(alertGenerator, 1);

        // Simulate the pressing of the alert button
        healthDataGenerator.alertButtonPressed();

        // Verify that the triggerManualAlert method was called with the correct patient ID
        verify(alertGenerator).triggerManualAlert(1);
    }
}