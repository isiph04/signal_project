package data_management;

import com.data_management.DataReader;
import com.data_management.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataReaderTest {
    private DataReader dataReader;

    @Mock
    private DataStorage dataStorage;

    @BeforeEach
    public void setup() {
        // Initialize the data reader
        MockitoAnnotations.openMocks(this);
        dataReader = mock(DataReader.class);
    }

    @Test
    public void testReadData_ValidData() throws IOException {
        // Test the data reader with valid data
        doNothing().when(dataReader).readData(dataStorage);
        dataReader.readData(dataStorage);
        verify(dataReader, times(1)).readData(dataStorage);
    }

    @Test
    public void testReadData_IOException() throws IOException {
        // Test the data reader with an IOException
        doThrow(IOException.class).when(dataReader).readData(dataStorage);
        assertThrows(IOException.class, () -> dataReader.readData(dataStorage));
    }

    @Test
    public void testConnect_ValidURI() throws IOException, URISyntaxException {
        // Test the data reader with a valid URI
        URI validUri = new URI("http://localhost:8887");
        doNothing().when(dataReader).connect(validUri);
        dataReader.connect(validUri);
        verify(dataReader, times(1)).connect(validUri);
    }

    @Test
    public void testConnect_InvalidURI() {
        // Test the data reader with an invalid URI
        assertThrows(URISyntaxException.class, () -> {
            URI invalidUri = new URI("invalid uri");
        });
    }
}
