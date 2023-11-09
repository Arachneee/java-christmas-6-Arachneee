package christmas.domain.discount;

import christmas.domain.order.Order;
import christmas.domain.order.OrderDay;

public class ChristmasDiscount extends Discount {

    private static final int OFFSET = 1000;

    private static final int ONCE_AMOUNT = 100;

    private static final int REFERENCE_DAY = 1;
    private static final int CHRISTMAS_DAY = 25;

    @Override
    boolean isUnavailable(Order order) {
        return order.isDayOverThan(OrderDay.from(CHRISTMAS_DAY));
    }

    @Override
    int calculateAmount(final Order order) {
        return order.calculateDayGap(OrderDay.from(REFERENCE_DAY)) * ONCE_AMOUNT + OFFSET;
    }
}
