package data_management;

import com.alerts.AlertStrategy;
import com.data_management.DataStorage;
import com.data_management.dataWebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class SystemIntegrationTest {
    private dataWebSocketClient client;
    private DataStorage dataStorage;
    private AlertStrategy alertStrategy;

    @BeforeEach
    public void setup() throws URISyntaxException {
        // Initialize the client, data storage, and alert strategy.
        MockitoAnnotations.openMocks(this);
        client = new dataWebSocketClient(new URI("ws://localhost:8887"));
        dataStorage = new DataStorage();
        alertStrategy = mock(AlertStrategy.class);
    }

    @Test
    public void testSystem_ValidData() {
        // Test the system with valid data
        String validData = "1, 1627554492000, HeartBeat, 0.8";
        client.onMessage(validData);
    }

    @Test
    public void testSystem_InvalidData() {
        // Test the system with invalid data
        String invalidData = "invalid data";
        client.onMessage(invalidData);
    }

    @Test
    public void testSystem_UnparsableData() {
        // Test the system with unparsable data
        String unparsableData = "1, unparsable, HeartBeat, 0.8";
        client.onMessage(unparsableData);
    }
}
