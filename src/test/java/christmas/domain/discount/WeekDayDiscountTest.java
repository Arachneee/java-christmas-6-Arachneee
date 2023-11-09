package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constant.Menu;
import christmas.domain.order.Order;
import christmas.domain.order.OrderDay;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class WeekDayDiscountTest {

    @DisplayName("평일에 디저트 메뉴 1개당 2023원 할인 합계를 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("weekDayMenuProvider")
    void calculateAmount(Map<Menu, Integer> menuCount, int discountTarget) {
        // given
        WeekDayDiscount weekDayDiscount = new WeekDayDiscount();
        OrderDay orderDay = OrderDay.from(1);
        Order order = Order.of(orderDay, menuCount);

        // when
        int discountAmount = weekDayDiscount.calculateAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(discountTarget);

    }

    static Stream<Arguments> weekDayMenuProvider() {
        return Stream.of(
                arguments(Map.of(Menu.ICE_CREAM, 1), 2023),
                arguments(Map.of(Menu.ICE_CREAM, 2), 2023 * 2),
                arguments(Map.of(Menu.TAPAS, 1), 0),
                arguments(Map.of(Menu.CAESAR_SALAD, 1, Menu.CHOCOLATE_CAKE, 10), 2023 * 10),
                arguments(Map.of(Menu.ICE_CREAM, 3, Menu.CHOCOLATE_CAKE, 5), 2023 * 8),
                arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.ZERO_COLA, 1), 0)
        );
    }

    @DisplayName("평일에 할인 가능하다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7,
            10, 11, 12, 13, 14,
            17, 18, 19, 20, 21,
            24, 25, 26, 27, 28,
            31})
    void applyWeekDay(int day) {
        // given
        WeekDayDiscount weekDayDiscount = new WeekDayDiscount();
        OrderDay orderDay = OrderDay.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 3));

        // when
        int discountAmount = weekDayDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(2023 * 3);
    }

    @DisplayName("주말에는 할인이 불가능하다.")
    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    void applyWeekend(int day) {
        // given
        WeekDayDiscount weekDayDiscount = new WeekDayDiscount();
        OrderDay orderDay = OrderDay.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 3));

        // when
        int discountAmount = weekDayDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }
}