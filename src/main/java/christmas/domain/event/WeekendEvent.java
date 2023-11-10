package christmas.domain.event;

import christmas.domain.order.Order;

public class WeekendEvent extends Event {

    private static final int ONCE_AMOUNT = 2_023;

    @Override
    boolean isUnavailable(final Order order) {
        return !order.isWeekend();
    }

    @Override
    int calculateAmount(final Order order) {
        return order.countWeekEventMenu() * ONCE_AMOUNT;
    }
}
