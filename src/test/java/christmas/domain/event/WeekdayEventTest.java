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

class WeekdayEventTest {

    WeekdayEvent weekDayDiscount = new WeekdayEvent();

    @DisplayName("평일에 디저트 메뉴 1개당 2023원 할인 합계를 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("weekDayMenuProvider")
    void calculateAmount(Map<Menu, Integer> menuCount, int discountTarget) {
        // given
        Day day = Day.from(3);
        Order order = Order.of(day, menuCount);

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

    @DisplayName("주말에 메인 메뉴 1개당 2023원 할인 합계를 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("weekendMenuProvider")
    void calculateAmountWeekend(Map<Menu, Integer> menuCount, int discountTarget) {
        // given
        Day day = Day.from(1);
        Order order = Order.of(day, menuCount);

        // when
        int discountAmount = weekDayDiscount.calculateAmount(order);

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
}