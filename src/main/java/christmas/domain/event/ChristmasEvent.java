package christmas.domain.event;

import static christmas.domain.day.December.CHRISTMAS_DAY;
import static christmas.domain.day.December.START_DAY;

import christmas.domain.day.Day;
import christmas.domain.order.Order;

public class ChristmasEvent extends Event {

    private static final int OFFSET = 1000;

    private static final int ONCE_AMOUNT = 100;

    private static final Day REFERENCE_DAY = Day.from(START_DAY.getDay());
    private static final Day FINAL_DAY = Day.from(CHRISTMAS_DAY.getDay());

    @Override
    boolean isUnavailable(final Order order) {
        return order.isDayOverThan(FINAL_DAY);
    }

    @Override
    int calculateAmount(final Order order) {
        return order.calculateDayGap(REFERENCE_DAY) * ONCE_AMOUNT + OFFSET;
    }
}
