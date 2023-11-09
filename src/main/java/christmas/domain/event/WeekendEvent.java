package christmas.domain.event;

import static christmas.constant.Category.MAIN;

import christmas.domain.order.Order;

public class WeekendEvent extends Event {
    private static final int ONCE_AMOUNT = 2023;

    @Override
    boolean isUnavailable(Order order) {
        return !order.isWeekend();
    }

    @Override
    int calculateAmount(Order order) {
        return order.countMenu(MAIN) * ONCE_AMOUNT;
    }
}
