package christmas.domain.event;

import christmas.domain.order.Order;

public class SpecialEvent extends Event {

    private static final int ONCE_AMOUNT = 1000;

    @Override
    boolean isUnavailable(final Order order) {
        return !order.isStarDay();
    }

    @Override
    int calculateAmount(final Order order) {
        return ONCE_AMOUNT;
    }
}
