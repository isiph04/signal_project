package alerts;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;

public class AlertGeneratorTest {
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
}

