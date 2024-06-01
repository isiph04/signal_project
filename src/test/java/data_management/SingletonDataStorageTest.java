package data_management;

import com.data_management.DataStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class SingletonDataStorageTest {

    @Test
    void testDataStorageSingleton() {
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();
        assertSame(instance1, instance2, "Instances are not the same. Singleton pattern is not working correctly.");
    }

}