package christmas.domain.order.constant;

import static christmas.domain.order.constant.DayOfWeek.FRIDAY;
import static christmas.domain.order.constant.DayOfWeek.MONDAY;
import static christmas.domain.order.constant.DayOfWeek.SUNDAY;

public enum December {
    START_DAY(1, FRIDAY),
    CHRISTMAS_DAY(25, MONDAY),
    END_DAY(31, SUNDAY);

    private final int day;
    private final DayOfWeek dayOfWeek;

    December(final int day, final DayOfWeek dayOfWeek) {
        this.day = day;
        this.dayOfWeek = dayOfWeek;
    }

    public static boolean isInRange(final int day) {
        return day >= START_DAY.getDay() && day <= END_DAY.getDay();
    }

    public static boolean isChristMas(final int day) {
        return day == CHRISTMAS_DAY.getDay();
    }

    public int getDay() {
        return day;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
