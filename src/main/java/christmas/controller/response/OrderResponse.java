package christmas.controller.response;

import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.List;
import java.util.Map;

public record OrderResponse(
        int day,
        List<MenuCountResponse> menuCount
) {

    public static OrderResponse from(final Order order) {
        final int day = order.getDayInt();
        final List<MenuCountResponse> menuCounts = convertMenuCounts(order.getMenuCount());

        return new OrderResponse(day, menuCounts);
    }

    private static List<MenuCountResponse> convertMenuCounts(final Map<Menu, Integer> menuCount) {
        return menuCount.entrySet().stream()
                .map(entry -> MenuCountResponse.of(entry.getKey().getTitle(), entry.getValue()))
                .toList();
    }

}
