package christmas.service.order;

import christmas.domain.order.Order;
import christmas.domain.order.menu.Menu;
import christmas.response.EventDetailResponse;
import christmas.response.MenuCountResponse;
import christmas.response.OrderResponse;
import christmas.response.OrderSummaryResponse;
import christmas.service.event.EventService;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final EventService eventService;

    public OrderService(final EventService eventService) {
        this.eventService = eventService;
    }

    public OrderSummaryResponse createOrderSummary(final Order order) {
        eventService.createEvent(order);

        final OrderResponse orderResponse = createOrderResponse(order);
        final EventDetailResponse eventDetailResponse = createEventDetailResponse(order);

        return OrderSummaryResponse.of(orderResponse, eventDetailResponse);
    }

    private OrderResponse createOrderResponse(final Order order) {
        return OrderResponse.of(order.getDayInt(), convertMenuCounts(order.getMenuCount()));
    }

    private List<MenuCountResponse> convertMenuCounts(final Map<Menu, Integer> menuCount) {
        return menuCount.entrySet().stream()
                .map(entry -> MenuCountResponse.of(entry.getKey().getTitle(), entry.getValue()))
                .toList();
    }

    private EventDetailResponse createEventDetailResponse(final Order order) {
        final int priceBeforeDiscount = order.calculateTotalPrice();

        return eventService.getEventDetail(priceBeforeDiscount);
    }
}
