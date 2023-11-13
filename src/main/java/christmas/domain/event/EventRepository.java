package christmas.domain.event;

import static java.util.stream.Collectors.toMap;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;


public abstract class EventRepository<T extends Enum<T> & Event> {

    protected EnumMap<T, Integer> eventAmounts;

    int ZERO = 0;

    public void init(EnumMap<T, Integer> eventAmounts) {
        this.eventAmounts = eventAmounts;
    }

    public int calculateTotal() {
        return eventAmounts.values().stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public Map<String, Integer> getActiveResult() {
        return eventAmounts.entrySet().stream()
                .filter(this::isActive)
                .collect(toMap(this::getEventTitle, Entry::getValue));
    }

    protected boolean isActive(final Entry<T, Integer> entry) {
        return entry.getValue() != ZERO;
    }

    private String getEventTitle(final Entry<T, Integer> entry) {
        return entry.getKey().getTitle();
    }
}
