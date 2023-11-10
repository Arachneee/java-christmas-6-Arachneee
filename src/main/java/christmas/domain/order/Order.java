package christmas.domain.order;

import christmas.domain.day.Day;
import java.util.Map;
import java.util.Map.Entry;

public class Order {

    private final Day day;
    private final Map<Menu, Integer> menuCount;

    private Order(final Day day, final Map<Menu, Integer> menuCount) {
        this.day = day;
        this.menuCount = menuCount;
    }

    public static Order of(final Day day, final Map<Menu, Integer> menuCount) {
        return new Order(day, menuCount);
    }

    public int calculateTotalPrice() {
        return menuCount.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public int calculateDayGap(final Day day) {
        return this.day.gap(day);
    }

    public int countWeekEventMenu() {
        final Category category = day.checkEventCategory();

        return menuCount.entrySet().stream()
                .filter(entry -> entry.getKey().isCategory(category))
                .mapToInt(Entry::getValue)
                .sum();
    }

    public boolean isTotalPriceUnder(final int price) {
        return calculateTotalPrice() < price;
    }

    public boolean isDayOverThan(final Day day) {
        return this.day.isOverThan(day);
    }

    public boolean isSunDay() {
        return day.isSunDay();
    }

    public boolean isChristmasDay() {
        return day.isChristmasDay();
    }
}
