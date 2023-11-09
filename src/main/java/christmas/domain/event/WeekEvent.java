package christmas.domain.event;

import christmas.domain.order.Order;

public class WeekEvent extends Event {
    private static final String WEEKEND_TITLE = "주말 할인";
    private static final String WEEKDAY_TITLE = "평일 할인";
    private static final int ONCE_AMOUNT = 2023;

    @Override
    boolean isUnavailable(final Order order) {
        return false;
    }

    @Override
    int calculateAmount(final Order order) {
        return order.countWeekEventMenu() * ONCE_AMOUNT;
    }

    @Override
    String getTitle(final Order order) {
        if (order.isWeekend()) {
            return WEEKEND_TITLE;
        }

        return WEEKDAY_TITLE;
    }
}
