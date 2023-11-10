package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.order.constant.Menu;
import christmas.domain.order.Order;
import christmas.domain.day.Day;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StarEventTest {

    SpecialEvent specialDiscount = new SpecialEvent();

    @DisplayName("별이 있는 달에 1000원 할인")
    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    void applyStar(int day) {
        // given
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 1));

        // when
        int discount = specialDiscount.apply(order);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    @DisplayName("별이 없는 달에 할인 불가")
    @ParameterizedTest
    @ValueSource(ints = {1, 2,
            4, 5, 6, 7, 8, 9,
            11, 12, 13, 14, 15, 16,
            18, 19, 20, 21, 22, 23,
            26, 27, 28, 29, 30})
    void applyNonStar(int day) {
        // given
        Day orderDay = Day.from(day);
        Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 1));

        // when
        int discount = specialDiscount.apply(order);

        // then
        assertThat(discount).isEqualTo(0);
    }

}