package christmas.domain.order;

import christmas.domain.day.Day;
import christmas.domain.order.constant.Category;
import christmas.domain.order.constant.Menu;
import christmas.exception.OrderException;
import christmas.exception.constant.ErrorMessage;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Order {

    private static final int TOTAL_COUNT_MAX = 20;
    private static final int ONCE_COUNT_MIN = 1;
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

    private static EnumMap<Menu, Integer> convertToEnumMap(final Map<String, Integer> nameCount) {
        return nameCount.entrySet().stream()
                .collect(Collectors.toMap(entry -> Menu.from(entry.getKey()),
                        Entry::getValue,
                        throwingDuplicateOrderException(),
                        () -> new EnumMap<>(Menu.class)));
    }

    private static <T> BinaryOperator<T> throwingDuplicateOrderException() {
        return (menu, other) -> {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        };
    }

    private static void validate(final Map<Menu, Integer> menuCount) {
        validateCountRange(menuCount.values());
        validateTotalCountMax(menuCount.values());
        validateOnlyBeverage(menuCount.keySet());
    }

    private static void validateCountRange(final Collection<Integer> counts) {
        if (isUnderThanMin(counts)) {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        }

    }

    private static boolean isUnderThanMin(final Collection<Integer> counts) {
        return counts.stream()
                .anyMatch(count -> count < ONCE_COUNT_MIN);
    }


    private static void validateTotalCountMax(final Collection<Integer> counts) {
        if (calculateTotalCount(counts) > TOTAL_COUNT_MAX) {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        }
    }

    private static int calculateTotalCount(final Collection<Integer> counts) {
        return counts.stream()
                .mapToInt(value -> value)
                .sum();
    }

    private static void validateOnlyBeverage(final Set<Menu> menus) {
        if (isOnlyBeverage(menus)) {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        }
    }

    private static boolean isOnlyBeverage(final Set<Menu> menus) {
        return menus.stream()
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

    public Map<Menu, Integer> getMenuCount() {
        return Collections.unmodifiableMap(menuCount);
    }

    public int getDayInt() {
        return day.getValue();
    }
}
