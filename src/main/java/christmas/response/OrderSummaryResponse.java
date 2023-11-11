package christmas.response;

public record OrderSummaryResponse(OrderResponse orderResponse, DiscountDetailResponse discountDetailResponse) {

    public static OrderSummaryResponse of(final OrderResponse orderResponse,
            final DiscountDetailResponse discountDetailResponse) {
        return new OrderSummaryResponse(orderResponse, discountDetailResponse);
    }
}
