package christmas.domain.order.day;

import static christmas.exception.ErrorMessage.INVALID_DAY;

import christmas.domain.order.menu.Category;
import christmas.exception.OrderException;
import java.util.Objects;

public class Day {

    private final int day;

    private Day(final int day) {
        this.day = day;
    }

    public static Day from(final int day) {
        validate(day);
        return new Day(day);
    }

    private static void validate(final int day) {
        if (isOutOfRange(day)) {
            throw OrderException.from(INVALID_DAY);
        }
    }

    private static boolean isOutOfRange(final int day) {
        return !December.isInRange(day);
    }

    public int gap(final Day other) {
        return Math.abs(other.day - this.day);
    }

    public Integer mod(final int weekCount) {
        return day % weekCount;
    }

    public Category getTodayEventCategory() {
        return Week.from(DayOfWeek.from(this)).getEventCategory();
    }

    public boolean isOverThan(final Day other) {
        return this.day > other.day;
    }

    public boolean isChristmasDay() {
        return December.isChristMas(day);
    }

    public boolean isSunDay() {
        return DayOfWeek.from(this).isSunDay();
    }

    public boolean isWeekend() {
        return DayOfWeek.from(this).isWeekend();
    }

    public int getValue() {
        return day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Day day1 = (Day) o;
        return day == day1.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day);
    }
}
