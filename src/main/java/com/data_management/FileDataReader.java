package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.net.URI;

public class FileDataReader implements DataReader
{
    private final String directory;
    // this string is only accessible in this class
    // this string can only be set once and cannot be changed

    public FileDataReader(String directory) // a public contructor and takes the "directory" string as a parameter
    {
        this.directory = directory;
    }

    @Override
    // overides readData method from DataReader interface
    public void readData(DataStorage dataStorage)
    {
        File direct = new File(directory); // creates a new file object and indicates the directory
        File[] files = direct.listFiles((d, name) -> name.endsWith(".txt"));
        // creates a new file array and lists all the files in the directory that ends with ".txt"
        // stores all these files in the array "files"
        //listFiles() method takes the name of the files as a parameter

        if (files != null) // if the directory doesn't exist or there is an error
        {
            for (File file : files) // for each file in the files array
            {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) // try to read the file
                {
                    String lineOfText;
                    while ((lineOfText = br.readLine()) != null) // while there is a line of text to read
                    {
                        String[] dataCollected = lineOfText.split(","); // split the line of text by commas
                        int patientId = Integer.parseInt(dataCollected[0].trim()); // parse the first element of the array as an integer and store it in the variable "patientId
                        long timestamp = Long.parseLong(dataCollected[1].trim()); // parse the second element of the array as a long and store it in the variable "timestamp"
                        String label = dataCollected[2].trim(); // store the third element of the array in the variable "label"
                        double dataValue = Double.parseDouble(dataCollected[3].trim()); // parse the fourth element of the array as a double and store it in the variable "dataCollected"
                        dataStorage.addPatientData(patientId, dataValue, label, timestamp);
                        // add the dataCollected to the dataCollected storage
                        // calls the addPatientData method from the DataStorage class
                    }
                }
                catch (IOException e) // catch any exceptions
                {
                    System.err.println("Error when reading the file: " + e.getMessage()); // print the stack trace
                }
            }
        }
    }

        @Override
        public void connect(URI serverUri) throws IOException
        {
            //returns nothing
        }

        @Override
        public void onMessage(String message)
        {
            //returns nothing
        }

}
