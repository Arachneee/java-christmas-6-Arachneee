package christmas.controller.dto;

import static christmas.domain.event.constant.EventType.GIFT_MENU;

import christmas.domain.discount.Discount;
import christmas.domain.discount.Discounts;
import christmas.domain.discount.constant.Badge;
import java.util.List;

public record DiscountsDto(
        int amountBeforeDiscount,
        GiftDto giftDto,
        List<DiscountDto> activeDiscount,
        int totalDiscountAmount,
        int amountAfterDiscount,
        String badge
) {

    public static DiscountsDto from(final Discounts discounts) {

        return new DiscountsDtoBuilder()
                .amountBeforeDiscount(discounts.getTotalOriginalPrice())
                .giftDto(createGiftDto(discounts))
                .activeDiscount(getActiveDiscount(discounts))
                .totalDiscountAmount(discounts.calculateTotalDiscount())
                .amountAfterDiscount(discounts.calculateAmountAfterDiscount())
                .build();
    }

    private static GiftDto createGiftDto(final Discounts discounts) {
        return GiftDto.of(GIFT_MENU.getTitle(), discounts.getGiftCount());
    }

    private static List<DiscountDto> getActiveDiscount(final Discounts discounts) {
        return discounts.getDiscountResults().stream()
                .filter(Discount::isNotZero)
                .map(DiscountDto::from)
                .toList();
    }

    public static class DiscountsDtoBuilder {

        int amountBeforeDiscount;
        GiftDto giftDto;
        List<DiscountDto> activeDiscount;
        int totalDiscountAmount;
        int amountAfterDiscount;
        String badge;

        public DiscountsDtoBuilder amountBeforeDiscount(final int amountBeforeDiscount) {
            this.amountBeforeDiscount = amountBeforeDiscount;
            return this;
        }

        public DiscountsDtoBuilder giftDto(final GiftDto giftDto) {
            this.giftDto = giftDto;
            return this;
        }

        public DiscountsDtoBuilder activeDiscount(final List<DiscountDto> activeDiscount) {
            this.activeDiscount = activeDiscount;
            return this;
        }

        public DiscountsDtoBuilder totalDiscountAmount(final int totalDiscountAmount) {
            this.totalDiscountAmount = totalDiscountAmount;
            this.badge = Badge.from(totalDiscountAmount).getTitle();
            return this;
        }

        public DiscountsDtoBuilder amountAfterDiscount(final int amountAfterDiscount) {
            this.amountAfterDiscount = amountAfterDiscount;
            return this;
        }

        public DiscountsDto build() {
            return new DiscountsDto(amountBeforeDiscount, giftDto, activeDiscount, totalDiscountAmount,
                    amountAfterDiscount, badge);
        }
    }

}
