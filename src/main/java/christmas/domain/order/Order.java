package christmas.domain.order;

import christmas.constant.Menu;
import java.util.Map;

public class Order {

    private final OrderDay orderDay;
    private final Map<Menu, Integer> menuCount;

    private Order(final OrderDay orderDay, final Map<Menu, Integer> menuCount) {
        this.orderDay = orderDay;
        this.menuCount = menuCount;
    }

    public static Order of(final OrderDay orderDay, final Map<Menu, Integer> menuCount) {
        return new Order(orderDay, menuCount);
    }

    public boolean isTotalPriceUnder(final int price) {
        return calculateTotalPrice() < price;
    }

    public int calculateTotalPrice() {
        return menuCount.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public int calculateDayGap(final OrderDay orderDay) {
        return this.orderDay.gap(orderDay);
    }

    public boolean isDayOverThan(OrderDay orderDay) {
        return this.orderDay.isOverThan(orderDay);
    }
}
