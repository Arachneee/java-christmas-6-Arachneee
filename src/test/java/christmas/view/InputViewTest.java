package christmas.view;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("입력 뷰는")
class InputViewTest {

    TestWriter writer = new TestWriter();
    InputView inputView;


    @BeforeEach
    void setUp() {
        writer.clear();
    }

    @DisplayName("날짜를 입력하라는 문자를 출력할 수 있다.")
    @Test
    void readDateWriter() {
        // given
        inputView = new InputView(new TestReader("3"), writer);

        // when
        inputView.readDate();

        // then
        assertThat(writer.getString())
                .isEqualTo("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)" + lineSeparator());
    }

    @DisplayName("문자를 입력받을 수 있다.")
    @Test
    void readDateReader() {
        // given
        inputView = new InputView(new TestReader("3"), writer);

        // when
        String day = inputView.readDate();

        // then
        assertThat(day)
                .isEqualTo("3");
    }

    @DisplayName("메뉴와 수량을 입력하라는 문자를 출력할 수 있다.")
    @Test
    void readMenuAndCountWriter() {
        // given
        inputView = new InputView(new TestReader("타파스-1,제로콜라-1"), writer);
        // when
        inputView.readMenuAndCount();

        // then
        assertThat(writer.getString())
                .isEqualTo("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)" +
                        lineSeparator());
    }

    @DisplayName("메뉴와 수량을 입력받을 수 있다.")
    @Test
    void readMenuAndCountReader() {
        // given
        inputView = new InputView(new TestReader("타파스-1,제로콜라-1"), writer);

        // when
        inputView.readMenuAndCount();

        // then
        assertThat(writer.getString())
                .isEqualTo("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)" +
                        lineSeparator());
    }
}