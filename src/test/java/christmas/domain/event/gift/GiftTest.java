package christmas.domain.event.gift;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GiftTest {

    @DisplayName("할인 결과가 Type과 Amount로 생성된다.")
    @Test
    void of() {
        // when
        Gift gift = Gift.of(GiftEventType.PRESENTATION, 25000);

        // then
        assertThat(gift.getAmount()).isEqualTo(25000);
        assertThat(gift).extracting("giftEventType")
                .isEqualTo(GiftEventType.PRESENTATION);

    }

    @DisplayName("할인 금액이 0원이 아닌지 확인할 수 있다. True")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,10000,9999999})
    void isActiveTrue(int input) {
        // given
        Gift gift = Gift.of(GiftEventType.PRESENTATION, input);

        // when
        boolean notZero = gift.isActive();

        // then
        assertThat(notZero).isTrue();
    }

    @DisplayName("할인 금액이 0원이 아닌지 확인할 수 있다. False")
    @Test
    void isActiveFalse() {
        // given
        Gift gift = Gift.of(GiftEventType.PRESENTATION, 0);

        // when
        boolean notZero = gift.isActive();

        // then
        assertThat(notZero).isFalse();
    }

    @DisplayName("할인 이벤트의 제목을 얻을 수 있다.")
    @Test
    void getEventTitle() {
        // given
        Gift gift = Gift.of(GiftEventType.PRESENTATION, 10000);

        // when
        String title = gift.getEventTitle();

        // then
        assertThat(title).isEqualTo("증정 이벤트");
    }

    @DisplayName("증정 메뉴의 이름을 얻을 수 있다.")
    @Test
    void getMenuTitle() {
        // given
        Gift gift = Gift.of(GiftEventType.PRESENTATION, 10000);

        // when
        String menuTitle = gift.getMenuTitle();

        // then
        assertThat(menuTitle).isEqualTo(GiftEventType.PRESENTATION.getMenu().getTitle());
    }

    @DisplayName("증정 메뉴의 수량을 얻을 수 있다.")
    @Test
    void getMenuCount() {
        // given
        Gift gift = Gift.of(GiftEventType.PRESENTATION, 10000);

        // when
        int menuCount = gift.getMenuCount();

        // then
        assertThat(menuCount).isEqualTo(GiftEventType.PRESENTATION.getCount());
    }
}