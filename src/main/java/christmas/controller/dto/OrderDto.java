package christmas.controller.dto;

import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.List;
import java.util.Map;

public record OrderDto(
        int day,
        List<MenuCountDto> menuCount
) {

    public static OrderDto from(final Order order) {
        final int day = order.getDayInt();
        final List<MenuCountDto> menuCounts = convertMenuCounts(order.getMenuCount());

        return new OrderDto(day, menuCounts);
    }

    private static List<MenuCountDto> convertMenuCounts(final Map<Menu, Integer> menuCount) {
        return menuCount.entrySet().stream()
                .map(entry -> MenuCountDto.of(entry.getKey().getTitle(), entry.getValue()))
                .toList();
    }

}
