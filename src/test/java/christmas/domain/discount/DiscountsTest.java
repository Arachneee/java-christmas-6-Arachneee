package christmas.domain.discount;

import static christmas.domain.event.constant.EventType.WEEKDAY_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.event.constant.EventType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountsTest {

    Discounts discounts = Discounts.of(List.of(Discount.of(EventType.SPECIAL_DISCOUNT, 1_000),
            Discount.of(EventType.CHRISTMAS_D_DAY_DISCOUNT, 2_400),
            Discount.of(WEEKDAY_DISCOUNT, 2_023),
            Discount.of(EventType.GIFT, 25_000)
            ), 50_000);

    @DisplayName("전체 할인 금액을 계산할 수 있다.")
    @Test
    void calculateTotal() {
        // given // when
        int totalDiscount = discounts.calculateTotal();

        // then
        assertThat(totalDiscount).isEqualTo(1000 + 2400 + 2023 + 25000);
    }

    @DisplayName("할인 후 금액을 계산할 수 있다.")
    @Test
    void calculatePriceAfter() {
        // given // when
        int priceAfter = discounts.calculatePriceAfter();

        // then
        assertThat(priceAfter).isEqualTo(50000 - (1000 + 2400 + 2023));
    }

    @DisplayName("증정품 이벤트의 수를 구할 수 있다.")
    @Test
    void countGift() {
        // given // when
        int countGift = discounts.countGift();

        // then
        assertThat(countGift).isEqualTo(1);
    }

    @DisplayName("getter로 조회할 List가 변경 불가이다.")
    @Test
    void getDiscounts() {
        List<Discount> discountsDiscounts = discounts.getDiscounts();

        assertThatThrownBy(() -> discountsDiscounts.add(Discount.of(WEEKDAY_DISCOUNT, 2023)))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}