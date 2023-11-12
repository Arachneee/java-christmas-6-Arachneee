package christmas.domain.event;

import christmas.domain.order.Order;

@FunctionalInterface

public interface Event {

    int calculateBenefits(final Order order);

}
