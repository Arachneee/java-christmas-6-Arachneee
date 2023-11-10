package christmas.controller.convertor;

import static java.util.stream.Collectors.toMap;

import christmas.domain.order.constant.Menu;
import christmas.exception.OrderException;
import christmas.exception.constant.ErrorMessage;
import christmas.view.request.MenuCountRequest;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BinaryOperator;

public class MenuConvertor {

    private MenuConvertor() {
    }

    public static EnumMap<Menu, Integer> convertToEnumMap(final List<MenuCountRequest> menuCountRequests) {
        return menuCountRequests.stream()
                .collect(toMap(menuCountRequest -> Menu.from(menuCountRequest.title()),
                        MenuCountRequest::count,
                        throwingDuplicateOrderException(),
                        () -> new EnumMap<>(Menu.class)));
    }

    private static <T> BinaryOperator<T> throwingDuplicateOrderException() {
        return (menu, other) -> {
            throw OrderException.from(ErrorMessage.INVALID_ORDER);
        };
    }
}
