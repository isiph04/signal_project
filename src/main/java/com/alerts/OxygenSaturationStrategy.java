package com.alerts;

public class OxygenSaturationStrategy implements AlertStrategy
{
    @Override
    public boolean checkAlert(double oxygenSaturation)
    {
        return oxygenSaturation < 95;
    }
}
