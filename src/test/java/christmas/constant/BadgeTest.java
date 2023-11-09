package christmas.constant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BadgeTest {

    @DisplayName("해택금액이 20000원 이상이면 산타등급이다.")
    @ParameterizedTest
    @ValueSource(ints = {20000, 30000, 100000})
    void santa(int discount) {
        // given // when
        Badge badge = Badge.from(discount);

        // then
        assertThat(badge).isEqualByComparingTo(Badge.SANTA);
    }

    @DisplayName("해택금액이 20000원 미만 10000원 이상이면 트리등급이다.")
    @ParameterizedTest
    @ValueSource(ints = {10000, 15000, 19999})
    void tree(int discount) {
        // given // when
        Badge badge = Badge.from(discount);

        // then
        assertThat(badge).isEqualByComparingTo(Badge.TREE);
    }

    @DisplayName("해택금액이 10000원 미만 5000원 이상이면 별등급이다.")
    @ParameterizedTest
    @ValueSource(ints = {5000, 8000, 9999})
    void star(int discount) {
        // given // when
        Badge badge = Badge.from(discount);

        // then
        assertThat(badge).isEqualByComparingTo(Badge.STAR);
    }

    @DisplayName("해택금액이 5000원 미만이면 등급이 없다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1000, 4999})
    void none(int discount) {
        // given // when
        Badge badge = Badge.from(discount);

        // then
        assertThat(badge).isEqualByComparingTo(Badge.NONE);
    }

}