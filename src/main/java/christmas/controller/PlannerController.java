package christmas.controller;

import static christmas.controller.convertor.MenuConvertor.convertToEnumMap;

import christmas.controller.dto.DiscountsDto;
import christmas.controller.dto.OrderDto;
import christmas.domain.day.Day;
import christmas.domain.discount.Discounts;
import christmas.domain.event.constant.EventType;
import christmas.domain.order.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.function.Supplier;

public class PlannerController {

    private final InputView inputView;
    private final OutputView outputView;

    public PlannerController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printHello();

        final Day orderDay = createOrderDay();
        final Order order = createOrder(orderDay);
        outputView.printAllOrder(OrderDto.from(order));

        final Discounts discounts = EventType.discountAll(order);
        outputView.printAllDiscountResults(DiscountsDto.from(discounts));
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