package christmas.domain.order;


import static christmas.domain.order.Category.APPETIZER;
import static christmas.domain.order.Category.BEVERAGE;
import static christmas.domain.order.Category.DESSERT;
import static christmas.domain.order.Category.MAIN;

public enum Menu {

    BUTTON_MUSHROOM_SOUP(APPETIZER, 6_000),
    TAPAS(APPETIZER, 5_500),
    CAESAR_SALAD(APPETIZER, 8_000),
    T_BONE_STEAK(MAIN, 55_000),
    BARBECUE_RIBS(MAIN, 54_000),
    SEAFOOD_PASTA(MAIN, 35_000),
    CHRISTMAS_PASTA(MAIN, 25_000),
    CHOCOLATE_CAKE(DESSERT, 15_000),
    ICE_CREAM(DESSERT, 5_000),
    ZERO_COLA(BEVERAGE, 3_000),
    RED_WINE(BEVERAGE, 60_000),
    CHAMPAGNE(BEVERAGE, 25_000);


    private final Category category;
    private final int price;

    Menu(final Category category, final int price) {
        this.category = category;
        this.price = price;
    }

    public boolean isCategory(final Category category) {
        return this.category.equals(category);
    }

    public int getPrice() {
        return price;
    }
}
