package alerts;

import com.alerts.BloodPressureStrategy;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BloodPressureStrategyTest
{
    @Test
    public void testCheckAlertHighBloodPressure()
    {
        BloodPressureStrategy bloodPressureStrategy = new BloodPressureStrategy();
        assertTrue(bloodPressureStrategy.checkAlert(155));
        assertFalse(bloodPressureStrategy.checkAlert(130));
    }

    @Test
    public void testCheckAlertLowBloodPressure()
    {
        BloodPressureStrategy bloodPressureStrategy = new BloodPressureStrategy();
        assertTrue(bloodPressureStrategy.checkAlert(85));
        assertFalse(bloodPressureStrategy.checkAlert(100));
    }

    @Test
    public void testCheckAlertNormalBloodPressure()
    {
        BloodPressureStrategy bloodPressureStrategy = new BloodPressureStrategy();
        assertTrue(bloodPressureStrategy.checkAlert(145));
        assertFalse(bloodPressureStrategy.checkAlert(120));
    }

}
