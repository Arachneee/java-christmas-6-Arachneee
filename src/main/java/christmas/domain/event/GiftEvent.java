package christmas.domain.event;

import static christmas.domain.order.constant.Menu.CHAMPAGNE;

import christmas.domain.order.Order;

public class GiftEvent extends Event {

    private static final int MIN_TOTAL_PRICE = 120_000;

    @Override
    boolean isUnavailable(final Order order) {
        return order.isTotalPriceUnder(MIN_TOTAL_PRICE);
    }

    @Override
    int calculateAmount(final Order order) {
        return CHAMPAGNE.getPrice();
    }
}