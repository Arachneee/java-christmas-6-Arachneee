package christmas.domain.discount;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discount discount = (Discount) o;
        return amount == discount.amount && eventType == discount.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, amount);
    }
}
