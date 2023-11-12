package christmas.domain.event.discount;

import christmas.domain.event.EventResult;

public class Discount implements EventResult {

    private final DiscountEventType discountEventType;
    private final int amount;

    private Discount(final DiscountEventType discountEventType, final int amount) {
        this.discountEventType = discountEventType;
        this.amount = amount;
    }

    public static Discount of(final DiscountEventType discountEventType, final int amount) {
        return new Discount(discountEventType, amount);
    }

    @Override
    public boolean isActive() {
        return amount != ZERO;
    }

    @Override
    public String getEventTitle() {
        return discountEventType.getTitle();
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
