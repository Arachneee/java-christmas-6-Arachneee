package christmas.domain.event;

import christmas.domain.order.Order;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.stream.Collectors;


public interface Event {

    int calculateBenefits(final Order order);

    String getTitle();

    static <T extends Enum<T> & Event> EnumMap<T, Integer> applyAll(final Class<T> eventType, final Order order) {

        return Arrays.stream(eventType.getEnumConstants())
                .collect(Collectors.toMap(
                        event -> event,
                        event -> event.calculateBenefits(order),
                        Integer::sum,
                        () -> new EnumMap<>(eventType)
                ));
    }
}
