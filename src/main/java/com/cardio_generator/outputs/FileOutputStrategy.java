package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A strategy for outputting patient data to files.
 * This class implements the OutputStrategy interface and provides a method to write patient data to files
 */
public class FileOutputStrategy implements OutputStrategy {
    // renamed class to UpperCamelCase
    // renamed the file to match the class

    private String baseDirectory; // renamed member variable to lowerCamelCase BaseDirectory to baseDirectory

    /**
     * An existing map that holds the file paths associated with different data labels.
     */
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); // changed file_map to fileMap as lowerCamelCase

    /**
     * Constructs a new FileOutputStrategy with a specified base directory.
     * @param baseDirectory The base directory where data files will be stored.
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory; // renamed member variable basedirectory to lowerCamelCase BaseDirectory to baseDirectory
    }

    /**
     * Outputs the patient data to a file.
     * The method adds to a file named with the label and writes the patient data along with the timestamp.
     * It handles the creation of directories if they do not exist.
     *
     * @param patientId The unique identifier for the patient.
     * @param timestamp The timestamp of the data.
     * @param label The label associated with the data.
     * @param data The data to be written to the file.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory)); // renamed member variable to lowerCamelCase
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // renamed member variables to lowerCamelCase

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}