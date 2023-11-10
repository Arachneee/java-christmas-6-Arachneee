package christmas.controller.dto;

import christmas.domain.day.Day;
import java.util.List;
import java.util.Map;

public record OrderDto(
        int day,
        List<MenuCountDto> menuCount,
        int totalPrice
) {

    public static OrderDto of(final Day day, final Map<String, Integer> nameCount, final int totalPrice) {
        final List<MenuCountDto> menuCountDtos = convertMenuCountDtos(nameCount);

        return new OrderDto(day.getValue(), menuCountDtos, totalPrice);
    }

    private static List<MenuCountDto> convertMenuCountDtos(final Map<String, Integer> nameCount) {
        return nameCount.entrySet().stream()
                .map(entry -> MenuCountDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }

}
