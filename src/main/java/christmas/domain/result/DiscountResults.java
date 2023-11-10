package christmas.domain.result;

import java.util.Collections;
import java.util.List;

public class DiscountResults {
    private final List<DiscountResult> discountResults;

    private DiscountResults(final List<DiscountResult> discountResults) {
        this.discountResults = discountResults;
    }

    public static DiscountResults from(final List<DiscountResult> discountResults) {
        return new DiscountResults(discountResults);
    }

    public int calculateTotalDiscount() {
        return discountResults.stream()
                .mapToInt(DiscountResult::getDiscountAmount)
                .sum();
    }

    public int calculateAfterDiscountPayment(final int beforePayment) {
        return beforePayment - calculateDiscountForPayment();
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
                .filter(discountResult -> discountResult.getDiscountAmount() != 0)
                .count();
    }


    public List<DiscountResult> getDiscountResults() {
        return Collections.unmodifiableList(discountResults);
    }

    @Override
    public String toString() {
        return "DiscountResults{" +
                "discountResults=" + discountResults +
                '}';
    }
}
