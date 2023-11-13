package christmas.domain.event.gift;

import static java.util.stream.Collectors.toMap;

import christmas.domain.event.EventRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

public class GiftRepository implements EventRepository {

    private EnumMap<GiftEventType, Integer> giftAmounts = new EnumMap<>(GiftEventType.class);

    public void init(final EnumMap<GiftEventType, Integer> giftAmounts) {
        this.giftAmounts = giftAmounts;
    }

    @Override
    public int calculateTotal() {
        return giftAmounts.values().stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    @Override
    public Map<String, Integer> getActiveResult() {
        return giftAmounts.entrySet().stream()
                .filter(this::isActive)
                .collect(toMap(this::getEventTitle, Entry::getValue));
    }

    private boolean isActive(final Entry<GiftEventType, Integer> entry) {
        return entry.getValue() != ZERO;
    }

    private String getEventTitle(final Entry<GiftEventType, Integer> entry) {
        return entry.getKey().getTitle();
    }

    public Map<String, Integer> getActiveMenuCounts() {
        return giftAmounts.entrySet().stream()
                .filter(this::isActive)
                .collect(toMap(this::getMenuTitle, this::getMenuCount));
    }

    private String getMenuTitle(final Entry<GiftEventType, Integer> entry) {
        return entry.getKey().getMenuTitle();
    }

    private Integer getMenuCount(final Entry<GiftEventType, Integer> entry) {
        return entry.getKey().getCount();
    }

}
