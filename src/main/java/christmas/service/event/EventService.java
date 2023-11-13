package christmas.service.event;

import christmas.domain.event.Event;
import christmas.domain.event.EventRepository;
import christmas.domain.order.Order;
import christmas.response.EventResponse;
import java.util.List;

public abstract class EventService<T extends Enum<T> & Event> {

    protected final EventRepository<T> eventRepository;

    public EventService(EventRepository<T> eventRepository) {
        this.eventRepository = eventRepository;
    }

    abstract void applyEventAll(final Order order);

    public List<EventResponse> getActiveEventResult() {
        return eventRepository.getActiveResult().entrySet().stream()
                .map(entry -> EventResponse.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    public int calculateTotalBenefits() {
        return eventRepository.calculateTotal();
    }
}
