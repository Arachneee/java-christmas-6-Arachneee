package christmas.config;

import christmas.controller.PlannerController;
import christmas.domain.event.discount.DiscountRepository;
import christmas.domain.event.gift.GiftRepository;
import christmas.service.event.DiscountService;
import christmas.service.event.EventDetailService;
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

    private static EventDetailService getEventService() {
        return new EventDetailService(getDiscountService(), getGiftService());
    }

    private static DiscountService getDiscountService() {
        return new DiscountService(new DiscountRepository());
    }

    private static GiftService getGiftService() {
        return new GiftService(new GiftRepository());
    }
}
