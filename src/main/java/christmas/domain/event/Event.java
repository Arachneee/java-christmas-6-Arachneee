package christmas.domain.event;

import christmas.domain.order.Order;

public abstract class Event {

    private static final int MIN_ORDER_PRICE = 10_000;

    public int apply(final Order order) {
        if (order.isTotalPriceUnder(MIN_ORDER_PRICE)) {
            return 0;
        }

        if (isUnavailable(order)) {
            return 0;
        }

        return calculateAmount(order);
    }


    abstract boolean isUnavailable(final Order order);

    abstract int calculateAmount(final Order order);

}
