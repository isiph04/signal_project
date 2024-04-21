package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating patient data.
 * This interface represents a contract for generating patient data based on a specified output strategy.
 */

public interface PatientDataGenerator {

    /**
     * Generated data for a specified patient ID and outputs it using the specified output strategy.
     *
     * @param patientId The unique identifier for the patient.
     * @param outputStrategy The strategy used to output the generated patient data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
