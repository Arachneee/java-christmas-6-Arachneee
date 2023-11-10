package christmas.view.converter;

import static christmas.exception.constant.ErrorMessage.INVALID_ORDER;

import christmas.exception.OrderException;
import christmas.exception.constant.ErrorMessage;
import christmas.view.request.MenuCountRequest;
import java.util.Arrays;
import java.util.List;

public class RequestConverter {

    private static final String MENU_SPLIT_SIGNAL = ",";
    private static final String COUNT_SPLIT_SIGNAL = "-";
    private static final int TITLE_INDEX = 0;
    private static final int COUNT_INDEX = 1;

    private RequestConverter() {
    }

    public static int convertDayToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw OrderException.from(ErrorMessage.INVALID_DAY);
        }
    }

    public static List<MenuCountRequest> convertMenuCountRequests(final String input) {
        try {
            return Arrays.stream(input.split(MENU_SPLIT_SIGNAL))
                    .map(RequestConverter::splitMenuCount)
                    .map(RequestConverter::createMenuCountRequest)
                    .toList();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw OrderException.from(INVALID_ORDER);
        }

    }

    private static String[] splitMenuCount(final String menuCount) {
        return menuCount.split(COUNT_SPLIT_SIGNAL);
    }

    private static MenuCountRequest createMenuCountRequest(String[] menu) {
        return MenuCountRequest.of(menu[TITLE_INDEX], convertCountToInt(menu[COUNT_INDEX]));
    }

    public static int convertCountToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw OrderException.from(INVALID_ORDER);
        }
    }
}
