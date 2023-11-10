package christmas.domain.result;

import christmas.domain.event.constant.EventType;

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

    public boolean isDiscount() {
        return eventType.isDiscount();
    }

    public boolean isGiftEvent() {
        return !eventType.isDiscount();
    }

    public boolean isNotZero() {
        return discountAmount != 0;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public String getTitle() {
        return eventType.getTitle();
    }

    @Override
    public String toString() {
        return "DiscountResult{" +
                "eventType=" + eventType.getTitle() +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
