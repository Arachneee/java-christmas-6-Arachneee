package christmas.constant;

import static christmas.constant.Category.DESSERT;
import static christmas.constant.Category.MAIN;

import christmas.domain.order.OrderDay;
import java.util.List;

public enum Week {
    WEEKDAY(DESSERT),
    WEEKEND(MAIN);

    private static final int WEEK_COUNT = 7;
    private static final List<Integer> WEEKEND_NUMBER = List.of(1, 2);
    private final Category eventCategory;


    Week(Category category) {
        this.eventCategory = category;
    }

    public static Week from(final OrderDay orderDay) {
        if (WEEKEND_NUMBER.contains(orderDay.mod(WEEK_COUNT))) {
            return WEEKEND;
        }

        return WEEKDAY;
    }

    public boolean isWeekend() {
        return this.equals(Week.WEEKEND);
    }

    public Category getEventCategory() {
        return eventCategory;
    }
}
