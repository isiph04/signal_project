package data_management;

import com.data_management.dataWebSocketClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.net.URI;
import java.net.URISyntaxException;


public class dataWebSocketClientTest {
    private dataWebSocketClient client;

    @BeforeEach
    public void setup() throws URISyntaxException {
        // Initialize the client
        MockitoAnnotations.openMocks(this);
        client = new dataWebSocketClient(new URI("ws://localhost:8887"));
    }

    @Test
    public void testOnMessage_ValidData() {
        // Test the client with valid data
        String validData = "1, 1627554492000, HeartBeat, 0.8";
        client.onMessage(validData);
    }

    @Test
    public void testOnMessage_InvalidData() {
        // Test the client with invalid data
        String invalidData = "invalid data";
        client.onMessage(invalidData);
    }

    @Test
    public void testOnMessage_UnparsableData() {
        // Test the client with unparsable data
        String unparsableData = "1, unparsable, HeartBeat, 0.8";
        client.onMessage(unparsableData);
    }

    @Test
    public void testOnError_NetworkError() {
        // Test the client with a network error.
        Exception exception = new Exception("Network error");
        client.onError(exception);

    }
}
