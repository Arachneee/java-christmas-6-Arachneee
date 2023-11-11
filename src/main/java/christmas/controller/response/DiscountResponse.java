package christmas.controller.response;

import christmas.domain.discount.Discount;

public record DiscountResponse(String title, int amount) {

    public static DiscountResponse from(final Discount discount) {
        return new DiscountResponse(discount.getTitle(), discount.getAmount());
    }
}
