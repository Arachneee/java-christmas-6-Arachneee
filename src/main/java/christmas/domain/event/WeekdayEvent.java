package christmas.domain.event;

import static christmas.constant.Category.DESSERT;

import christmas.domain.order.Order;

public class WeekdayEvent extends Event {
    private static final int ONCE_AMOUNT = 2023;

    @Override
    boolean isUnavailable(Order order) {
        return order.isWeekend();
    }

    @Override
    int calculateAmount(Order order) {
        return order.countMenu(DESSERT) * ONCE_AMOUNT;
    }
}
