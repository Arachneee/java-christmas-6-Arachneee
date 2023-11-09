package christmas.domain.result;

import java.util.List;

public class DiscountResults {
    private final List<DiscountResult> discountResults;

    private DiscountResults(List<DiscountResult> discountResults) {
        this.discountResults = discountResults;
    }

    public static DiscountResults from(final List<DiscountResult> discountResults) {
        return new DiscountResults(discountResults);
    }
}
