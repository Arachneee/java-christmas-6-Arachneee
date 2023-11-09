package christmas.constant;

import christmas.domain.order.OrderDay;
import java.util.List;

public enum WEEK {
    WEEKDAY,
    WEEKEND;

    private static final int WEEK_COUNT = 7;
    private static final List<Integer> WEEKEND_NUMBER = List.of(1, 2);

    public static WEEK from(final OrderDay orderDay) {
        if (WEEKEND_NUMBER.contains(orderDay.mod(WEEK_COUNT))) {
            return WEEKEND;
        }

        return WEEKDAY;
    }

    public boolean isWeekend() {
        return this.equals(WEEK.WEEKEND);
    }
}
