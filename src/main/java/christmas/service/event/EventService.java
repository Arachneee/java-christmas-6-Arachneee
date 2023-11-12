package christmas.service.event;

import christmas.domain.event.EventRepository;
import christmas.domain.event.EventResult;
import christmas.domain.order.Order;
import christmas.response.EventResponse;
import java.util.List;

public abstract class EventService<T extends EventResult> {

    protected final EventRepository<T> eventRepository;

    public EventService(EventRepository<T> eventRepository) {
        this.eventRepository = eventRepository;
    }

    abstract void applyEventAll(final Order order);

    public List<EventResponse> getActiveEventResult() {
        return eventRepository.getActiveResult().stream()
                .map(this::createEventResult)
                .toList();
    }

    public int calculateTotalBenefits() {
        return eventRepository.calculateTotal();
    }

    private EventResponse createEventResult(final T eventResult) {
        return EventResponse.of(eventResult.getEventTitle(), eventResult.getAmount());
    }
}
