package christmas.controller;

import christmas.domain.day.Day;
import christmas.domain.event.constant.EventType;
import christmas.domain.order.Order;
import christmas.domain.result.DiscountResults;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Planner {

    private final InputView inputView;
    private final OutputView outputView;

    public Planner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printHello();

        Day day = createOrderDay();
        Order order = createOrderMenu(day);

        outputView.printOrder(OrderDto.from(order));

        int totalPrice = order.calculateTotalPrice();


        DiscountResults discountResults = EventType.discountAll(order);

        int discountPrice = discountResults.calculateTotalDiscount();
        int afterDiscountPayment = discountResults.calculateAfterDiscountPayment(totalPrice);

        outputView.printDiscountResults(DiscountResultsDto.of(totalPrice, discountResults));
    }

    private Order createOrderMenu(final Day day) {
        while (true) {
            try {
                return Order.of(day, inputView.readDate());
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.printError(illegalArgumentException.getMessage());
            }
        }
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
}
