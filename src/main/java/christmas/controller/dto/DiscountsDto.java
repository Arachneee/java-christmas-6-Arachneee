package christmas.controller.dto;

import static christmas.domain.event.constant.EventType.GIFT_MENU;

import christmas.domain.discount.Discount;
import christmas.domain.discount.Discounts;
import christmas.domain.discount.constant.Badge;
import java.util.List;

public record DiscountsDto(
        int priceBefore,
        GiftDto giftDto,
        List<DiscountDto> activeDiscount,
        int totalAmount,
        int priceAfter,
        String badge
) {

    public static DiscountsDto from(final Discounts discounts) {

        return new DiscountsDtoBuilder()
                .priceBefore(discounts.getTotalPriceBefore())
                .giftDto(createGiftDto(discounts))
                .activeDiscount(getActiveDiscount(discounts))
                .totalAmount(discounts.calculateTotal())
                .priceAfter(discounts.calculatePriceAfter())
                .build();
    }

    private static GiftDto createGiftDto(final Discounts discounts) {
        return GiftDto.of(GIFT_MENU.getTitle(), discounts.countGift());
    }

    private static List<DiscountDto> getActiveDiscount(final Discounts discounts) {
        return discounts.getDiscounts().stream()
                .filter(Discount::isNotZero)
                .map(DiscountDto::from)
                .toList();
    }

    public static class DiscountsDtoBuilder {

        int priceBefore;
        GiftDto giftDto;
        List<DiscountDto> activeDiscount;
        int totalAmount;
        int priceAfter;
        String badge;

        public DiscountsDtoBuilder priceBefore(final int priceBefore) {
            this.priceBefore = priceBefore;
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

        public DiscountsDtoBuilder totalAmount(final int totalAmount) {
            this.totalAmount = totalAmount;
            this.badge = Badge.from(totalAmount).getTitle();
            return this;
        }

        public DiscountsDtoBuilder priceAfter(final int priceAfter) {
            this.priceAfter = priceAfter;
            return this;
        }

        public DiscountsDto build() {
            return new DiscountsDto(priceBefore, giftDto, activeDiscount, totalAmount,
                    priceAfter, badge);
        }
    }

}
