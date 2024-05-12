package com.cardio_generator.generators;

import com.alerts.AlertGenerator;

public class HealthDataGenerator
{
    private AlertGenerator alertGenerator;
    private int patientId;

    public HealthDataGenerator(AlertGenerator alertGenerator, int patientId)
    {
        this.alertGenerator = alertGenerator;
        this.patientId = patientId;
    }

    public void alertButtonPressed()
    {
        alertGenerator.triggerManualAlert(patientId);
    }
}
