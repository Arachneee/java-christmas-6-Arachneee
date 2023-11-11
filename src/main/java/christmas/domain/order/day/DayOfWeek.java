package christmas.domain.order.day;

import static christmas.exception.ErrorMessage.INVALID_DAY;

import christmas.exception.OrderException;
import java.util.Arrays;

public enum DayOfWeek {
    FRIDAY(1, Week.WEEKEND),
    SATURDAY(2, Week.WEEKEND),
    SUNDAY(3, Week.WEEKDAY),
    MONDAY(4, Week.WEEKDAY),
    TUESDAY(5, Week.WEEKDAY),
    WEDNESDAY(6, Week.WEEKDAY),
    THURSDAY(0, Week.WEEKDAY);

    private final int modNumber;
    private final Week week;

    DayOfWeek(final int modNumber, final Week week) {
        this.modNumber = modNumber;
        this.week = week;
    }

    public static DayOfWeek from(final Day day) {
        return Arrays.stream(values())
                .filter(dayOfWeek -> isModNumber(day, dayOfWeek))
                .findFirst()
                .orElseThrow(() -> OrderException.from(INVALID_DAY));
    }

    private static boolean isModNumber(final Day day, final DayOfWeek dayOfWeek) {
        return dayOfWeek.modNumber == day.mod(totalCount());
    }

    private static int totalCount() {
        return (int) Arrays.stream(values()).count();
    }

    public boolean isSunDay() {
        return this.equals(SUNDAY);
    }

    public boolean isWeekend() {
        return this.week.equals(Week.WEEKEND);
    }
}
