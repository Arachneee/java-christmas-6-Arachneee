package christmas.domain.discount;

import christmas.domain.order.Order;

public interface Discount {

    int calculateAmount(Order order);
    boolean isAvailable(Order order);

}
