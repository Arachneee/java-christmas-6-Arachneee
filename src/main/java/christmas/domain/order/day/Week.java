package christmas.domain.order.day;

import static christmas.domain.order.menu.Category.DESSERT;
import static christmas.domain.order.menu.Category.MAIN;

import christmas.domain.order.menu.Category;

public enum Week {
    WEEKDAY(DESSERT),
    WEEKEND(MAIN);

    private final Category eventCategory;


    Week(final Category category) {
        this.eventCategory = category;
    }

    public static Week from(final DayOfWeek dayOfWeek) {
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
