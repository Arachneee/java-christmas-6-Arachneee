package christmas.domain.event.gift;

import static java.util.stream.Collectors.toMap;

import christmas.domain.event.EventRepository;
import java.util.Map;
import java.util.Map.Entry;

public class GiftRepository extends EventRepository<GiftEventType> {

    public Map<String, Integer> getActiveMenuCounts() {
        return eventAmounts.entrySet().stream()
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
