package christmas.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

import christmas.controller.convertor.RequestConverter;
import christmas.request.MenuCountRequest;
import java.util.List;

public class InputView {

    public int readDate() {
        System.out.println(Request.VISIT_DAY.value);

        return RequestConverter.convertDayToInt(readLine());
    }

    public List<MenuCountRequest> readMenuAndCount() {
        System.out.println(Request.MENU_AND_COUNT.value);

        return RequestConverter.convertMenuCountRequests(readLine());
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
