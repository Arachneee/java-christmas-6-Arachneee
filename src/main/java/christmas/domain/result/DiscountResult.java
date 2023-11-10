package christmas.domain.result;

import christmas.domain.event.EventType;

public class DiscountResult {

    private final EventType eventType;
    private final int discountAmount;

    private DiscountResult(final EventType eventType, final int discountAmount) {
        this.eventType = eventType;
        this.discountAmount = discountAmount;
    }

    public static DiscountResult of(final EventType eventType, final int discountAmount) {
        return new DiscountResult(eventType, discountAmount);
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public boolean isDiscount() {
        return eventType.isDiscount();
    }
}
