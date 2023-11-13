package christmas.domain.order.constant;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.order.Day;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("주말은")
class WeekTest {

    @Nested
    @DisplayName("날짜가 주말인지 확인할 수 있다.")
    class IsWeekend {

        @DisplayName("True")
        @ParameterizedTest
        @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
        void isWeekendTrue(int day) {
            // given
            Day orderDay = Day.from(day);
            Week week = Week.from(DayOfWeek.from(orderDay));

            // when
            boolean weekend = week.isWeekend();

            // then
            assertThat(weekend).isTrue();
        }

        @DisplayName("False")
        @ParameterizedTest
        @ValueSource(ints = {3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 24, 25, 26, 27, 28, 31})
        void isWeekendFalse(int day) {
            // given
            Day orderDay = Day.from(day);
            Week week = Week.from(DayOfWeek.from(orderDay));

            // when
            boolean weekend = week.isWeekend();

            // then
            assertThat(weekend).isFalse();
        }
    }


}