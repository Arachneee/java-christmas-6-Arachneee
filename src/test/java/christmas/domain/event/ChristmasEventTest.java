package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.day.Day;
import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @DisplayName("주문 날짜가 25일 이내이면 할인이 적용된다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 15, 20, 23, 24, 25})
    void apply(int day) {
        // given
        ChristmasEvent christmasDiscount = new ChristmasEvent();
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 10));

        // when
        int discountAmount = christmasDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo((day - 1) * 100 + 1000);
    }

    @DisplayName("주문 날짜가 25일을 넘어가면 할인 적용이 안된다.")
    @ParameterizedTest
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    void applyOverDay(int day) {
        // given
        ChristmasEvent christmasDiscount = new ChristmasEvent();
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 10));

        // when
        int discountAmount = christmasDiscount.apply(order);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }

}