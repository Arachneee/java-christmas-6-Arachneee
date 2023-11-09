package christmas.domain.event;

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

class WeekendEventTest {
    WeekEvent weekendDiscount = new WeekEvent();


    @DisplayName("주말에 메인 메뉴 1개당 2023원 할인 합계를 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("weekendMenuProvider")
    void calculateAmount(Map<Menu, Integer> menuCount, int discountTarget) {
        // given
        OrderDay orderDay = OrderDay.from(1);
        Order order = Order.of(orderDay, menuCount);

        // when
        int discountAmount = weekendDiscount.calculateAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(discountTarget);

    }

    static Stream<Arguments> weekendMenuProvider() {
        return Stream.of(
                arguments(Map.of(Menu.BARBECUE_RIBS, 1), 2023),
                arguments(Map.of(Menu.T_BONE_STEAK, 2), 2023 * 2),
                arguments(Map.of(Menu.ICE_CREAM, 1), 0),
                arguments(Map.of(Menu.ICE_CREAM, 1, Menu.BARBECUE_RIBS, 10), 2023 * 10),
                arguments(Map.of(Menu.BARBECUE_RIBS, 3, Menu.T_BONE_STEAK, 5), 2023 * 8),
                arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.ZERO_COLA, 1), 0)
        );
    }

    @DisplayName("주말에 할인 적용이 불가능하다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7,
            10, 11, 12, 13, 14,
            17, 18, 19, 20, 21,
            24, 25, 26, 27, 28,
            31})
    void applyWeekDay(int day) {
        // given
        OrderDay orderDay = OrderDay.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 3));

        // when
        int discountAmount = weekendDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }

    @DisplayName("주말에는 할인 가능하다.")
    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    void applyWeekend(int day) {
        // given
        OrderDay orderDay = OrderDay.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 3));

        // when
        int discountAmount = weekendDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(2023 * 3);
    }

}