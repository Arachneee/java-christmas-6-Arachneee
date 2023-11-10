package christmas;

import christmas.controller.Planner;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        Planner planner = new Planner(new InputView(), new OutputView());
        planner.run();
    }
}
