package christmas.domain.discount;

import java.util.Collections;
import java.util.List;

public class Discounts {

    private final List<Discount> discounts;

    private Discounts(final List<Discount> discounts) {
        this.discounts = discounts;
    }

    public static Discounts from(final List<Discount> discounts) {
        return new Discounts(discounts);
    }

    public int calculateTotal() {
        return discounts.stream()
                .mapToInt(Discount::getAmount)
                .sum();
    }

    public int calculateTotalNotGift() {
        return discounts.stream()
                .filter(Discount::isDiscount)
                .mapToInt(Discount::getAmount)
                .sum();
    }

    public int countGift() {
        return (int) discounts.stream()
                .filter(discount -> discount.isGiftEvent() && discount.isNotZero())
                .count();
    }

    public List<Discount> getDiscounts() {
        return Collections.unmodifiableList(discounts);
    }
}
