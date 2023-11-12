package christmas.domain.event.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GiftRepositoryTest {

    GiftRepository giftRepository = new GiftRepository();

    @BeforeEach
    void setUp() {
        giftRepository.init(List.of(Gift.of(GiftEventType.PRESENTATION, 25_000)));
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
        List<Gift> gifts = giftRepository.getActiveResult();

        // then
        assertThat(gifts).hasSize(1)
                .containsExactlyInAnyOrder(Gift.of(GiftEventType.PRESENTATION, 25_000));
    }
}