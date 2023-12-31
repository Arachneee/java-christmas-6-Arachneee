package christmas.domain.order.constant;


import static christmas.domain.order.constant.Category.APPETIZER;
import static christmas.domain.order.constant.Category.BEVERAGE;
import static christmas.domain.order.constant.Category.DESSERT;
import static christmas.domain.order.constant.Category.MAIN;
import static christmas.exception.ErrorMessage.INVALID_ORDER;
import static java.util.stream.Collectors.toMap;

import christmas.exception.OrderException;
import java.util.Arrays;
import java.util.Map;

public enum Menu {

    BUTTON_MUSHROOM_SOUP("양송이수프", APPETIZER, 6_000),
    TAPAS("타파스", APPETIZER, 5_500),
    CAESAR_SALAD("시저샐러드", APPETIZER, 8_000),
    T_BONE_STEAK("티본스테이크", MAIN, 55_000),
    BARBECUE_RIBS("바비큐립", MAIN, 54_000),
    SEAFOOD_PASTA("해산물파스타", MAIN, 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", MAIN, 25_000),
    CHOCOLATE_CAKE("초코케이크", DESSERT, 15_000),
    ICE_CREAM("아이스크림", DESSERT, 5_000),
    ZERO_COLA("제로콜라", BEVERAGE, 3_000),
    RED_WINE("레드와인", BEVERAGE, 60_000),
    CHAMPAGNE("샴페인", BEVERAGE, 25_000);

    private static final Map<String, Menu> menus = Arrays.stream(values())
            .collect(toMap(menu -> menu.title, menu -> menu));
    private final String title;
    private final Category category;
    private final int price;

    Menu(final String title, final Category category, final int price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public static Menu from(final String title) {
        return menus.computeIfAbsent(title, key -> {
            throw OrderException.from(INVALID_ORDER);
        });
    }

    public boolean isCategory(final Category category) {
        return this.category.equals(category);
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }
}
