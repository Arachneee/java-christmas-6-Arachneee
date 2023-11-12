package christmas.domain.event.gift;

import christmas.domain.event.EventRepository;
import java.util.Collections;
import java.util.List;

public class GiftRepository implements EventRepository {

    private final List<Gift> gifts;

    private GiftRepository(final List<Gift> gifts) {
        this.gifts = gifts;
    }

    public static GiftRepository create(final List<Gift> gifts) {
        return new GiftRepository(gifts);
    }

    @Override
    public int calculateTotal() {
        return gifts.stream()
                .mapToInt(Gift::getAmount)
                .sum();
    }

    public List<Gift> getGifts() {
        return Collections.unmodifiableList(gifts);
    }
}
