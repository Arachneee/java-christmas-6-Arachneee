package christmas.domain.day.constant;

import static christmas.domain.day.constant.Week.WEEKDAY;
import static christmas.domain.day.constant.Week.WEEKEND;

import christmas.domain.day.Day;
import christmas.exception.constant.ErrorMessage;
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

    private final int modSevenNumber;
    private final Week week;

    DayOfWeek(final int modSevenNumber, final Week week) {
        this.modSevenNumber = modSevenNumber;
        this.week = week;
    }

    public static DayOfWeek from(final Day day) {
        return Arrays.stream(values())
                .filter(dayOfWeek -> isModNumber(day, dayOfWeek))
                .findFirst()
                .orElseThrow(() -> OrderException.from(ErrorMessage.INVALID_DAY));
    }

    private static boolean isModNumber(final Day day, final DayOfWeek dayOfWeek) {
        return dayOfWeek.modSevenNumber == day.mod(totalCount());
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
