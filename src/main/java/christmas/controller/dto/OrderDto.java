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
        final List<MenuCountDto> menuCountDtos = convertMenuCounts(order.getMenuCount());

        return new OrderDto(day, menuCountDtos);
    }

    private static List<MenuCountDto> convertMenuCounts(final Map<Menu, Integer> nameCount) {
        return nameCount.entrySet().stream()
                .map(entry -> MenuCountDto.of(entry.getKey().getTitle(), entry.getValue()))
                .toList();
    }

}
