package christmas.domain.day;

import static christmas.domain.order.Category.DESSERT;
import static christmas.domain.order.Category.MAIN;

import christmas.domain.order.Category;

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
        return this.equals(Week.WEEKEND);
    }

    public Category getEventCategory() {
        return eventCategory;
    }
}
