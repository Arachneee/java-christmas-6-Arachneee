package christmas.view;

import christmas.view.io.Reader;
import christmas.view.io.Writer;

public class InputView {

    private final Reader reader;
    private final Writer writer;

    public InputView(final Reader reader, final Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public String readDate() {
        writer.println(Request.VISIT_DAY.value);

        return reader.readLine();
    }

    public String readMenuAndCount() {
        writer.println(Request.MENU_AND_COUNT.value);

        return reader.readLine();
    }

    public void close() {
        reader.close();
    }

    private enum Request {
        VISIT_DAY("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
        MENU_AND_COUNT("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        private final String value;

        Request(final String value) {
            this.value = value;
        }
    }
}
