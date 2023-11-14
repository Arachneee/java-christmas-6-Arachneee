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
        discountService.applyEventAll(order);
        giftService.applyEventAll(order);
    }

    public EventDetailResponse getEventDetail(final int priceBeforeDiscount) {
        final int totalDiscountBenefits = discountService.calculateTotalBenefits();
        final int totalGiftBenefits = giftService.calculateTotalBenefits();

        final int totalBenefitsAmount = totalDiscountBenefits + totalGiftBenefits;

        return buildEventDetailResponse(priceBeforeDiscount, totalBenefitsAmount, totalDiscountBenefits);
    }

    private EventDetailResponse buildEventDetailResponse(
            final int priceBeforeDiscount,
            final int totalBenefitsAmount,
            final int totalDiscountBenefits
    ) {

        return EventDetailResponseBuilder.builder()
                .priceBeforeEvent(priceBeforeDiscount)
                .giftMenuResponses(giftService.getGiftMenuResponse())
                .activeEvents(getAllActiveEvent())
                .totalBenefitsAmount(totalBenefitsAmount)
                .priceAfterEvent(calculatePriceAfterDiscount(priceBeforeDiscount, totalDiscountBenefits))
                .badge(getBadgeTitle(totalBenefitsAmount))
                .build();
    }

    private List<EventResponse> getAllActiveEvent() {
        return Stream.concat(discountService.getActiveEventResult().stream(),
                        giftService.getActiveEventResult().stream())
                .toList();
    }

    private int calculatePriceAfterDiscount(final int priceBeforeDiscount, final int totalDiscountBenefits) {
        return priceBeforeDiscount - totalDiscountBenefits;
    }

    private String getBadgeTitle(final int totalBenefitsAmount) {
        return Badge.from(totalBenefitsAmount).getTitle();
    }


}
