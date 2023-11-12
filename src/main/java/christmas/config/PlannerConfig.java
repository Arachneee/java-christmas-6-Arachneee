package christmas.config;

import christmas.controller.PlannerController;
import christmas.service.event.DiscountService;
import christmas.service.event.EventService;
import christmas.service.event.GiftService;
import christmas.service.order.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class PlannerConfig {

    private PlannerConfig() {
    }

    public static PlannerController getPlannerController() {
        return new PlannerController(new InputView(), new OutputView(), getOrderService());
    }

    private static OrderService getOrderService() {
        return new OrderService(getEventService());
    }

    private static EventService getEventService() {
        return new EventService(new DiscountService(), new GiftService());
    }
}
