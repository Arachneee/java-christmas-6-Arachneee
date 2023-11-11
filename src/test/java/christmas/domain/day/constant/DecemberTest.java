package christmas.domain.day.constant;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.order.day.December;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DecemberTest {

    @DisplayName("12월에 있는 일인지 확인 할 수 있다. True")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 5, 6, 7, 8, 9, 15, 25, 31})
    void isInRangeTrue(int day) {
        assertThat(December.isInRange(day)).isTrue();
    }

    @DisplayName("12월에 있는 일인지 확인 할 수 있다. False")
    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0, 32, 33, 57, 88, 999, 15000})
    void isInRangeFalse(int day) {
        assertThat(December.isInRange(day)).isFalse();
    }

}