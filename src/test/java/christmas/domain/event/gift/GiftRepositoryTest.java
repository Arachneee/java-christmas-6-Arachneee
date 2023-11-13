package christmas.domain.event.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GiftRepositoryTest {

    GiftRepository giftRepository = new GiftRepository();

    @BeforeEach
    void setUp() {
        EnumMap<GiftEventType, Integer> giftCounts = new EnumMap<>(GiftEventType.class);
        giftCounts.put(GiftEventType.PRESENTATION, 25_000);

        giftRepository.init(giftCounts);
    }

    @DisplayName("전체 할인 금액을 계산할 수 있다.")
    @Test
    void calculateTotal() {
        // given // when
        int totalDiscount = giftRepository.calculateTotal();

        // then
        assertThat(totalDiscount).isEqualTo(25_000);
    }

    @DisplayName("적용된 할인목록을 구할 수 있다.")
    @Test
    void getActiveResult() {
        // given // when
        Map<String, Integer> activeResult = giftRepository.getActiveResult();

        // then
        assertThat(activeResult)
                .containsExactlyInAnyOrderEntriesOf(Map.of("증정 이벤트", 25_000));
    }


    @DisplayName("적용된 증정 메뉴의 이름과 수량을 알 수 있다.")
    @Test
    void getActiveMenuCounts() {
        // given // when
        Map<String, Integer> activeMenuCounts = giftRepository.getActiveMenuCounts();

        // then
        assertThat(activeMenuCounts)
                .containsExactlyInAnyOrderEntriesOf(Map.of("샴페인", 1));
    }
}