package com.alerts;

public class BloodPressureStrategy implements AlertStrategy
{
    @Override
    public boolean checkAlert(double bloodPressure)
    {
        return bloodPressure < 90 || bloodPressure > 140;
    }

}
