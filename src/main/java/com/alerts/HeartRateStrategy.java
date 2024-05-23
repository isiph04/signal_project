package com.alerts;

public class HeartRateStrategy implements AlertStrategy
{
    @Override
    public boolean checkAlert(double heartRate)
    {
        return heartRate < 60 || heartRate > 100;
    }

}
