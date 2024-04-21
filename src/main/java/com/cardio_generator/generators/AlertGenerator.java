package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates alert signals for patients.
 * This class is responsible for simulating the occurrence of alerts based on random probability.
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private final boolean[] alertStates; // false = resolved, true = pressed
    // AlertStates to alertStates to lowerCamelCase

    /**
     * Constructs a new AlertGenerator with the specified number of patients.
     *
     * @param patientCount The number of patients to monitor for alerts.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    } // AlertStates to alertStates --> lowerCamelCase

    /**
     * Generates alert data for a specified patient and outputs it using a specified output strategy.
     *
     * @param patientId The unique identifier for the patient.
     * @param outputStrategy The strategy used to output the generated patient data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) // AlertStates to alertStates --> lowerCamelCase
                {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false; // AlertStates to alertStates --> lowerCamelCase
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true; // AlertStates to alertStates --> lowerCamelCase
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
