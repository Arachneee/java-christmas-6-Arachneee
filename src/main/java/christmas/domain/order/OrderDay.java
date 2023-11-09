package christmas.domain.order;

import static christmas.exception.ErrorMessage.INVALID_DAY;

import christmas.exception.OrderException;

public class OrderDay {

    private static final int MIN = 1;
    private static final int MAX = 31;
    private final int day;

    private OrderDay(final int day) {
        this.day = day;
    }

    public static OrderDay from(final int day) {
        validate(day);

        return new OrderDay(day);
    }

    private static void validate(final int day) {
        if (isOutOfRange(day)) {
            throw OrderException.from(INVALID_DAY);
        }
    }

    private static boolean isOutOfRange(int day) {
        return day > MAX || day < MIN;
    }

    public int gap(final OrderDay orderDay) {
        return orderDay.gap(day);
    }

    private int gap(final int day) {
        return Math.abs(day - this.day);
    }

    public boolean isOverThan(final OrderDay orderDay) {
        return orderDay.isUnderThan(this.day);
    }

    private boolean isUnderThan(final int day) {
        return day > this.day;
    }

    public Integer mod(final int weekCount) {
        return day % weekCount;
    }
}
