package alerts;

import com.alerts.AlertStrategy;
import com.alerts.OxygenSaturationStrategy;
import com.alerts.PriorityAlertDecorator;
import com.alerts.RepeatedAlertDecorator;

public class AlertDecoratorTest {
    public static void main(String[] args) {
        AlertStrategy baseAlert = new OxygenSaturationStrategy();

        // Decorate the base alert with RepeatedAlertDecorator
        AlertStrategy repeatedAlert = new RepeatedAlertDecorator(baseAlert, 3);
        System.out.println("Repeated Alert Result: " + repeatedAlert.checkAlert(90.0));

        // Decorate the base alert with PriorityAlertDecorator
        AlertStrategy priorityAlert = new PriorityAlertDecorator(baseAlert, 1);
        System.out.println("Priority Alert Result: " + priorityAlert.checkAlert(90.0));

        // Decorate the base alert with RepeatedAlertDecorator and then with PriorityAlertDecorator
        AlertStrategy combinedAlert = new PriorityAlertDecorator(new RepeatedAlertDecorator(baseAlert, 3), 1);
        System.out.println("Combined Alert Result: " + combinedAlert.checkAlert(90.0));
    }
}
