package christmas;

import christmas.config.PlannerConfig;
import christmas.controller.PlannerController;

public class Application {

    public static void main(String[] args) {
        final PlannerController plannerController = PlannerConfig.getPlannerController();
        plannerController.run();;
    }
}
