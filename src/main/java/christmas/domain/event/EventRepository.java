package christmas.domain.event;

import static java.util.stream.Collectors.toMap;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;


public abstract class EventRepository<T extends Enum<T> & Event> {

    private static final int ZERO = 0;
    protected EnumMap<T, Integer> eventAmounts;

    public void init(final EnumMap<T, Integer> eventAmounts) {
        this.eventAmounts = eventAmounts;
    }

    public int calculateTotal() {
        return eventAmounts.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Map<String, Integer> getActiveResult() {
        return eventAmounts.entrySet().stream().filter(this::isActive)
                .collect(toMap(this::getEventTitle, Entry::getValue));
    }

    protected boolean isActive(final Entry<T, Integer> entry) {
        return entry.getValue() != ZERO;
    }

    private String getEventTitle(final Entry<T, Integer> entry) {
        return entry.getKey().getTitle();
    }
}
