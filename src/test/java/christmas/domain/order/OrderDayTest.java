package christmas.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderDayTest {

    @DisplayName("날짜의 범위는 1 ~ 31 이면 정상 생성된다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 20, 30, 31})
    void from(int day) {
        // given // when
        OrderDay from = OrderDay.from(day);

        // then
        assertThat(from)
                .extracting("day")
                .isEqualTo(day);
    }

    @DisplayName("날짜의 범위는 1 ~ 31 이 아니면 예외가 나온다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, 0, 32, 33, 34})
    void fromOutOfRange(int day) {
        assertThatThrownBy(() -> OrderDay.from(day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @DisplayName("날짜 차이를 계산할 수 있다.")
    @Test
    void gap() {
        // given
        OrderDay orderDay1 = OrderDay.from(1);
        OrderDay orderDay2 = OrderDay.from(10);

        // when
        int gap = orderDay2.gap(orderDay1);

        // then
        assertThat(gap).isEqualTo(9);
    }

    @DisplayName("주말이면 true를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    void isWeekendTrue(int day) {
        // given
        OrderDay orderDay = OrderDay.from(day);

        // when
        boolean weekend = orderDay.isWeekend();

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

        // when
        boolean weekend = orderDay.isWeekend();

        // then
        assertThat(weekend).isFalse();
    }

}