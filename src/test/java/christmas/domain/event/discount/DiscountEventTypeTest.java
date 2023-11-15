package christmas.domain.event.discount;

import static christmas.domain.event.discount.DiscountEventType.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.SPECIAL_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.WEEKDAY_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.WEEKEND_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.event.Event;
import christmas.domain.order.Day;
import christmas.domain.order.Order;
import christmas.domain.order.constant.Menu;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("할인 이벤트 타입은")
class DiscountEventTypeTest {

    @Nested
    @DisplayName("공통 적으로")
    class Common {

        @DisplayName("할인 정책을 중복 적용할 수 있다.")
        @Test
        void discountAll() {
            // given
            Order order = Order.of(Day.from(25),
                    Map.of(Menu.T_BONE_STEAK, 1, Menu.ICE_CREAM, 2, Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.CHRISTMAS_PASTA,
                            2));

            // when
            EnumMap<DiscountEventType, Integer> discountEventTypeIntegerEnumMap = Event.applyAll(DiscountEventType.class, order);

            // then
            assertThat(discountEventTypeIntegerEnumMap)
                    .containsExactlyInAnyOrderEntriesOf(Map.of(CHRISTMAS_D_DAY_DISCOUNT, 3400,
                            WEEKDAY_DISCOUNT, 2023 * 2,
                            WEEKEND_DISCOUNT, 0,
                            SPECIAL_DISCOUNT, 1000));
        }

        @DisplayName("어떤 이벤트도 총 주문 금액이 10,000원이 넘지 않으면 할인이 적용되지 않는다.")
        @ParameterizedTest
        @MethodSource("eventProvider")
        void calculateBenefits(DiscountEventType event, int day) {
            // given
            Order order = Order.of(Day.from(day), Map.of(Menu.ZERO_COLA, 1,
                    Menu.ICE_CREAM, 1));

            // when
            int benefits = event.calculateBenefits(order);

            // then
            assertThat(benefits).isEqualTo(0);
        }

        static Stream<Arguments> eventProvider() {
            return Stream.of(arguments(CHRISTMAS_D_DAY_DISCOUNT, 25),
                    arguments(WEEKEND_DISCOUNT, 2),
                    arguments(WEEKDAY_DISCOUNT, 24),
                    arguments(SPECIAL_DISCOUNT, 25));
        }
    }

    @Nested
    @DisplayName("크리스마스 디데이 할인은")
    class ChristmasDDayDiscount {

        @DisplayName("날짜에 따른 할인 금액을 계산할 수 있다.")
        @ParameterizedTest
        @CsvSource(value = {"1,1000", "2,1100", "3,1200", "24,3300", "25,3400"}, delimiter = ',')
        void calculateAmount(int day, int discountTarget) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 1));

            // when
            int discountAmount = CHRISTMAS_D_DAY_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(discountTarget);
        }

        @DisplayName("주문 날짜가 25일 이내이면 할인이 적용된다.")
        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 15, 20, 23, 24, 25})
        void calculateBenefits(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 10));

            // when
            int discountAmount = CHRISTMAS_D_DAY_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo((day - 1) * 100 + 1000);
        }

        @DisplayName("주문 날짜가 25일을 넘어가면 할인 적용이 안된다.")
        @ParameterizedTest
        @ValueSource(ints = {26, 27, 28, 29, 30, 31})
        void calculateBenefitsOverDay(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 10));

            // when
            int discountAmount = CHRISTMAS_D_DAY_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("평일 할인은")
    class WeekdayDiscount {

        @DisplayName("평일에 디저트 메뉴 1개당 2023원 할인 합계를 계산할 수 있다.")
        @ParameterizedTest
        @MethodSource("weekDayMenuProvider")
        void calculateAmount(Map<Menu, Integer> menuCount, int discountTarget) {
            // given
            Day day = Day.from(3);
            Order order = Order.of(day, menuCount);

            // when
            int discountAmount = WEEKDAY_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(discountTarget);

        }

        static Stream<Arguments> weekDayMenuProvider() {
            return Stream.of(
                    arguments(Map.of(Menu.ICE_CREAM, 1, Menu.T_BONE_STEAK, 1), 2023),
                    arguments(Map.of(Menu.ICE_CREAM, 2), 2023 * 2),
                    arguments(Map.of(Menu.TAPAS, 1), 0),
                    arguments(Map.of(Menu.CAESAR_SALAD, 1, Menu.CHOCOLATE_CAKE, 10), 2023 * 10),
                    arguments(Map.of(Menu.ICE_CREAM, 3, Menu.CHOCOLATE_CAKE, 5), 2023 * 8),
                    arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.ZERO_COLA, 1), 0)
            );
        }

        @DisplayName("평일에 할인이 적용된다.")
        @ParameterizedTest
        @ValueSource(ints = {3, 4, 5, 6, 7,
                10, 11, 12, 13, 14,
                17, 18, 19, 20, 21,
                24, 25, 26, 27, 28,
                31})
        void calculateAmountWeekend(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 10));

            // when
            int discountAmount = WEEKDAY_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(2023 * 10);

        }

        @DisplayName("주말에 할인 적용이 불가능하다.")
        @ParameterizedTest
        @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
        void calculateBenefitsWeekday(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.ICE_CREAM, 3));

            // when
            int discountAmount = WEEKDAY_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("주말 할인은")
    class WeekendDiscount {

        @DisplayName("주말에 메인 메뉴 1개당 2023원 할인 합계를 계산할 수 있다.")
        @ParameterizedTest
        @MethodSource("weekendMenuProvider")
        void calculateBenefits(Map<Menu, Integer> menuCount, int discountTarget) {
            // given
            Day day = Day.from(1);
            Order order = Order.of(day, menuCount);

            // when
            int discountAmount = WEEKEND_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(discountTarget);

        }

        static Stream<Arguments> weekendMenuProvider() {
            return Stream.of(
                    arguments(Map.of(Menu.BARBECUE_RIBS, 1), 2023),
                    arguments(Map.of(Menu.T_BONE_STEAK, 2), 2023 * 2),
                    arguments(Map.of(Menu.ICE_CREAM, 1), 0),
                    arguments(Map.of(Menu.ICE_CREAM, 1, Menu.BARBECUE_RIBS, 10), 2023 * 10),
                    arguments(Map.of(Menu.BARBECUE_RIBS, 3, Menu.T_BONE_STEAK, 5), 2023 * 8),
                    arguments(Map.of(Menu.BUTTON_MUSHROOM_SOUP, 1, Menu.ZERO_COLA, 1), 0)
            );
        }

        @DisplayName("평일에 할인이 적용되지 않는다.")
        @ParameterizedTest
        @ValueSource(ints = {3, 4, 5, 6, 7,
                10, 11, 12, 13, 14,
                17, 18, 19, 20, 21,
                24, 25, 26, 27, 28,
                31})
        void weekendInWeekday(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 10));

            // when
            int discountAmount = WEEKEND_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(0);

        }

        @DisplayName("주말에 할인 적용된다.")
        @ParameterizedTest
        @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
        void weekendInWeekend(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 10));

            // when
            int discountAmount = WEEKEND_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discountAmount).isEqualTo(2023 * 10);
        }
    }

    @Nested
    @DisplayName("스페셜 할인은")
    class SpecialDiscount {

        @DisplayName("별이 있는 달에 1000원 할인된다.")
        @ParameterizedTest
        @ValueSource(ints = {3, 10, 17, 24, 25, 31})
        void calculateBenefitsAtStarDay(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 1));

            // when
            int discount = SPECIAL_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discount).isEqualTo(1000);
        }

        @DisplayName("별이 없는 달에 할인 불가하다.")
        @ParameterizedTest
        @ValueSource(ints = {1, 2,
                4, 5, 6, 7, 8, 9,
                11, 12, 13, 14, 15, 16,
                18, 19, 20, 21, 22, 23,
                26, 27, 28, 29, 30})
        void calculateBenefitsAtNonStarDay(int day) {
            // given
            Day orderDay = Day.from(day);
            Order order = Order.of(orderDay, Map.of(Menu.T_BONE_STEAK, 1));

            // when
            int discount = SPECIAL_DISCOUNT.calculateBenefits(order);

            // then
            assertThat(discount).isEqualTo(0);
        }
    }

}