package christmas.domain.result;

import java.util.Collections;
import java.util.List;

public class DiscountResults {

    private final List<DiscountResult> discountResults;
    private final int totalOriginalPrice;

    private DiscountResults(final List<DiscountResult> discountResults, final int totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
        this.discountResults = discountResults;
    }

    public static DiscountResults from(final List<DiscountResult> discountResults, final int totalOriginalPrice) {
        return new DiscountResults(discountResults, totalOriginalPrice);
    }

    public int calculateTotalDiscount() {
        return discountResults.stream()
                .mapToInt(DiscountResult::getDiscountAmount)
                .sum();
    }

    public int calculateAfterDiscountPayment() {
        return totalOriginalPrice - calculateDiscountForPayment();
    }

    private int calculateDiscountForPayment() {
        return discountResults.stream()
                .filter(DiscountResult::isDiscount)
                .mapToInt(DiscountResult::getDiscountAmount)
                .sum();
    }

    public int getGiftCount() {
        return (int) discountResults.stream()
                .filter(DiscountResult::isGiftEvent)
                .filter(DiscountResult::isNotZero)
                .count();
    }

    public List<DiscountResult> getDiscountResults() {
        return Collections.unmodifiableList(discountResults);
    }

    public int getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    @Override
    public String toString() {
        return "DiscountResults{" +
                "discountResults=" + discountResults +
                '}';
    }
}
