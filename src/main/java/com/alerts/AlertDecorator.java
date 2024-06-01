package com.alerts;

public abstract class AlertDecorator implements AlertStrategy {
    protected AlertStrategy decoratedAlert;

    public AlertDecorator(AlertStrategy decoratedAlert) {
        this.decoratedAlert = decoratedAlert;
    }

    public boolean checkAlert(double value) {
        return decoratedAlert.checkAlert(value);
    }
}
