package christmas;

import static christmas.config.PlannerConfig.orderController;

public class Application {

    public static void main(String[] args) {
        orderController().run();
    }
}
