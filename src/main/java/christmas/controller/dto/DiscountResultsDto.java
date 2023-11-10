package christmas.controller.dto;

import christmas.domain.event.constant.EventType;
import christmas.domain.result.DiscountResult;
import christmas.domain.result.DiscountResults;
import christmas.domain.result.constant.Badge;
import java.util.List;

public record DiscountResultsDto(
        GiftDto giftDto,
        List<DiscountResultDto> discounts,
        int beforePaymentAmount,
        int totalDiscountAmount,
        int afterDiscountPayment,
        String badge
) {

    public static DiscountResultsDto from(final DiscountResults discountResults) {
        final int totalDiscountAmount = discountResults.calculateTotalDiscount();

        return new DiscountResultsDto(GiftDto.of(EventType.GIFT_MENU.getTitle(), discountResults.getGiftCount()),
                convertDiscountResultDtos(discountResults),
                discountResults.getTotalOriginalPrice(),
                totalDiscountAmount,
                discountResults.calculateAfterDiscountPayment(),
                Badge.from(totalDiscountAmount).getTitle());
    }

    private static List<DiscountResultDto> convertDiscountResultDtos(final DiscountResults discountResults) {
        return discountResults.getDiscountResults().stream()
                .filter(DiscountResult::isNotZero)
                .map(DiscountResultDto::from)
                .toList();
    }
}
