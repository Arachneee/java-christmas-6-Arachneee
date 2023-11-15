package christmas.service.event;

import christmas.domain.event.gift.GiftEventType;
import christmas.domain.event.gift.GiftRepository;
import christmas.response.GiftMenuResponse;
import java.util.List;
import java.util.Map.Entry;

public class GiftService extends EventService<GiftEventType> {

    private final GiftRepository giftRepository;

    public GiftService(final GiftRepository giftRepository) {
        super(giftRepository);
        this.giftRepository = giftRepository;
    }

    public List<GiftMenuResponse> getGiftMenuResponse() {
        return giftRepository.getActiveMenuCounts().entrySet().stream()
                .map(this::createGiftMenuResponse)
                .toList();
    }

    private GiftMenuResponse createGiftMenuResponse(final Entry<String, Integer> entry) {
        return GiftMenuResponse.of(entry.getKey(), entry.getValue());
    }
}
