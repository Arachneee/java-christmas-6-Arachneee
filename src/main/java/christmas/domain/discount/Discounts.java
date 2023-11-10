package christmas.domain.discount;

import java.util.Collections;
import java.util.List;

public class Discounts {

    private final List<Discount> discounts;
    private final int totalOriginalPrice;

    private Discounts(final List<Discount> discounts, final int totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
        this.discounts = discounts;
    }

    public static Discounts from(final List<Discount> discounts, final int totalOriginalPrice) {
        return new Discounts(discounts, totalOriginalPrice);
    }

    public int calculateTotalDiscount() {
        return discounts.stream()
                .mapToInt(Discount::getDiscountAmount)
                .sum();
    }

    public int calculateAmountAfterDiscount() {
        return totalOriginalPrice - calculateDiscountForPayment();
    }

    private int calculateDiscountForPayment() {
        return discounts.stream()
                .filter(Discount::isDiscount)
                .mapToInt(Discount::getDiscountAmount)
                .sum();
    }

    public int getGiftCount() {
        return (int) discounts.stream()
                .filter(Discount::isGiftEvent)
                .filter(Discount::isNotZero)
                .count();
    }

    public List<Discount> getDiscountResults() {
        return Collections.unmodifiableList(discounts);
    }

    public int getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    @Override
    public String toString() {
        return "DiscountResults{" +
                "discountResults=" + discounts +
                '}';
    }
}
