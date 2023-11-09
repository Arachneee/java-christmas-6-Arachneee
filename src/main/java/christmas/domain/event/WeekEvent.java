package christmas.domain.event;

import christmas.domain.order.Order;

public class WeekEvent extends Event {
    private static final int ONCE_AMOUNT = 2023;

    @Override
    boolean isUnavailable(final Order order) {
        return false;
    }

    @Override
    int calculateAmount(final Order order) {
        return order.countWeekEventMenu() * ONCE_AMOUNT;
    }
}
