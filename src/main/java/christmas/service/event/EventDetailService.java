package christmas.service.event;

import christmas.domain.event.Badge;
import christmas.domain.order.Order;
import christmas.response.EventDetailResponse;
import christmas.response.EventDetailResponse.EventDetailResponseBuilder;
import christmas.response.EventResponse;
import java.util.List;
import java.util.stream.Stream;

public class EventDetailService {

    private final DiscountService discountService;
    private final GiftService giftService;

    public EventDetailService(final DiscountService discountService, final GiftService giftService) {
        this.discountService = discountService;
        this.giftService = giftService;
    }

    public void createEvent(final Order order) {
        discountService.createDiscount(order);
        giftService.createGift(order);
    }

    public EventDetailResponse getEventDetail(final int priceBeforeDiscount) {
        final int totalDiscountAmount = discountService.calculateTotalAmount();
        final int totalGiftAmount = giftService.calculateTotalAmount();

        final int totalBenefitsAmount = totalDiscountAmount + totalGiftAmount;

        return buildEventDetailResponse(priceBeforeDiscount, totalBenefitsAmount, totalDiscountAmount);
    }

    private EventDetailResponse buildEventDetailResponse(
            final int priceBeforeDiscount,
            final int totalBenefitsAmount,
            final int totalDiscountAmount
    ) {

        return EventDetailResponseBuilder.builder()
                .priceBeforeEvent(priceBeforeDiscount)
                .giftMenuResponses(giftService.createGiftMenuResponse())
                .activeEvents(getAllActiveEvent())
                .totalBenefitsAmount(totalBenefitsAmount)
                .priceAfterEvent(calculatePriceAfterDiscount(priceBeforeDiscount, totalDiscountAmount))
                .badge(getBadgeTitle(totalBenefitsAmount))
                .build();
    }

    private List<EventResponse> getAllActiveEvent() {
        return Stream.concat(discountService.getActiveEventResult().stream(),
                        giftService.getActiveEventResult().stream())
                .toList();
    }

    private int calculatePriceAfterDiscount(final int priceBeforeDiscount, final int totalDiscountAmount) {
        return priceBeforeDiscount - totalDiscountAmount;
    }

    private String getBadgeTitle(final int totalBenefitsAmount) {
        return Badge.from(totalBenefitsAmount).getTitle();
    }


}
