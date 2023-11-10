package christmas;

import christmas.controller.PlannerMainController;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        PlannerMainController plannerMainController = new PlannerMainController(new InputView(), new OutputView());
        plannerMainController.run();
    }
}
