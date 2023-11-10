package christmas.controller.dto;

import christmas.domain.event.constant.EventType;
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
        return new DiscountResultsDto(GiftDto.from(EventType.GIFT_MENU.getTitle(), discountResults.getGiftCount()),
                convertDiscountResultDtos(discountResults),
                discountResults.calculateTotalDiscount(),
                discountResults.calculateAfterDiscountPayment(totalPrice),
                Badge.from(totalPrice).getTitle());
    }

    private static List<DiscountResultDto> convertDiscountResultDtos(DiscountResults discountResults) {
        return discountResults.getDiscountResults().stream()
                .map(DiscountResultDto::from)
                .toList();
    }
}
