package christmas.domain.discount;

import static christmas.constant.Category.DESSERT;

import christmas.domain.order.Order;

public class WeekdayDiscount extends Discount {
    private static final int ONCE_AMOUNT = 2023;

    @Override
    boolean isUnavailable(Order order) {
        return order.isWeekend();
    }

    @Override
    int calculateAmount(Order order) {
        return order.countMenu(DESSERT) * ONCE_AMOUNT;
    }
}
