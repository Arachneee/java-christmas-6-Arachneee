package christmas.controller.dto;

import christmas.domain.event.constant.EventType;
import christmas.domain.result.DiscountResult;
import christmas.domain.result.DiscountResults;
import christmas.domain.result.constant.Badge;
import java.util.List;

public record DiscountResultsDto(
        GiftDto giftDto,
        List<DiscountResultDto> discounts,
        int totalDiscountAmount,
        int afterDiscountPayment,
        String badge
) {

    public static DiscountResultsDto of(final DiscountResults discountResults, final int totalPrice) {
        final int totalDiscountAmount = discountResults.calculateTotalDiscount();

        return new DiscountResultsDto(GiftDto.from(EventType.GIFT_MENU.getTitle(), discountResults.getGiftCount()),
                convertDiscountResultDtos(discountResults),
                totalDiscountAmount,
                discountResults.calculateAfterDiscountPayment(totalPrice),
                Badge.from(totalDiscountAmount).getTitle());
    }

    private static List<DiscountResultDto> convertDiscountResultDtos(DiscountResults discountResults) {
        return discountResults.getDiscountResults().stream()
                .filter(DiscountResult::isNotZero)
                .map(DiscountResultDto::from)
                .toList();
    }
}
