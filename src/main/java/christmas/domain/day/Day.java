package christmas.domain.day;

import static christmas.exception.ErrorMessage.INVALID_DAY;

import christmas.exception.OrderException;

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
        return !December.in(day);
    }

    public int gap(final Day other) {
        return Math.abs(other.day - this.day);
    }

    public boolean isOverThan(final Day other) {
        return this.day > other.day;
    }

    public Integer mod(final int weekCount) {
        return day % weekCount;
    }

    public boolean isChristMasDay() {
        return December.isChristMas(day);
    }
}
