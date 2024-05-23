package alerts;

import com.alerts.*;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class AlertFactoryTest

{
    @Test
    public void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("1", "High Blood Pressure", System.currentTimeMillis());

        assertInstanceOf(BloodPressureAlert.class, alert);
        assertEquals("1", alert.getPatientId());
        assertEquals("High Blood Pressure", alert.getCondition());
    }

    @Test
    public void testBloodOxygenAlertFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("1", "Low Oxygen Level", System.currentTimeMillis());

        assertInstanceOf(BloodOxygenAlert.class, alert);
        assertEquals("1", alert.getPatientId());
        assertEquals("Low Oxygen Level", alert.getCondition());
    }

    @Test
    public void testECGAlertFactory() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("1", "Irregular Heartbeat", System.currentTimeMillis());

        assertInstanceOf(ECGAlert.class, alert);
        assertEquals("1", alert.getPatientId());
        assertEquals("Irregular Heartbeat", alert.getCondition());
    }
}

