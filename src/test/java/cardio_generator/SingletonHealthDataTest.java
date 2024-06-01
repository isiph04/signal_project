package cardio_generator;

import com.cardio_generator.HealthDataSimulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class SingletonHealthDataTest {

    @Test
    void testHealthDataSimulatorSingleton() {
        HealthDataSimulator instance1 = HealthDataSimulator.getInstance();
        HealthDataSimulator instance2 = HealthDataSimulator.getInstance();
        assertSame(instance1, instance2, "Instances are not the same. Singleton pattern is not working correctly.");
    }
}
