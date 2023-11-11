package christmas.domain.order.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {


    @DisplayName("메뉴 이름으로 Menu로 변환 할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"양송이수프,BUTTON_MUSHROOM_SOUP", "타파스,TAPAS", "시저샐러드,CAESAR_SALAD",
            "티본스테이크,T_BONE_STEAK", "바비큐립,BARBECUE_RIBS", "해산물파스타,SEAFOOD_PASTA", "크리스마스파스타,CHRISTMAS_PASTA",
            "초코케이크,CHOCOLATE_CAKE", "아이스크림,ICE_CREAM",
            "제로콜라,ZERO_COLA", "레드와인,RED_WINE", "샴페인,CHAMPAGNE"}, delimiter = ',')
    void from(String name, Menu target) {
        // given // when
        Menu menu = Menu.from(name);

        // then
        assertThat(menu).isEqualByComparingTo(target);
    }

    @DisplayName("메뉴 이름이 Menu에 없으면 예외를 반환한다..")
    @ParameterizedTest
    @ValueSource(strings = {"스파게티","치킨","피자"})
    void from(String name) {
        assertThatThrownBy(() -> Menu.from(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("메뉴의 카테고리를 확인할 수 있다. True")
    @ParameterizedTest
    @CsvSource(value = {"APPETIZER,BUTTON_MUSHROOM_SOUP", "APPETIZER,TAPAS", "APPETIZER,CAESAR_SALAD",
            "MAIN,T_BONE_STEAK", "MAIN,BARBECUE_RIBS", "MAIN,SEAFOOD_PASTA", "MAIN,CHRISTMAS_PASTA",
            "DESSERT,CHOCOLATE_CAKE", "DESSERT,ICE_CREAM",
            "BEVERAGE,ZERO_COLA", "BEVERAGE,RED_WINE", "BEVERAGE,CHAMPAGNE"}, delimiter = ',')
    void isCategoryTrue(Category target, Menu menu) {
        assertThat(menu.isCategory(target)).isTrue();
    }

    @DisplayName("메뉴의 카테고리를 확인할 수 있다. False")
    @ParameterizedTest
    @CsvSource(value = {"MAIN,BUTTON_MUSHROOM_SOUP", "DESSERT,TAPAS", "DESSERT,CAESAR_SALAD",
            "APPETIZER,T_BONE_STEAK", "APPETIZER,BARBECUE_RIBS", "DESSERT,SEAFOOD_PASTA", "BEVERAGE,CHRISTMAS_PASTA",
            "BEVERAGE,CHOCOLATE_CAKE", "MAIN,ICE_CREAM",
            "MAIN,ZERO_COLA", "DESSERT,RED_WINE", "DESSERT,CHAMPAGNE"}, delimiter = ',')
    void isCategoryFalse(Category target, Menu menu) {
        assertThat(menu.isCategory(target)).isFalse();
    }


}