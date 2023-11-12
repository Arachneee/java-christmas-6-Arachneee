package christmas.domain.event.discount;

import static christmas.domain.event.discount.DiscountEventType.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.SPECIAL_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.WEEKDAY_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.WEEKEND_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountRepositoryTest {

    DiscountRepository discountRepository = new DiscountRepository();

    @BeforeEach
    void setUp() {
        discountRepository.init(List.of(Discount.of(SPECIAL_DISCOUNT, 1_000),
                Discount.of(CHRISTMAS_D_DAY_DISCOUNT, 2_400),
                Discount.of(WEEKDAY_DISCOUNT, 2_023),
                Discount.of(WEEKEND_DISCOUNT, 0)));
    }

    @DisplayName("전체 할인 금액을 계산할 수 있다.")
    @Test
    void calculateTotal() {
        // given // when
        int totalDiscount = discountRepository.calculateTotal();

        // then
        assertThat(totalDiscount).isEqualTo(1000 + 2400 + 2023);
    }

    @DisplayName("적용된 할인목록을 구할 수 있다.")
    @Test
    void getActiveResult() {
        // given // when
        List<Discount> discountsDiscounts = discountRepository.getActiveResult();

        // then
        assertThat(discountsDiscounts).hasSize(3)
                .containsExactlyInAnyOrder(Discount.of(SPECIAL_DISCOUNT, 1_000),
                        Discount.of(CHRISTMAS_D_DAY_DISCOUNT, 2_400),
                        Discount.of(WEEKDAY_DISCOUNT, 2_023));
    }
}