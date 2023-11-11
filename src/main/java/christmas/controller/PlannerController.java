package christmas.controller;

import static christmas.controller.convertor.MenuConvertor.convertToEnumMap;

import christmas.domain.order.day.Day;
import christmas.domain.order.Order;
import christmas.response.OrderSummaryResponse;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.function.Supplier;

public class PlannerController {

    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public PlannerController(final InputView inputView, final OutputView outputView, final OrderService orderService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
    }

    public void run() {
        outputView.printHello();

        final Day orderDay = createOrderDay();
        final Order order = createOrder(orderDay);

        final OrderSummaryResponse orderSummaryResponse = orderService.createOrderSummary(order);

        outputView.printOrderSummary(orderSummaryResponse);
    }

    private Day createOrderDay() {
        return getInputByRoof(() -> Day.from(inputView.readDate()));
    }

    private Order createOrder(final Day day) {
        return getInputByRoof(() -> Order.of(day, convertToEnumMap(inputView.readMenuAndCount())));
    }

    private <T> T getInputByRoof(final Supplier<T> method) {
        while (true) {
            try {
                return method.get();
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.printError(illegalArgumentException.getMessage());
            }
        }
    }

}
