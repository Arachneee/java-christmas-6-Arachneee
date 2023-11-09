package christmas.constant;


import static christmas.constant.Menu.Category.APPETIZER;
import static christmas.constant.Menu.Category.BEVERAGE;
import static christmas.constant.Menu.Category.DESSERT;
import static christmas.constant.Menu.Category.MAIN;

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

    Menu(Category category, int price) {
        this.category = category;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    protected enum Category {
        APPETIZER, MAIN, DESSERT, BEVERAGE
    }
}
