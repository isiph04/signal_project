package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int interval;

    public RepeatedAlertDecorator(AlertStrategy decoratedAlert, int interval) {
        super(decoratedAlert);
        this.interval = interval;
    }

    @Override
    public boolean checkAlert(double value) {
        for (int i = 0; i < interval; i++) {
            if (super.checkAlert(value)) {
                return true;
            }
        }
        return false;
    }
}
