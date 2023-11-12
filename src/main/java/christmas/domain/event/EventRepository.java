package christmas.domain.event;

import java.util.ArrayList;
import java.util.List;


public abstract class EventRepository<T extends EventResult> {

    private List<T> eventResults = new ArrayList<>();
    public void init(List<T> eventResults) {
        this.eventResults = eventResults;
    }

    public int calculateTotal() {
        return eventResults.stream()
                .mapToInt(EventResult::getAmount)
                .sum();
    }

    public List<T> getActiveResult() {
        return eventResults.stream()
                .filter(EventResult::isActive)
                .toList();
    }
}
