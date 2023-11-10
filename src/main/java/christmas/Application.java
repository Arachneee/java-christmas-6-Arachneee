package christmas;

import christmas.controller.PlannerController;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        PlannerController plannerController = new PlannerController(new InputView(), new OutputView());
        plannerController.run();
    }
}
