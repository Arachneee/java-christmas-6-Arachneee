package christmas.domain.day.constant;

import static christmas.domain.day.constant.Week.WEEKDAY;
import static christmas.domain.day.constant.Week.WEEKEND;
import static christmas.exception.constant.ErrorMessage.INVALID_DAY;

import christmas.domain.day.Day;
import christmas.exception.OrderException;
import java.util.Arrays;

public enum DayOfWeek {
    FRIDAY(1, WEEKEND),
    SATURDAY(2, WEEKEND),
    SUNDAY(3, WEEKDAY),
    MONDAY(4, WEEKDAY),
    TUESDAY(5, WEEKDAY),
    WEDNESDAY(6, WEEKDAY),
    THURSDAY(0, WEEKDAY);

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
        return this.week.equals(WEEKEND);
    }
}
