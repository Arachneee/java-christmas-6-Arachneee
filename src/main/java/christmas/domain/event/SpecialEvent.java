package christmas.domain.event;

import christmas.domain.order.Order;

public class SpecialEvent extends Event {

    private static final int ONCE_AMOUNT = 1_000;

    @Override
    boolean isUnavailable(final Order order) {
        return isNotStarDay(order);
    }

    @Override
    int calculateAmount(final Order order) {
        return ONCE_AMOUNT;
    }

    private static boolean isNotStarDay(Order order) {
        return !(order.isSunDay() || order.isChristmasDay());
    }
}
