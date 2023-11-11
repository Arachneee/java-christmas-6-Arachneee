package christmas.domain.order.menu;

public enum Category {
    APPETIZER, MAIN, DESSERT, BEVERAGE;

    public boolean isNotBeverage() {
        return !this.equals(BEVERAGE);
    }
}
