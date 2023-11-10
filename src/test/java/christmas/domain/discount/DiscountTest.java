package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.domain.event.constant.EventType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountTest {

    @DisplayName("할인 이벤트인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("eventTypeProvider")
    void isDiscountTrue(EventType eventType, boolean target) {
        // given
        Discount discount = Discount.of(eventType, 1000);

        // when
        boolean isDiscount = discount.isDiscount();

        // then
        assertThat(isDiscount).isEqualTo(target);
    }

    static Stream<Arguments> eventTypeProvider() {
        return Stream.of(
                arguments(EventType.SPECIAL_DISCOUNT, true),
                arguments(EventType.WEEKDAY_DISCOUNT, true),
                arguments(EventType.WEEKEND_DISCOUNT, true),
                arguments(EventType.CHRISTMAS_D_DAY_DISCOUNT, true),
                arguments(EventType.GIFT, false)
        );
    }

    @DisplayName("할인 이벤트인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("eventTypeProvider")
    void isGiftEvent(EventType eventType, boolean target) {
        // given
        Discount discount = Discount.of(eventType, 1000);

        // when
        boolean isDiscount = discount.isGiftEvent();

        // then
        assertThat(isDiscount).isEqualTo(!target);
    }

    @DisplayName("할인 금액이 0원이 아닌지 확인할 수 있다. True")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,10000,9999999})
    void isNotZeroTrue(int input) {
        // given
        Discount discount = Discount.of(EventType.WEEKDAY_DISCOUNT, input);

        // when
        boolean notZero = discount.isNotZero();

        // then
        assertThat(notZero).isTrue();
    }

    @DisplayName("할인 금액이 0원이 아닌지 확인할 수 있다. False")
    @Test
    void isNotZeroFalse() {
        // given
        Discount discount = Discount.of(EventType.WEEKDAY_DISCOUNT, 0);

        // when
        boolean notZero = discount.isNotZero();

        // then
        assertThat(notZero).isFalse();
    }

    @DisplayName("할인 이벤트의 제목을 얻을 수 있다.")
    @ParameterizedTest
    @MethodSource("eventTitleProvider")
    void getTitle(EventType eventType, String target) {
        // given
        Discount discount = Discount.of(eventType, 10000);

        // when
        String title = discount.getTitle();

        // then
        assertThat(title).isEqualTo(target);
    }

    static Stream<Arguments> eventTitleProvider() {
        return Stream.of(
                arguments(EventType.SPECIAL_DISCOUNT, "특별 할인"),
                arguments(EventType.WEEKDAY_DISCOUNT, "평일 할인"),
                arguments(EventType.WEEKEND_DISCOUNT, "주말 할인"),
                arguments(EventType.CHRISTMAS_D_DAY_DISCOUNT, "크리스마스 디데이 할인"),
                arguments(EventType.GIFT, "증정 이벤트")
        );
    }
}