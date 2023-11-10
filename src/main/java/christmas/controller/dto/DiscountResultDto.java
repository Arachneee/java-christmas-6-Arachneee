package christmas.controller.dto;

import christmas.domain.result.DiscountResult;

public record DiscountResultDto(String title, int payment) {

    public static DiscountResultDto from(final DiscountResult discountResult) {
        return new DiscountResultDto(discountResult.getTitle(), discountResult.getDiscountAmount());
    }
}
