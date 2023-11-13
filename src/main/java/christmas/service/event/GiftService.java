package christmas.service.event;

import christmas.domain.event.gift.GiftEventType;
import christmas.domain.event.gift.GiftRepository;
import christmas.domain.order.Order;
import christmas.response.GiftMenuResponse;
import java.util.EnumMap;
import java.util.List;

public class GiftService extends EventService<GiftEventType> {

    private final GiftRepository giftRepository;

    public GiftService(final GiftRepository giftRepository) {
        super(giftRepository);
        this.giftRepository = giftRepository;
    }

    @Override
    public void applyEventAll(final Order order) {
        final EnumMap<GiftEventType, Integer> giftAmounts = GiftEventType.applyAll(order);
        giftRepository.init(giftAmounts);
    }

    public List<GiftMenuResponse> getGiftMenuResponse() {
        return giftRepository.getActiveMenuCounts().entrySet().stream()
                .map(entry -> GiftMenuResponse.of(entry.getKey(), entry.getValue()))
                .toList();
    }
}
