package alerts;

import com.alerts.HeartRateStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeartRateStrategyTest
{
    @Test
    public void testCheckAlertHighHeartRate()
    {
        HeartRateStrategy heartRateStrategy = new HeartRateStrategy();
        assertTrue(heartRateStrategy.checkAlert(105));
        assertTrue(heartRateStrategy.checkAlert(57));
        assertFalse(heartRateStrategy.checkAlert(100));
        assertFalse(heartRateStrategy.checkAlert(60));
    }

    @Test
    public void testCheckAlertLowHeartRate()
    {
        HeartRateStrategy heartRateStrategy = new HeartRateStrategy();
        assertTrue(heartRateStrategy.checkAlert(105));
        assertTrue(heartRateStrategy.checkAlert(57));
        assertFalse(heartRateStrategy.checkAlert(85));
        assertFalse(heartRateStrategy.checkAlert(95));
    }

    @Test
    public void testCheckAlertNormalHeartRate()
    {
        HeartRateStrategy heartRateStrategy = new HeartRateStrategy();
        assertTrue(heartRateStrategy.checkAlert(102));
        assertTrue(heartRateStrategy.checkAlert(58));
        assertFalse(heartRateStrategy.checkAlert(80));
    }
}
