package christmas.config;

import christmas.controller.OrderController;
import christmas.domain.event.discount.DiscountRepository;
import christmas.domain.event.gift.GiftRepository;
import christmas.service.event.DiscountService;
import christmas.service.event.EventDetailService;
import christmas.service.event.GiftService;
import christmas.service.order.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.io.ConsoleReader;
import christmas.view.io.ConsoleWriter;

public class PlannerConfig {

    private PlannerConfig() {
    }

    public static OrderController getOrderController() {
        return new OrderController(getInputView(), getOutputView(), getOrderService());
    }

    private static InputView getInputView() {
        return new InputView(new ConsoleReader(), new ConsoleWriter());
    }

    private static OutputView getOutputView() {
        return new OutputView(new ConsoleWriter());
    }

    private static OrderService getOrderService() {
        return new OrderService(getEventDetailService());
    }

    private static EventDetailService getEventDetailService() {
        return new EventDetailService(getDiscountService(), getGiftService());
    }

    private static DiscountService getDiscountService() {
        return new DiscountService(new DiscountRepository());
    }

    private static GiftService getGiftService() {
        return new GiftService(new GiftRepository());
    }
}
