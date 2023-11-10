package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.order.constant.Menu;
import christmas.domain.order.Order;
import christmas.domain.day.Day;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class WeekdayEventTest {

    WeekdayEvent weekdayDiscount = new WeekdayEvent();

    @DisplayName("평일에 디저트 메뉴 1개당 2023원 할인 합계를 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("weekDayMenuProvider")
    void calculateAmount(Map<String, Integer> menuCount, int discountTarget) {
        // given
        Day day = Day.from(3);
        Order order = Order.of(day, menuCount);

        // when
        int discountAmount = weekdayDiscount.calculateAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(discountTarget);

    }

    static Stream<Arguments> weekDayMenuProvider() {
        return Stream.of(
                arguments(Map.of(Menu.ICE_CREAM.getTitle(), 1), 2023),
                arguments(Map.of(Menu.ICE_CREAM.getTitle(), 2), 2023 * 2),
                arguments(Map.of(Menu.TAPAS.getTitle(), 1), 0),
                arguments(Map.of(Menu.CAESAR_SALAD.getTitle(), 1, Menu.CHOCOLATE_CAKE.getTitle(), 10), 2023 * 10),
                arguments(Map.of(Menu.ICE_CREAM.getTitle(), 3, Menu.CHOCOLATE_CAKE.getTitle(), 5), 2023 * 8),
                arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP.getTitle(), 1, Menu.ZERO_COLA.getTitle(), 1), 0)
        );
    }

    @DisplayName("평일에 할인이 적용된다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7,
            10, 11, 12, 13, 14,
            17, 18, 19, 20, 21,
            24, 25, 26, 27, 28,
            31})
    void calculateAmountWeekend(int day) {
        // given
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM.getTitle(), 10));

        // when
        int discountAmount = weekdayDiscount.calculateAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(2023 * 10);

    }

    @DisplayName("주말에 할인 적용이 불가능하다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    void applyWeekDay(int day) {
        // given
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM.getTitle(), 3));

        // when
        int discountAmount = weekdayDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }
}