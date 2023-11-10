package christmas.domain.discount;

import christmas.domain.event.constant.EventType;

public class Discount {

    private final EventType eventType;
    private final int amount;

    private Discount(final EventType eventType, final int amount) {
        this.eventType = eventType;
        this.amount = amount;
    }

    public static Discount of(final EventType eventType, final int amount) {
        return new Discount(eventType, amount);
    }

    public boolean isDiscount() {
        return !eventType.isGift();
    }

    public boolean isGiftEvent() {
        return eventType.isGift();
    }

    public boolean isNotZero() {
        return amount != 0;
    }

    public int getAmount() {
        return amount;
    }

    public String getTitle() {
        return eventType.getTitle();
    }
}
