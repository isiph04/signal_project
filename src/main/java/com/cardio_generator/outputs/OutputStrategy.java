package com.cardio_generator.outputs;

/**
 * Interface for output strategies in the cardio generator system.
 */
public interface OutputStrategy {

    /**
     * Outputs the patient data.
     *
     * @param patientId The unique identifier for the patient.
     * @param timestamp The timestamp associated with the data.
     * @param label The label associated with the data.
     * @param data The actual data to be outputted.
     */
    void output(int patientId, long timestamp, String label, String data);
}
