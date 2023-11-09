package christmas.constant;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.order.OrderDay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WeekTest {

    @DisplayName("주말이면 true를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    void isWeekendTrue(int day) {
        // given
        OrderDay orderDay = OrderDay.from(day);
        Week week = Week.from(orderDay);

        // when
        boolean weekend = week.isWeekend();

        // then
        assertThat(weekend).isTrue();
    }

    @DisplayName("주말이 아니면 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7,
            10, 11, 12, 13, 14,
            17, 18, 19, 20, 21,
            24, 25, 26, 27, 28,
            31})
    void isWeekendFalse(int day) {
        // given
        OrderDay orderDay = OrderDay.from(day);
        Week week = Week.from(orderDay);

        // when
        boolean weekend = week.isWeekend();

        // then
        assertThat(weekend).isFalse();
    }
}