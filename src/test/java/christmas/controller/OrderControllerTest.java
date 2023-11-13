package christmas.controller;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.event.discount.DiscountRepository;
import christmas.domain.event.gift.GiftRepository;
import christmas.service.event.DiscountService;
import christmas.service.event.EventDetailService;
import christmas.service.event.GiftService;
import christmas.service.order.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.Reader;
import christmas.view.TestWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("주문 컨트롤러는")
class OrderControllerTest {

    OrderController orderController;
    TestWriter writer;
    TestQueueReader reader;

    @BeforeEach
    void setUp() {
        writer = new TestWriter();
        reader = new TestQueueReader();

        InputView inputView = new InputView(reader, writer);
        OutputView outputView = new OutputView(writer);
        DiscountService discountService = new DiscountService(new DiscountRepository());
        GiftService giftService = new GiftService(new GiftRepository());
        EventDetailService eventDetailService = new EventDetailService(discountService, giftService);
        OrderService orderService = new OrderService(eventDetailService);

        orderController = new OrderController(inputView, outputView, orderService);
    }

    @DisplayName("정상적인 날짜가 입력될 때까지 재입력 받는다.")
    @Test
    void createDay() {
        // given
        reader.add("A", "01", "32", "-1", "12일", "12월1일", "", " 1", "25");
        reader.add("해산물파스타-2,레드와인-1");

        // when
        orderController.run();

        // then
        assertThat(writer.getString())
                .contains("12월 25일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!");
    }

    @DisplayName("정상적인 메뉴가 입력될 때까지 재입력 받는다.")
    @Test
    void createMenuCount() {
        // given
        reader.add("25");
        reader.add("티마스-2", "해산물파스타-2,,", "제로콜라-1", "해산물파스타-A", "해토파스칼-2,레드와인-1", "해산물파스타-2,레드와인-1");

        // when
        orderController.run();

        // then
        assertThat(writer.getString())
                .contains("<주문 메뉴>\n해산물파스타 2개\n레드와인 1개");
    }

    static class TestQueueReader implements Reader {

        private static final Queue<String> queue = new LinkedList<>();

        public void add(String... args) {
            queue.addAll(List.of(args));
        }

        @Override
        public String readLine() {
            return queue.poll();
        }

        @Override
        public void close() {
        }
    }

}