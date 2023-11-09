package christmas.domain.discount;

import christmas.domain.order.Order;

public class ChristmasDiscount implements Discount {


    @Override
    public int calculateAmount(Order order) {
        return 0;
    }

    @Override
    public boolean isAvailable(Order order) {
        return false;
    }
}
