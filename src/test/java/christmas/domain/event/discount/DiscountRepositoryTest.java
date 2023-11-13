package christmas.domain.event.discount;

import static christmas.domain.event.discount.DiscountEventType.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.SPECIAL_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.WEEKDAY_DISCOUNT;
import static christmas.domain.event.discount.DiscountEventType.WEEKEND_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountRepositoryTest {

    DiscountRepository discountRepository = new DiscountRepository();

    @BeforeEach
    void setUp() {
        EnumMap<DiscountEventType, Integer> discountCounts = new EnumMap<>(DiscountEventType.class);
        discountCounts.put(SPECIAL_DISCOUNT, 1_000);
        discountCounts.put(CHRISTMAS_D_DAY_DISCOUNT, 2_400);
        discountCounts.put(WEEKDAY_DISCOUNT, 2_023);
        discountCounts.put(WEEKEND_DISCOUNT, 0);

        discountRepository.init(discountCounts);
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
        Map<String, Integer> activeResult = discountRepository.getActiveResult();

        // then
        assertThat(activeResult)
                .containsExactlyInAnyOrderEntriesOf(Map.of("특별 할인", 1_000,
                        "크리스마스 디데이 할인", 2_400,
                        "평일 할인", 2_023));
    }
}