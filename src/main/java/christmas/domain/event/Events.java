package christmas.domain.event;

import christmas.domain.order.Order;
import christmas.domain.result.DiscountResult;
import christmas.domain.result.DiscountResults;
import java.util.ArrayList;
import java.util.List;

public class Events {

    private List<Event> events = new ArrayList<>();

    public Events() {
    }

    public void addEvent(final Event event) {
        events.add(event);
    }

    public DiscountResults discount(final Order order) {
        return DiscountResults.from(events.stream()
                .map(event -> DiscountResult.of(event.getTitle(order), event.apply(order)))
                .toList());
    }
}
