package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.day.Day;
import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventTest {

    @DisplayName("어떤 이벤트도 총 주문 금액이 10,000원이 넘지 않으면 할인이 적용되지 않는다.")
    @ParameterizedTest
    @MethodSource("eventProvider")
    void apply(Event event, int day) {
        // given
        Order order = Order.of(Day.from(day), Map.of(Menu.ZERO_COLA, 1,
                Menu.ICE_CREAM, 1));

        // when
        int apply = event.apply(order);

        // then
        assertThat(apply).isEqualTo(0);
    }

    static Stream<Arguments> eventProvider() {
        return Stream.of(arguments(new ChristmasEvent(), 25),
                arguments(new WeekendEvent(), 2),
                arguments(new WeekdayEvent(), 24),
                arguments(new GiftEvent(), 25),
                arguments(new SpecialEvent(), 25));
    }
}