package christmas.response;

public record OrderSummaryResponse(OrderResponse orderResponse, EventDetailResponse eventDetailResponse) {

    public static OrderSummaryResponse of(final OrderResponse orderResponse,
            final EventDetailResponse eventDetailResponse) {
        return new OrderSummaryResponse(orderResponse, eventDetailResponse);
    }
}
