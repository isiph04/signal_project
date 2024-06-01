package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {
    private int priority;

    public PriorityAlertDecorator(AlertStrategy decoratedAlert, int priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    @Override
    public boolean checkAlert(double value) {
        if (super.checkAlert(value)) {
            System.out.println("Priority Alert! Level: " + priority);
            return true;
        }
        return false;
    }
}
