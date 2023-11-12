package christmas.domain.event;

import christmas.domain.order.Order;


public interface Event<T extends EventResult> {

    int calculateBenefits(final Order order);

    String getTitle();

}
