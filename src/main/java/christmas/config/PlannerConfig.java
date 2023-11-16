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

    public static OrderController orderController() {
        return new OrderController(inputView(), outputView(), orderService());
    }

    private static InputView inputView() {
        return new InputView(new ConsoleReader(), new ConsoleWriter());
    }

    private static OutputView outputView() {
        return new OutputView(new ConsoleWriter());
    }

    private static OrderService orderService() {
        return new OrderService(eventDetailService());
    }

    private static EventDetailService eventDetailService() {
        return new EventDetailService(discountService(), giftService());
    }

    private static DiscountService discountService() {
        return new DiscountService(new DiscountRepository());
    }

    private static GiftService giftService() {
        return new GiftService(new GiftRepository());
    }
}
