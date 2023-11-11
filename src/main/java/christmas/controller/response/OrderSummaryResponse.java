package christmas.controller.response;

import christmas.domain.discount.Discount;
import christmas.domain.discount.Discounts;
import christmas.domain.discount.constant.Badge;
import christmas.domain.event.EventType.GiftMenu;
import christmas.domain.order.Order;
import java.util.List;

public record OrderSummaryResponse(

        OrderResponse orderResponse,
        int priceBefore,
        GiftResponse giftResponse,
        List<DiscountResponse> activeDiscount,
        int totalAmount,
        int priceAfter,
        String badge
) {

    public static OrderSummaryResponse of(final Order order, final Discounts discounts) {

        return OrderSummaryResponseBuilder.builder()
                .orderResponse(OrderResponse.from(order))
                .priceBefore(discounts.getTotalPriceBefore())
                .giftResponse(createGiftResponse(discounts))
                .activeDiscount(getActiveDiscount(discounts))
                .totalAmount(discounts.calculateTotal())
                .priceAfter(discounts.calculatePriceAfter())
                .build();
    }

    private static GiftResponse createGiftResponse(final Discounts discounts) {
        return GiftResponse.of(GiftMenu.GIFT.name(), discounts.countGift());
    }

    private static List<DiscountResponse> getActiveDiscount(final Discounts discounts) {
        return discounts.getDiscounts().stream()
                .filter(Discount::isNotZero)
                .map(DiscountResponse::from)
                .toList();
    }

    public static class OrderSummaryResponseBuilder {

        OrderResponse orderResponse;
        int priceBefore;
        GiftResponse giftResponse;
        List<DiscountResponse> activeDiscount;
        int totalAmount;
        int priceAfter;
        String badge;

        public static OrderSummaryResponseBuilder builder() {
            return new OrderSummaryResponseBuilder();
        }

        public OrderSummaryResponseBuilder orderResponse(final OrderResponse orderResponse) {
            this.orderResponse = orderResponse;
            return this;
        }

        public OrderSummaryResponseBuilder priceBefore(final int priceBefore) {
            this.priceBefore = priceBefore;
            return this;
        }

        public OrderSummaryResponseBuilder giftResponse(final GiftResponse giftResponse) {
            this.giftResponse = giftResponse;
            return this;
        }

        public OrderSummaryResponseBuilder activeDiscount(final List<DiscountResponse> activeDiscount) {
            this.activeDiscount = activeDiscount;
            return this;
        }

        public OrderSummaryResponseBuilder totalAmount(final int totalAmount) {
            this.totalAmount = totalAmount;
            this.badge = Badge.from(totalAmount).getTitle();
            return this;
        }

        public OrderSummaryResponseBuilder priceAfter(final int priceAfter) {
            this.priceAfter = priceAfter;
            return this;
        }

        public OrderSummaryResponse build() {
            return new OrderSummaryResponse(orderResponse, priceBefore, giftResponse, activeDiscount, totalAmount,
                    priceAfter, badge);
        }
    }

}
