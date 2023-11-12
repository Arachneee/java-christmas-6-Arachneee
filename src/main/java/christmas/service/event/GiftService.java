package christmas.service.event;

import christmas.domain.event.gift.Gift;
import christmas.domain.event.gift.GiftEventType;
import christmas.domain.event.gift.GiftRepository;
import christmas.domain.order.Order;
import christmas.response.GiftMenuResponse;
import java.util.List;

public class GiftService extends EventService<Gift> {

    private final GiftRepository giftRepository;

    public GiftService(final GiftRepository giftRepository) {
        super(giftRepository);
        this.giftRepository = giftRepository;
    }

    public void createGift(final Order order) {
        final List<Gift> gifts = GiftEventType.applyAll(order);
        giftRepository.init(gifts);
    }

    public List<GiftMenuResponse> createGiftMenuResponse() {
        return giftRepository.getActiveResult().stream()
                .map(this::createGiftMenuResponse)
                .toList();
    }

    private GiftMenuResponse createGiftMenuResponse(final Gift gift) {
        return GiftMenuResponse.of(gift.getMenuTitle(), gift.getMenuCount());
    }
}
