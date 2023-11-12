package christmas.service.event;

import christmas.domain.event.EventResult;
import christmas.domain.event.gift.Gift;
import christmas.domain.event.gift.GiftEventType;
import christmas.domain.event.gift.GiftRepository;
import christmas.domain.order.Order;
import christmas.response.EventResponse;
import christmas.response.GiftMenuResponse;
import java.util.List;

public class GiftService {

    private GiftRepository giftRepository;

    public void createGift(final Order order) {
        final List<Gift> gifts = GiftEventType.applyAll(order);
        giftRepository = GiftRepository.create(gifts);
    }

    public int calculateTotalAmount() {
        return giftRepository.calculateTotal();
    }

    public List<GiftMenuResponse> createGiftMenuResponse() {
        return giftRepository.getGifts().stream()
                .filter(Gift::isActive)
                .map(gift -> GiftMenuResponse.of(gift.getMenuTitle(), gift.getMenuCount()))
                .toList();
    }

    public List<EventResponse> getEventResult() {

        return giftRepository.getGifts().stream()
                .filter(EventResult::isActive)
                .map(eventResult -> EventResponse.from(eventResult.getEventTitle(), eventResult.getAmount()))
                .toList();
    }
}
