package christmas.controller;

import christmas.controller.dto.OrderDto;
import christmas.domain.day.Day;
import christmas.domain.order.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.Map;

public class OrderController {
    private final InputView inputView;
    private final OutputView outputView;
    private Map<String, Integer> nameCount;

    public OrderController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Order createOrder() {
        final Day day = createOrderDay();
        final Order order = createOrderMenu(day);

        outputView.printOrder(OrderDto.of(day, nameCount, order.calculateTotalPrice()));

        return order;
    }

    private Day createOrderDay() {
        while (true) {
            try {
                return Day.from(inputView.readDate());
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.printError(illegalArgumentException.getMessage());
            }
        }
    }

    private Order createOrderMenu(final Day day) {
        while (true) {
            try {
                nameCount = inputView.readMenuAndCount();
                return Order.of(day, nameCount);
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.printError(illegalArgumentException.getMessage());
            }
        }
    }

}
