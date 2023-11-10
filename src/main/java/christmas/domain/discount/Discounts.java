package christmas.domain.discount;

import java.util.Collections;
import java.util.List;

public class Discounts {

    private final List<Discount> discounts;
    private final int totalPriceBefore;

    private Discounts(final List<Discount> discounts, final int totalPriceBefore) {
        this.totalPriceBefore = totalPriceBefore;
        this.discounts = discounts;
    }

    public static Discounts of(final List<Discount> discounts, final int totalPriceBefore) {
        return new Discounts(discounts, totalPriceBefore);
    }

    public int calculateTotal() {
        return discounts.stream()
                .mapToInt(Discount::getAmount)
                .sum();
    }

    public int calculatePriceAfter() {
        return totalPriceBefore - calculateTotalNotGift();
    }

    private int calculateTotalNotGift() {
        return discounts.stream()
                .filter(Discount::isDiscount)
                .mapToInt(Discount::getAmount)
                .sum();
    }

    public int countGift() {
        return (int) discounts.stream()
                .filter(Discount::isGiftEvent)
                .filter(Discount::isNotZero)
                .count();
    }

    public List<Discount> getDiscounts() {
        return Collections.unmodifiableList(discounts);
    }

    public int getTotalPriceBefore() {
        return totalPriceBefore;
    }
}
