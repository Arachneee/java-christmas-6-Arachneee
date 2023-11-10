package christmas.domain.order;

import christmas.domain.day.Day;
import christmas.domain.order.constant.Category;
import christmas.domain.order.constant.Menu;
import christmas.exception.OrderException;
import christmas.exception.constant.ErrorMessage;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Order {

    private static final int MAX = 20;
    private final Day day;
    private final Map<Menu, Integer> menuCount;

    private Order(final Day day, final Map<Menu, Integer> menuCount) {
        this.day = day;
        this.menuCount = menuCount;
    }

    public static Order of(final Day day, final Map<String, Integer> nameCount) {
        final Map<Menu, Integer> menuCount = convertToEnumMap(nameCount);
        validate(menuCount);

        return new Order(day, menuCount);
    }

    private static EnumMap<Menu, Integer> convertToEnumMap(Map<String, Integer> nameCount) {
        return nameCount.entrySet().stream()
                .collect(Collectors.toMap(entry -> Menu.from(entry.getKey()),
                        Entry::getValue,
                        throwingOrderException(),
                        () -> new EnumMap<>(Menu.class)));
    }

    private static <T> BinaryOperator<T> throwingOrderException() {
        return (menu, other) -> {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        };
    }

    private static void validate(final Map<Menu, Integer> menuCount) {
        validateCountMax(menuCount);
        validateOnlyBeverage(menuCount);
    }


    private static void validateCountMax(final Map<Menu, Integer> menuCount) {
        if (calculateTotalCount(menuCount) > MAX) {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        }
    }

    private static int calculateTotalCount(final Map<Menu, Integer> menuCount) {
        return menuCount.values()
                .stream().mapToInt(value -> value)
                .sum();
    }

    private static void validateOnlyBeverage(final Map<Menu, Integer> menuCount) {
        if (isOnlyBeverage(menuCount)) {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        }
    }

    private static boolean isOnlyBeverage(final Map<Menu, Integer> menuCount) {
        return menuCount.keySet().stream()
                .map(Menu::getCategory)
                .distinct()
                .noneMatch(Category::isNotBeverage);
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

    public boolean isWeekend() {
        return day.isWeekend();
    }
}
