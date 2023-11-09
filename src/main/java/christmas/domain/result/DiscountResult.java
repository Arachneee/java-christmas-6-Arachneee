package christmas.domain.result;

import christmas.domain.event.Events;

public class DiscountResult {

    private final Events events;
    private final int discountAmount;

    private DiscountResult(final Events events, final int discountAmount) {
        this.events = events;
        this.discountAmount = discountAmount;
    }

    public static DiscountResult of(final Events events, final int discountAmount) {
        return new DiscountResult(events, discountAmount);
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public boolean isDiscount() {
        return events.isDiscount();
    }
}
