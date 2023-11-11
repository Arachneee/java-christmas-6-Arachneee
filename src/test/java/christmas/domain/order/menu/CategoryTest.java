package christmas.domain.order.menu;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategoryTest {

    @DisplayName("음료가 아닌지 확인할 수 있다. True")
    @ParameterizedTest
    @ValueSource(strings = {"APPETIZER", "MAIN", "DESSERT"})
    void isNotBeverageTrue(Category category) {

        assertThat(category.isNotBeverage()).isTrue();
    }

    @DisplayName("음료가 아닌지 확인할 수 있다. False")
    @Test
    void isNotBeverageFalse() {

        assertThat(Category.BEVERAGE.isNotBeverage()).isFalse();
    }
}