package christmas.domain.event.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountTest {

    @DisplayName("할인 결과가 Type과 Amount로 생성된다.")
    @ParameterizedTest
    @MethodSource("discountProvider")
    void of(DiscountEventType type, int amount) {
        // when
        Discount discount = Discount.of(type, amount);

        // then
        assertThat(discount.getAmount()).isEqualTo(amount);
        assertThat(discount).extracting("discountEventType")
                .isEqualTo(type);

    }

    static Stream<Arguments> discountProvider() {
        return Stream.of(
                arguments(DiscountEventType.SPECIAL_DISCOUNT, 1000),
                arguments(DiscountEventType.WEEKDAY_DISCOUNT, 2023),
                arguments(DiscountEventType.WEEKEND_DISCOUNT, 0),
                arguments(DiscountEventType.CHRISTMAS_D_DAY_DISCOUNT, 2500)
        );
    }

    @DisplayName("할인 금액이 0원이 아닌지 확인할 수 있다. True")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,10000,9999999})
    void isActiveTrue(int input) {
        // given
        Discount discount = Discount.of(DiscountEventType.WEEKDAY_DISCOUNT, input);

        // when
        boolean notZero = discount.isActive();

        // then
        assertThat(notZero).isTrue();
    }

    @DisplayName("할인 금액이 0원이 아닌지 확인할 수 있다. False")
    @Test
    void isActiveFalse() {
        // given
        Discount discount = Discount.of(DiscountEventType.WEEKDAY_DISCOUNT, 0);

        // when
        boolean notZero = discount.isActive();

        // then
        assertThat(notZero).isFalse();
    }

    @DisplayName("할인 이벤트의 제목을 얻을 수 있다.")
    @ParameterizedTest
    @MethodSource("eventTitleProvider")
    void getEventTitle(DiscountEventType discountEventType, String target) {
        // given
        Discount discount = Discount.of(discountEventType, 10000);

        // when
        String title = discount.getEventTitle();

        // then
        assertThat(title).isEqualTo(target);
    }

    static Stream<Arguments> eventTitleProvider() {
        return Stream.of(
                arguments(DiscountEventType.SPECIAL_DISCOUNT, "특별 할인"),
                arguments(DiscountEventType.WEEKDAY_DISCOUNT, "평일 할인"),
                arguments(DiscountEventType.WEEKEND_DISCOUNT, "주말 할인"),
                arguments(DiscountEventType.CHRISTMAS_D_DAY_DISCOUNT, "크리스마스 디데이 할인")
        );
    }

}