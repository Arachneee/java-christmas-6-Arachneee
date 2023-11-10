package christmas.controller;

import christmas.controller.dto.DiscountResultsDto;
import christmas.domain.event.constant.EventType;
import christmas.domain.order.Order;
import christmas.domain.result.DiscountResults;
import christmas.view.InputView;
import christmas.view.OutputView;

public class PlannerMainController {

    private final InputView inputView;
    private final OutputView outputView;

    public PlannerMainController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printHello();

        final Order order = new OrderController(inputView, outputView).createOrder();

        final DiscountResults discountResults = EventType.discountAll(order);

        outputView.printAllResults(DiscountResultsDto.of(discountResults, order.calculateTotalPrice()));
    }


}
