package christmas.controller.convertor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.order.constant.Menu;
import christmas.view.request.MenuCountRequest;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuConvertorTest {

    @DisplayName("List<MenuCountRequest>를 EnumMap 으로 변환할 수 있다.")
    @Test
    void convertToEnumMap() {
        // given
        List<MenuCountRequest> menuCountRequests = List.of(MenuCountRequest.of("티본스테이크", 1),
                MenuCountRequest.of("초코케이크", 2), MenuCountRequest.of("제로콜라", 3));

        // when
        EnumMap<Menu, Integer> convert = MenuConvertor.convertToEnumMap(menuCountRequests);

        // then
        assertThat(convert).containsExactlyInAnyOrderEntriesOf(
                Map.of(Menu.T_BONE_STEAK, 1, Menu.CHOCOLATE_CAKE, 2, Menu.ZERO_COLA, 3));
    }

    @DisplayName("중복된 메뉴가 있을 경우 예외가 발생한다.")
    @Test
    void throwingDuplicateOrderException() {
        // given
        List<MenuCountRequest> menuCountRequests = List.of(MenuCountRequest.of("티본스테이크", 1),
                MenuCountRequest.of("초코케이크", 2), MenuCountRequest.of("티본스테이크", 3));

        assertThatThrownBy(() -> MenuConvertor.convertToEnumMap(menuCountRequests))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

}