package christmas.domain.order.constant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.order.Day;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class DayOfWeekTest {

    @DisplayName("Day의 요일을 구분할 수 있다.")
    @ParameterizedTest
    @MethodSource("dayOfWeekProvider")
    void from(int input, DayOfWeek target) {
        // given
        Day day = Day.from(input);

        // when
        DayOfWeek dayOfWeek = DayOfWeek.from(day);

        // then
        assertThat(dayOfWeek).isEqualByComparingTo(target);
    }

    static Stream<Arguments> dayOfWeekProvider() {
        return Stream.of(
                arguments(1, DayOfWeek.FRIDAY),
                arguments(8, DayOfWeek.FRIDAY),
                arguments(2, DayOfWeek.SATURDAY),
                arguments(3, DayOfWeek.SUNDAY),
                arguments(4, DayOfWeek.MONDAY),
                arguments(5, DayOfWeek.TUESDAY),
                arguments(6, DayOfWeek.WEDNESDAY),
                arguments(7, DayOfWeek.THURSDAY),
                arguments(25, DayOfWeek.MONDAY)
        );
    }

    @DisplayName("일요일인지 확인 할 수 있다. True")
    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 31})
    void isSunDayTrue(int input) {
        // given
        Day day = Day.from(input);

        // when
        DayOfWeek dayOfWeek = DayOfWeek.from(day);

        // then
        assertThat(dayOfWeek.isSunDay()).isTrue();
    }

    @DisplayName("일요일인지 확인 할 수 있다. False")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 5, 6, 7, 8, 9, 15, 25, 26})
    void isSunDayFalse(int input) {
        // given
        Day day = Day.from(input);

        // when
        DayOfWeek dayOfWeek = DayOfWeek.from(day);

        // then
        assertThat(dayOfWeek.isSunDay()).isFalse();
    }

    @DisplayName("주말인지 확인 할 수 있다. True")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    void isWeekendTrue(int input) {
        // given
        Day day = Day.from(input);

        // when
        DayOfWeek dayOfWeek = DayOfWeek.from(day);

        // then
        assertThat(dayOfWeek.isWeekend()).isTrue();
    }

    @DisplayName("주말인지 확인 할 수 있다. False")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7, 10, 11, 12, 13, 19, 20, 21, 24, 25, 31})
    void isWeekendFalse(int input) {
        // given
        Day day = Day.from(input);

        // when
        DayOfWeek dayOfWeek = DayOfWeek.from(day);

        // then
        assertThat(dayOfWeek.isWeekend()).isFalse();
    }

}