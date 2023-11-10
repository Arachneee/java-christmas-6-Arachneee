package christmas.domain.day;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.order.constant.Category;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
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
        Category category = day.checkEventCategory();

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

    @DisplayName("다른 Day보다 큰지 판별할 수 있다. True")
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

    @DisplayName("다른 Day보다 큰지 판별할 수 있다. False")
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

    @DisplayName("크리스마스인지 판별할 수 있다. True")
    @Test
    void isChristmasDayTrue() {
        // given
        Day day = Day.from(25);

        // when
        boolean christmasDay = day.isChristmasDay();

        // then
        assertThat(christmasDay).isTrue();
    }

    @DisplayName("크리스마스인지 판별할 수 있다. False")
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

    @DisplayName("주말이면 true를 반환한다.")
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

    @DisplayName("주말이 아니면 false를 반환한다.")
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

    @DisplayName("일요일인지 확인 할 수 있다. True")
    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 31})
    void isSunDayTrue(int input) {
        // given
        Day day = Day.from(input);

        // when // then
        assertThat(day.isSunDay()).isTrue();
    }

    @DisplayName("일요일인지 확인 할 수 있다. False")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 5, 6, 7, 8, 9, 15, 25, 26})
    void isSunDayFalse(int input) {
        // given
        Day day = Day.from(input);

        // when // then
        assertThat(day.isSunDay()).isFalse();
    }
}