package christmas.domain.discount;

import christmas.constant.Category;
import christmas.domain.order.Order;

public class WeekDayDiscount extends Discount {
    private static final int ONCE_AMOUNT = 2023;

    @Override
    boolean isUnavailable(Order order) {
        return false;
    }

    @Override
    int calculateAmount(Order order) {
        return order.countMenu(Category.DESSERT) * ONCE_AMOUNT;
    }
}
