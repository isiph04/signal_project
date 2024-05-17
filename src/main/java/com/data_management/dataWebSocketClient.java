package com.data_management;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class dataWebSocketClient extends WebSocketClient
{
    public dataWebSocketClient(URI serverUri)
    {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata)
    {
        System.out.println("Connected was opened");
    }

    @Override
    public void onMessage(String message)
    {
        System.out.println("Message received");
        String[] dataCollected = message.split(",");
        if (dataCollected.length != 4)
        {
            System.err.println("Message has invalid format");
        }
        try
        {
            int patientId = Integer.parseInt(dataCollected[0].trim());
            long timestamp = Long.parseLong(dataCollected[1].trim());
            String label = dataCollected[2].trim();
            double dataValue = Double.parseDouble(dataCollected[3].trim());
        }
        catch (NumberFormatException e)
        {
            System.err.println("Error when parsing the message");
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
        System.err.println("An error occurred: " + ex.getMessage());
    }

    public static void main(String[] args) throws URISyntaxException
    {
       WebSocketClient client = new dataWebSocketClient(new URI ("ws://localhost:8887"));
       client.connect();
    }

}
