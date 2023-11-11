package christmas.response;

import java.util.List;

public record OrderResponse(
        int day,
        List<MenuCountResponse> menuCount
) {

    public static OrderResponse of(final int day, final List<MenuCountResponse> menuCount) {
        return new OrderResponse(day, menuCount);
    }

}
