package christmas.service.event;

import christmas.domain.event.Event;
import christmas.domain.event.EventRepository;
import christmas.domain.order.Order;
import christmas.response.EventResponse;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

public abstract class EventService<T extends Enum<T> & Event> {

    protected final EventRepository<T> eventRepository;

    public EventService(final EventRepository<T> eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void applyEventAll(final Class<T> eventType, final Order order) {
        final EnumMap<T, Integer> giftAmounts = Event.applyAll(eventType, order);
        eventRepository.init(giftAmounts);
    }

    public List<EventResponse> getActiveEventResult() {
        return eventRepository.getActiveResult().entrySet().stream()
                .map(this::createEventResponse)
                .toList();
    }

    private EventResponse createEventResponse(final Entry<String, Integer> entry) {
        return EventResponse.of(entry.getKey(), entry.getValue());
    }

    public int calculateTotalBenefits() {
        return eventRepository.calculateTotal();
    }
}
