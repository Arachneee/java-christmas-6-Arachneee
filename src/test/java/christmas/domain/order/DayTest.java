package christmas.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.order.constant.Category;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("날짜는")
class DayTest {

    @Nested
    @DisplayName("입력 범위가 1~31일 때만 생성된다.")
    class Create {

        @DisplayName("정상 생성된다.")
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

        @DisplayName("예외가 나온다.")
        @ParameterizedTest
        @ValueSource(ints = {-1, -2, -3, 0, 32, 33, 34})
        void fromOutOfRange(int day) {
            assertThatThrownBy(() -> Day.from(day))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
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
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 10, 20, 23, 30, 31})
    void mod(int day) {
        // given
        Day orderDay = Day.from(day);

        // when
        Integer mod = orderDay.mod(7);

        // then
        assertThat(mod).isEqualTo(day % 7);
    }

    @DisplayName("주문 일자의 이벤트 메뉴 카테고리를 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("eventCategoryProvider")
    void checkEventCategory(int input, Category target) {
        // given
        Day day = Day.from(input);

        // when
        Category category = day.getTodayEventCategory();

        // then
        assertThat(category).isEqualByComparingTo(target);
    }

    static Stream<Arguments> eventCategoryProvider() {
        return Stream.of(
                arguments(1, Category.MAIN),
                arguments(2, Category.MAIN),
                arguments(8, Category.MAIN),
                arguments(23, Category.MAIN),
                arguments(29, Category.MAIN),
                arguments(30, Category.MAIN),
                arguments(3, Category.DESSERT),
                arguments(4, Category.DESSERT),
                arguments(5, Category.DESSERT),
                arguments(10, Category.DESSERT),
                arguments(21, Category.DESSERT),
                arguments(25, Category.DESSERT),
                arguments(28, Category.DESSERT),
                arguments(31, Category.DESSERT)
        );
    }

    @Nested
    @DisplayName("다른 날짜보다 큰지 판별 할 수 있다.")
    class IsOverThan {

        @DisplayName("True")
        @ParameterizedTest
        @CsvSource(value = {"1,2", "1,31", "2,10", "24,25", "30,31"}, delimiter = ',')
        void isOverThanTrue(int left, int right) {
            // given
            Day leftDay = Day.from(left);
            Day rightDay = Day.from(right);

            // when
            boolean over = rightDay.isOverThan(leftDay);

            // then
            assertThat(over).isTrue();
        }

        @DisplayName("False")
        @ParameterizedTest
        @CsvSource(value = {"2,1", "31,1", "25,1", "25,25", "25,24", "31,31"}, delimiter = ',')
        void isOverThanFalse(int left, int right) {
            // given
            Day leftDay = Day.from(left);
            Day rightDay = Day.from(right);

            // when
            boolean over = rightDay.isOverThan(leftDay);

            // then
            assertThat(over).isFalse();
        }
    }

    @Nested
    @DisplayName("크리스마스인지 판별 할 수 있다.")
    class IsChristmasDay {

        @DisplayName("True")
        @Test
        void isChristmasDayTrue() {
            // given
            Day day = Day.from(25);

            // when
            boolean christmasDay = day.isChristmasDay();

            // then
            assertThat(christmasDay).isTrue();
        }

        @DisplayName("False")
        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5, 10, 24, 26, 29, 30, 31})
        void isChristmasDayFalse(int input) {
            // given
            Day day = Day.from(input);

            // when
            boolean christmasDay = day.isChristmasDay();

            // then
            assertThat(christmasDay).isFalse();
        }
    }


    @Nested
    @DisplayName("주말인지 판별 할 수 있다.")
    class IsWeekend {

        @DisplayName("True")
        @ParameterizedTest
        @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
        void isWeekendTrue(int day) {
            // given
            Day orderDay = Day.from(day);

            // when
            boolean weekend = orderDay.isWeekend();

            // then
            assertThat(weekend).isTrue();
        }

        @DisplayName("False")
        @ParameterizedTest
        @ValueSource(ints = {3, 4, 5, 6, 7,
                10, 11, 12, 13, 14,
                17, 18, 19, 20, 21,
                24, 25, 26, 27, 28,
                31})
        void isWeekendFalse(int day) {
            // given
            Day orderDay = Day.from(day);

            // when
            boolean weekend = orderDay.isWeekend();

            // then
            assertThat(weekend).isFalse();
        }
    }

    @Nested
    @DisplayName("일요일인지 판별 할 수 있다.")
    class IsSunDay {

        @DisplayName("True")
        @ParameterizedTest
        @ValueSource(ints = {3, 10, 17, 24, 31})
        void isSunDayTrue(int input) {
            // given
            Day day = Day.from(input);

            // when // then
            assertThat(day.isSunDay()).isTrue();
        }

        @DisplayName("False")
        @ParameterizedTest
        @ValueSource(ints = {1, 2, 4, 5, 6, 7, 8, 9, 15, 25, 26})
        void isSunDayFalse(int input) {
            // given
            Day day = Day.from(input);

            // when // then
            assertThat(day.isSunDay()).isFalse();
        }
    }

    @Nested
    @DisplayName("날짜가 같은지 판별할 수 있다.")
    class Equals {

        @DisplayName("True")
        @Test
        void equalsTrue() {
            // given
            Day day = Day.from(1);
            Day other = Day.from(1);

            // when
            boolean equals = day.equals(other);

            // then
            assertThat(equals).isTrue();
        }

        @DisplayName("False")
        @Test
        void equalsFalse() {
            // given
            Day day = Day.from(1);
            Day other = Day.from(2);

            // when
            boolean equals = day.equals(other);

            // then
            assertThat(equals).isFalse();
        }

        @DisplayName("False - Other Type")
        @Test
        void equalsFalse2() {
            // given
            Day day = Day.from(1);
            Integer other = 1;

            // when
            boolean equals = day.equals(other);

            // then
            assertThat(equals).isFalse();
        }
    }

    @Nested
    @DisplayName("같은 날짜이면 hashCode가 같다.")
    class Hash {

        @DisplayName("True")
        @Test
        void hashCodeEqualTrue() {
            // given // when
            int hash = Day.from(1).hashCode();
            int other = Day.from(1).hashCode();

            // then
            assertThat(hash == other).isTrue();
        }

        @DisplayName("False")
        @Test
        void hashCodeEqualFalse() {
            // given // when
            int hash = Day.from(1).hashCode();
            int other = Day.from(2).hashCode();

            // then
            assertThat(hash == other).isFalse();
        }
    }


}