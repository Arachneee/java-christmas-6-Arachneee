package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.order.Menu;
import christmas.domain.order.Order;
import christmas.domain.day.Day;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ChristmasEventTest {

    @DisplayName("날짜에 따른 할인 금액을 계산할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"1,1000", "2,1100", "3,1200", "24,3300", "25,3400"}, delimiter = ',')
    void calculateAmount(int day, int discountTarget) {
        // given
        ChristmasEvent christmasDiscount = new ChristmasEvent();
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 1));

        // when
        int discountAmount = christmasDiscount.calculateAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(discountTarget);
    }

    @DisplayName("주문의 총 금액이 10000뭔 이상이어야 할인을 적용한다.")
    @ParameterizedTest
    @MethodSource("underMenuProvider")
    void apply(Map<Menu, Integer> menuCount) {
        // given
        ChristmasEvent christmasDiscount = new ChristmasEvent();
        Day day = Day.from(1);
        Order order = Order.of(day, menuCount);

        // when
        int discountAmount = christmasDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }

    static Stream<Arguments> underMenuProvider() {
        return Stream.of(
                arguments(Map.of(Menu.ICE_CREAM, 1)),
                arguments(Map.of(Menu.TAPAS, 1)),
                arguments(Map.of(Menu.CAESAR_SALAD, 1)),
                arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 1)),
                arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.ZERO_COLA, 1))
        );
    }

    @DisplayName("주문 날짜가 25일을 넘어가면 할인 적용이 안된다.")
    @ParameterizedTest
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    void applyOverDay(int day) {
        // given
        ChristmasEvent christmasDiscount = new ChristmasEvent();
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 1));

        // when
        int discountAmount = christmasDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }

}