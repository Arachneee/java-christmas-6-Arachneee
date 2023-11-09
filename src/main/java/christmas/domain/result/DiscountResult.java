package christmas.domain.result;

public class DiscountResult {

    private final String title;
    private final int discountAmount;

    private DiscountResult(final String title, final int discountAmount) {
        this.title = title;
        this.discountAmount = discountAmount;
    }

    public static DiscountResult of(final String title, final int discountAmount) {
        return new DiscountResult(title, discountAmount);
    }
}
