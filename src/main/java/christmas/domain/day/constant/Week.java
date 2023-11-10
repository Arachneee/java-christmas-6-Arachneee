package christmas.domain.day.constant;

import static christmas.domain.order.constant.Category.DESSERT;
import static christmas.domain.order.constant.Category.MAIN;

import christmas.domain.day.Day;
import christmas.domain.order.constant.Category;

public enum Week {
    WEEKDAY(DESSERT),
    WEEKEND(MAIN);

    private final Category eventCategory;


    Week(final Category category) {
        this.eventCategory = category;
    }

    public static Week from(final Day day) {
        final DayOfWeek dayOfWeek = DayOfWeek.from(day);

        if (dayOfWeek.isWeekend()) {
            return WEEKEND;
        }

        return WEEKDAY;
    }

    public boolean isWeekend() {
        return this.equals(WEEKEND);
    }

    public Category getEventCategory() {
        return eventCategory;
    }
}