package christmas.domain.day;

import static christmas.exception.constant.ErrorMessage.INVALID_DAY;

import christmas.domain.day.constant.DayOfWeek;
import christmas.domain.day.constant.December;
import christmas.domain.day.constant.Week;
import christmas.domain.order.constant.Category;
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

    public int gap(final Day other) {
        return Math.abs(other.day - this.day);
    }

    public Integer mod(final int weekCount) {
        return day % weekCount;
    }

    public Category checkEventCategory() {
        return Week.from(this).getEventCategory();
    }

    private static boolean isOutOfRange(final int day) {
        return !December.in(day);
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
}
