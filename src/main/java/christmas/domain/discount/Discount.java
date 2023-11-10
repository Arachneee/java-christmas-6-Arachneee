package christmas.domain.discount;

import christmas.domain.event.constant.EventType;

public class Discount {

    private final EventType eventType;
    private final int discountAmount;

    private Discount(final EventType eventType, final int discountAmount) {
        this.eventType = eventType;
        this.discountAmount = discountAmount;
    }

    public static Discount of(final EventType eventType, final int discountAmount) {
        return new Discount(eventType, discountAmount);
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
