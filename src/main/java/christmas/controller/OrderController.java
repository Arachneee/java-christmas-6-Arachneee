package christmas.controller;

import christmas.controller.convertor.OrderConvertor;
import christmas.domain.order.Day;
import christmas.domain.order.Order;
import christmas.response.OrderSummaryResponse;
import christmas.service.order.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.function.Supplier;

public class OrderController {

    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public OrderController(final InputView inputView, final OutputView outputView, final OrderService orderService) {
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

        inputView.close();
    }

    private Day createOrderDay() {
        return getInputByRoof(() -> OrderConvertor.convertToDay(inputView.readDate()));
    }

    private Order createOrder(final Day day) {
        return getInputByRoof(() -> Order.of(day, OrderConvertor.convertToMenu(inputView.readMenuAndCount())));
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
