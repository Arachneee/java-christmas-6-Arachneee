package christmas.domain.discount;

import christmas.domain.order.Order;

public class SpecialDiscount extends Discount {

    private static final int ONCE_AMOUNT = 1000;

    @Override
    boolean isUnavailable(Order order) {
        return !order.isStarDay();
    }

    @Override
    int calculateAmount(Order order) {
        return ONCE_AMOUNT;
    }
}
