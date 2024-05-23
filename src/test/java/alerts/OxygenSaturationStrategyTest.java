package alerts;

import com.alerts.OxygenSaturationStrategy;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class OxygenSaturationStrategyTest
{
    @Test
    public void testCheckAlertLowOxygenSaturation()
    {
        OxygenSaturationStrategy oxygenSaturationStrategy = new OxygenSaturationStrategy();
        assertTrue(oxygenSaturationStrategy.checkAlert(85));
        assertFalse(oxygenSaturationStrategy.checkAlert(100));
    }

    @Test
    public void testCheckAlertNormalOxygenSaturation()
    {
        OxygenSaturationStrategy oxygenSaturationStrategy = new OxygenSaturationStrategy();
        assertFalse(oxygenSaturationStrategy.checkAlert(98));
    }
}
