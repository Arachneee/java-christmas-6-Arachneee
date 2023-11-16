package christmas.domain.order.constant;

public enum Category {
    APPETIZER, MAIN, DESSERT, BEVERAGE;

    public boolean isNotBeverage() {
        return !this.equals(BEVERAGE);
    }
}
