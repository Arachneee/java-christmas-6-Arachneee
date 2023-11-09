package christmas.domain.event;

import christmas.constant.December;
import christmas.domain.order.Order;
import christmas.domain.order.Day;

public class ChristmasEvent extends Event {

    private static final int OFFSET = 1000;

    private static final int ONCE_AMOUNT = 100;

    private static final Day REFERENCE_DAY = Day.from(December.START_DAY.getDay());
    private static final Day FINAL_DAY = Day.from(December.CHRISTMAS_DAY.getDay());

    @Override
    boolean isUnavailable(final Order order) {
        return order.isDayOverThan(FINAL_DAY);
    }

    @Override
    int calculateAmount(final Order order) {
        return order.calculateDayGap(REFERENCE_DAY) * ONCE_AMOUNT + OFFSET;
    }
}
