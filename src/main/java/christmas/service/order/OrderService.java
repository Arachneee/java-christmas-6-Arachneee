package christmas.service.order;

import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import christmas.response.EventDetailResponse;
import christmas.response.MenuCountResponse;
import christmas.response.OrderResponse;
import christmas.response.OrderSummaryResponse;
import christmas.service.event.EventDetailService;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OrderService {

    private final EventDetailService eventDetailService;

    public OrderService(final EventDetailService eventDetailService) {
        this.eventDetailService = eventDetailService;
    }

    public OrderSummaryResponse createOrderSummary(final Order order) {
        eventDetailService.createEvent(order);

        final OrderResponse orderResponse = createOrderResponse(order);
        final EventDetailResponse eventDetailResponse = createEventDetailResponse(order);

        return OrderSummaryResponse.of(orderResponse, eventDetailResponse);
    }

    private OrderResponse createOrderResponse(final Order order) {
        return OrderResponse.of(order.getDayInt(), convertMenuCounts(order.getMenuCount()));
    }

    private List<MenuCountResponse> convertMenuCounts(final Map<Menu, Integer> menuCount) {
        return menuCount.entrySet().stream()
                .map(this::createMenuCountResponse)
                .toList();
    }

    private MenuCountResponse createMenuCountResponse(final Entry<Menu, Integer> entry) {
        return MenuCountResponse.of(entry.getKey().getTitle(), entry.getValue());
    }

    private EventDetailResponse createEventDetailResponse(final Order order) {
        final int priceBeforeDiscount = order.calculateTotalPrice();

        return eventDetailService.getEventDetail(priceBeforeDiscount);
    }
}
