package com.data_management;

import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;

public class WebSocketDataReader extends dataWebSocketClient implements DataReader
{
    //files are read locally
    // websocket is connected to the server
    private DataStorage dataStorage;

    public WebSocketDataReader(URI serverUri, DataStorage dataStorage)
    {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata)
    {
        System.out.println("Connected was opened");
    }

    @Override
    public void onMessage(String message)
    {
        try {
            String[] dataCollected = message.split(",");
            if (dataCollected.length != 4) {
                throw new IllegalArgumentException("Message has invalid format");
            }
            int patientId = Integer.parseInt(dataCollected[0].trim());
            long timestamp = Long.parseLong(dataCollected[1].trim());
            String label = dataCollected[2].trim();
            double dataValue = Double.parseDouble(dataCollected[3].trim());
            dataStorage.addPatientData(patientId, dataValue, label, timestamp);
        } catch (NumberFormatException e) {
            System.err.println("Error when parsing the message: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid message format: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while processing this message: " + e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        System.out.println("Connection was closed");
    }

    @Override
    public void onError(Exception ex)
    {
        ex.printStackTrace();
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {}

    @Override
    public void connect(URI serverUri) throws IOException
    {
        this.connect();
    }
}
