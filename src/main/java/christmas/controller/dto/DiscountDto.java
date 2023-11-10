package christmas.controller.dto;

import christmas.domain.discount.Discount;

public record DiscountDto(String title, int payment) {

    public static DiscountDto from(final Discount discount) {
        return new DiscountDto(discount.getTitle(), discount.getDiscountAmount());
    }
}
