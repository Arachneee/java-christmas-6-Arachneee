package christmas.domain.order.day;

import static christmas.domain.order.day.December.START_DAY;
import static christmas.domain.order.day.Week.WEEKDAY;
import static christmas.domain.order.day.Week.WEEKEND;

import java.util.Arrays;

public enum DayOfWeek {
    FRIDAY(0, WEEKEND),
    SATURDAY(1, WEEKEND),
    SUNDAY(2, WEEKDAY),
    MONDAY(3, WEEKDAY),
    TUESDAY(4, WEEKDAY),
    WEDNESDAY(5, WEEKDAY),
    THURSDAY(6, WEEKDAY);

    private static final int TOTAL_COUNT = (int) Arrays.stream(values()).count();
    private final int sequence;
    private final Week week;

    DayOfWeek(final int sequence, final Week week) {
        this.sequence = sequence;
        this.week = week;
    }

    public static DayOfWeek from(final Day day) {
        return START_DAY.getDayOfWeek().getAfterDay(day);
    }

    private DayOfWeek getAfterDay(final Day day) {
        final int mod = day.gapFromStartDay() % TOTAL_COUNT;
        return findDayOfWeekByMod(mod);
    }

    private DayOfWeek findDayOfWeekByMod(final int mod) {
        return Arrays.stream(values())
                .filter(dayOfWeek -> this.getGapMod(dayOfWeek.sequence) == mod)
                .findFirst()
                .orElseThrow();
    }

    private int getGapMod(final int mod) {
        return (mod - this.sequence + TOTAL_COUNT) % TOTAL_COUNT;
    }

    public boolean isSunDay() {
        return this.equals(SUNDAY);
    }

    public boolean isWeekend() {
        return this.week.equals(WEEKEND);
    }
}
