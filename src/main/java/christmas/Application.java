package christmas;

import christmas.config.PlannerConfig;
import christmas.controller.OrderController;

public class Application {

    public static void main(String[] args) {
        final OrderController orderController = PlannerConfig.getOrderController();
        orderController.run();
    }
}
