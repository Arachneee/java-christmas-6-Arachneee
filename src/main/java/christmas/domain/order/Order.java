package christmas.domain.order;

import christmas.constant.Category;
import christmas.constant.Menu;
import java.util.Map;
import java.util.Map.Entry;

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

    public boolean isDayOverThan(final OrderDay orderDay) {
        return this.orderDay.isOverThan(orderDay);
    }

    public int countMenu(final Category category) {
        return menuCount.entrySet().stream()
                .filter(entry -> entry.getKey().isCategory(category))
                .mapToInt(Entry::getValue)
                .sum();
    }

    public boolean isWeekDay() {
        return !orderDay.isWeekend();
    }
}
