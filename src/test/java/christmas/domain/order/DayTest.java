package christmas.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DayTest {

    @DisplayName("날짜의 범위는 1 ~ 31 이면 정상 생성된다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 20, 30, 31})
    void from(int day) {
        // given // when
        Day from = Day.from(day);

        // then
        assertThat(from)
                .extracting("day")
                .isEqualTo(day);
    }

    @DisplayName("날짜의 범위는 1 ~ 31 이 아니면 예외가 나온다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, 0, 32, 33, 34})
    void fromOutOfRange(int day) {
        assertThatThrownBy(() -> Day.from(day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @DisplayName("날짜 차이를 계산할 수 있다.")
    @Test
    void gap() {
        // given
        Day day1 = Day.from(1);
        Day day2 = Day.from(10);

        // when
        int gap = day2.gap(day1);

        // then
        assertThat(gap).isEqualTo(9);
    }

    @DisplayName("날짜의 나머지를 반환할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,10,20,23,30,31})
    void mod(int day) {
        // given
        Day orderDay = Day.from(day);

        // when
        Integer mod = orderDay.mod(7);

        // then
        assertThat(mod).isEqualTo(day % 7);
    }

}