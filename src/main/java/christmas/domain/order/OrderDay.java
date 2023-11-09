package christmas.domain.order;

import static christmas.exception.ErrorMessage.INVALID_DAY;

import christmas.exception.OrderException;

public class OrderDay {
    private static final int MIN = 1;
    private static final int MAX = 31;
    private int day;

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
}
